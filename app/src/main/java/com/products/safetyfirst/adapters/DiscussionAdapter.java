package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.activity.ProfileActivity;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.viewholder.PostViewHolder;

import java.util.List;


/**
 * Created by vikas on 01/11/17.
 */

public class DiscussionAdapter extends
        RecyclerView.Adapter<PostViewHolder> {

    // Store a member variable for the contacts
    private final List<PostModel> postModels;
   // private Context context;

    // Pass in the contact array into the constructor
    public DiscussionAdapter(List<PostModel> contacts, Context context) {
        postModels = contacts;
    //    this.context = context;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.discussion_item, parent, false);

        return new PostViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder, int position) {
        final PostModel postModel = postModels.get(position);


        //CharSequence getRelativeTimeSpanString (long time,
        //long now,
        //long minResolution)

        postViewHolder.post_title.setText(postModel.getTitle());
        postViewHolder.post_title.setAllCaps(true);



        postViewHolder.body.setText(postModel.getBody()+"");

        postViewHolder.overflow.setVisibility(View.GONE);

        postViewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        postViewHolder.ansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        postViewHolder.bookmark.setVisibility(View.GONE);

        postViewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
/**
        postViewHolder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(postViewHolder.context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postModel.getPreviousPost());
                postViewHolder.context.startActivity(intent);
            }
        });
**/
        postViewHolder.post_author_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(postViewHolder.context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_PROFILE_KEY, postModel.getUid());
                postViewHolder.context.startActivity(intent);
            }
        });

        postViewHolder.postCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(postViewHolder.context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postModel.getPreviousPost());
                postViewHolder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }
}