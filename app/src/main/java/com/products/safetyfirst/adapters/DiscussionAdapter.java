package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.modelhelper.AuthorHelper;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.utils.JustifiedWebView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rishabh on 21/10/17.
 */

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.PostViewHolder> {

    private List<Pair<String, PostModel>> posts;
    private DiscussionCallbacks discussionCallbacks;

    /* Helpers */
    AuthorHelper authorHelper = AuthorHelper.getInstance();
    PostHelper postHelper = PostHelper.getInstance();

    public DiscussionAdapter(DiscussionCallbacks discussionCallbacks) {
        posts = new ArrayList<>();
        this.discussionCallbacks = discussionCallbacks;
        discussionCallbacks.updateData(posts, this);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
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
                holder.setData(postDiscussionData);
            }
        }).subscribe();

        if(position+2 == posts.size()){
            discussionCallbacks.updateData(posts, this);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageView images, overflow, post_author_photo, likeBtn, ansBtn, bookmark;
        private TextView post_title, dateTime, post_author, post_author_email;
        private JustifiedWebView body;
        private Button readMore;
        private LinearLayout post_author_layout;

        private Context context;

        public PostViewHolder(View itemView) {
            super(itemView);
            post_author_photo = (ImageView) itemView.findViewById(R.id.post_author_photo);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            likeBtn = (ImageView) itemView.findViewById(R.id.LikeBtn);
            ansBtn = (ImageView) itemView.findViewById(R.id.ansBtn);
            bookmark = (ImageView) itemView.findViewById(R.id.bookmark);
            post_title = (TextView) itemView.findViewById(R.id.post_title);
            body = (JustifiedWebView) itemView.findViewById(R.id.type_info);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
            post_author = (TextView) itemView.findViewById(R.id.post_author);
            post_author_email = (TextView) itemView.findViewById(R.id.post_author_email);
            readMore = (Button) itemView.findViewById(R.id.view_details);
            post_author_layout = (LinearLayout) itemView.findViewById(R.id.post_author_layout);
            context = itemView.getContext();
        }

        public void setData(PostDiscussionModel postData) {
            post_title.setText(postData.title);
            body.setText(postData.body);
            post_author.setText(postData.author);
            post_author_email.setText(postData.authorEmail);
            Glide.with(context).load(postData.authorPhoto).error(R.drawable.ic_person_black_24dp).transform(new CircleTransform(context)).into(post_author_photo);
            Date date = new Date(postData.timestamp * 1000);
            SimpleDateFormat sDate = new SimpleDateFormat("dd MM yyyy", new Locale("hi", "IN"));
            dateTime.setText(sDate.format(date));
        }
    }

    public class PostDiscussionModel {
        public String title;
        public String body;
        public String author;
        public String authorEmail;
        public int starCount;
        public String authorPhoto;
        public long timestamp;

        public void setFromPostModel(PostModel post) {
            this.title = post.getTitle();
            this.body = post.getBody();
            this.timestamp = post.getTimestamp();
        }
    }

    public interface DiscussionCallbacks {
        void updateData(List<Pair<String, PostModel>> postList, DiscussionAdapter adapter);
    }
}
