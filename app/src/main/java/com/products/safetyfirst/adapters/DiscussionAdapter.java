package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.utils.JustifiedWebView;

import java.util.List;


/**
 * Created by vikas on 01/11/17.
 */

public class DiscussionAdapter extends
        RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<PostModel> postModels;
   // private Context context;

    // Pass in the contact array into the constructor
    public DiscussionAdapter(List<PostModel> contacts, Context context) {
        postModels = contacts;
    //    this.context = context;
    }

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
        private Context context;

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
            context = itemView.getContext();
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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final PostModel postModel = postModels.get(position);


        //CharSequence getRelativeTimeSpanString (long time,
        //long now,
        //long minResolution)

        viewHolder.post_title.setText(postModel.getTitle());
        viewHolder.post_title.setAllCaps(true);



        viewHolder.body.setText(postModel.getBody()+"");

        viewHolder.overflow.setVisibility(View.GONE);

        viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.ansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.bookmark.setVisibility(View.GONE);

        viewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewHolder.context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postModel.getPreviousPost());
                viewHolder.context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }
}