package com.products.safetyfirst.adapters;

<<<<<<< HEAD
import android.support.v7.widget.LinearLayoutManager;
=======
import android.content.Context;
>>>>>>> genericAdapter
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD

import com.products.safetyfirst.R;
import com.products.safetyfirst.modelhelper.AuthorHelper;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.models.PostDiscussionModel;
=======
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.products.safetyfirst.R;
>>>>>>> genericAdapter
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.viewholder.PostViewHolder;

<<<<<<< HEAD
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
=======
import java.util.List;
>>>>>>> genericAdapter

/**
 * Created by vikas on 01/11/17.
 */

<<<<<<< HEAD
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

=======
public class DiscussionAdapter extends
        RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {
>>>>>>> genericAdapter

    // Store a member variable for the contacts
    private List<PostModel> mContacts;

    // Pass in the contact array into the constructor
    public DiscussionAdapter(List<PostModel> contacts) {
        mContacts = contacts;
    }

<<<<<<< HEAD
=======
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView images, overflow, post_author_photo, likeBtn, ansBtn, bookmark;
        public TextView post_title, dateTime, post_author, post_author_email;
        public JustifiedWebView body;
        public Button readMore;
        public LinearLayout post_author_layout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            post_author_photo = (ImageView) itemView.findViewById(R.id.post_author_photo);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            likeBtn = (ImageView) itemView.findViewById(R.id.LikeBtn);
            ansBtn = (ImageView) itemView.findViewById(R.id.ansBtn);
            bookmark = (ImageView) itemView.findViewById(R.id.bookmark);
            post_title = (TextView) itemView.findViewById(R.id.post_title);
            body = (JustifiedWebView) itemView.findViewById(R.id.post_body);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
            post_author = (TextView) itemView.findViewById(R.id.post_author);
            post_author_email = (TextView) itemView.findViewById(R.id.post_author_email);
            readMore = (Button) itemView.findViewById(R.id.view_details);
            post_author_layout = (LinearLayout) itemView.findViewById(R.id.post_author_layout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.discussion_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        PostModel contact = mContacts.get(position);

        TextView textView = viewHolder.post_title;
        textView.setText(contact.getTitle());
    }
>>>>>>> genericAdapter

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}