package com.products.safetyfirst.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.models.PostDiscussionModel;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.utils.JustifiedWebView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class PostViewHolder  extends RecyclerView.ViewHolder{
    public ImageView images;
    public ImageView overflow;
    public ImageView post_author_photo;
    public ImageView likeBtn;
    public ImageView ansBtn;
    public ImageView bookmark;
    public TextView post_title;
    public TextView dateTime;
    public TextView post_author;
    public TextView post_author_email;
    public JustifiedWebView body;
    public Button readMore;
    public LinearLayout post_author_layout;
    public ImageView postImage;

    public final Context context;

    public PostViewHolder(View itemView){
        super(itemView);
        post_author_photo = itemView.findViewById(R.id.post_author_photo);
        overflow = itemView.findViewById(R.id.overflow);
        likeBtn = itemView.findViewById(R.id.LikeBtn);
        ansBtn = itemView.findViewById(R.id.ansBtn);
        bookmark = itemView.findViewById(R.id.bookmark);
        post_title = itemView.findViewById(R.id.post_title);
        body = itemView.findViewById(R.id.post_body);
        dateTime = itemView.findViewById(R.id.dateTime);
        post_author = itemView.findViewById(R.id.post_author);
        post_author_email = itemView.findViewById(R.id.post_author_email);
        readMore = itemView.findViewById(R.id.view_details);
        post_author_layout = itemView.findViewById(R.id.post_author_layout);
        postImage = itemView.findViewById(R.id.post_image);
        context = itemView.getContext();
    }


    public void setData(final PostDiscussionModel postData) {
        post_title.setText(postData.title);
        body.setText(postData.body);
        post_author.setText(postData.author);
        post_author_email.setText(postData.authorEmail);
        Glide.with(context).load(postData.authorPhoto).error(R.drawable.ic_person_black_24dp).transform(new CircleTransform(context)).into(post_author_photo);
        Date date = new Date(postData.timestamp * 1000);
        SimpleDateFormat sDate = new SimpleDateFormat("dd MM yyyy", new Locale("hi", "IN"));
        dateTime.setText(sDate.format(date));

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postData.key);
                Toast.makeText(context, postData.key , Toast.LENGTH_SHORT).show();
                context.startActivity(intent);

            }
        });
    }

    public void setData(PostModel post){
        post_title.setText(post.getTitle());
        post_author.setText(post.getAuthor());
        //numStarsView.setText(String.valueOf(post.starCount));

         body.setText(post.getBody());
    }

}
