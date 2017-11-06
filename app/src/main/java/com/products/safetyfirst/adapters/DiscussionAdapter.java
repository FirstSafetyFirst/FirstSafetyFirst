package com.products.safetyfirst.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.modelhelper.AuthorHelper;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.models.PostDiscussionModel;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.viewholder.PostViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by rishabh on 21/10/17.
 */

public class DiscussionAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<Pair<String, PostModel>> posts;
    private DiscussionCallbacks discussionCallbacks;
    private LinearLayoutManager layoutManager;

    /* Helpers */
    AuthorHelper authorHelper = AuthorHelper.getInstance();
    PostHelper postHelper = PostHelper.getInstance();

    public DiscussionAdapter(LinearLayoutManager layoutManager, DiscussionCallbacks discussionCallbacks) {
        posts = new ArrayList<>();
        this.layoutManager = layoutManager;
        this.discussionCallbacks = discussionCallbacks;
        discussionCallbacks.updateData(posts, this);

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);
        return new PostViewHolder(itemView);
    }

    public void notifyScrollUp(){
        this.notifyDataSetChanged();
        layoutManager.scrollToPosition(0);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int pos) {
        //int position = posts.size() - pos - 1;
        int position = pos;
        final PostDiscussionModel postDiscussionData = new PostDiscussionModel();
        final PostModel post = posts.get(position).second;
        final String postKey = posts.get(position).first;

        postDiscussionData.setFromPostModel(post);

        Disposable subs = authorHelper.getPeerName(post.getUid()).observeOn(
                AndroidSchedulers.mainThread()
        ).subscribeOn(
                Schedulers.io()
        ).flatMap(new Function<String, SingleSource<String>>() {
            @Override
            public SingleSource<String> apply(@NonNull String name) throws Exception {
                postDiscussionData.author = name;
                return authorHelper.getPeerEmail(post.getUid());
            }
        }).flatMap(new Function<String, SingleSource<String>>() {
            @Override
            public SingleSource<String> apply(@NonNull String email) throws Exception {
                postDiscussionData.authorEmail = email;
                return authorHelper.getPeerImage(post.getUid());
            }
        }).flatMap(new Function<String, SingleSource<Integer>>() {
            @Override
            public SingleSource<Integer> apply(@NonNull String photoUrl) throws Exception {
                postDiscussionData.authorPhoto = photoUrl;
                return postHelper.getStarCount(postKey);
            }
        }).map(new Function<Integer, Object>() {
            @Override
            public Object apply(@NonNull Integer starCount) throws Exception {
                postDiscussionData.starCount = starCount;
                return Single.just(1);
            }
        }).doAfterSuccess(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                postDiscussionData.key = postKey;
                holder.setData(postDiscussionData);
            }
        }).subscribe();

        if(position+2 == posts.size()){
            //discussionCallbacks.updateData(posts, this);
        }
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }


    public interface DiscussionCallbacks {
        void updateData(List<Pair<String, PostModel>> postList, DiscussionAdapter adapter);
    }
}
