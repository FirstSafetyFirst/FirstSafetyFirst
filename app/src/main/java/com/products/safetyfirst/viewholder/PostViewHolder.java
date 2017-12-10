package com.products.safetyfirst.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class PostViewHolder  extends RecyclerView.ViewHolder{
    private ImageView post_author_photo;
    private TextView post_title;
    private TextView post_author;
    private JustifiedWebView body;
    private Button readMore;
    private CardView postCardView;
    private final Context context;

    public PostViewHolder(View itemView){
        super(itemView);
        post_author_photo = itemView.findViewById(R.id.post_author_photo);
        post_title = itemView.findViewById(R.id.post_title);
        body = itemView.findViewById(R.id.post_body);
        post_author = itemView.findViewById(R.id.post_author);
        //readMore = itemView.findViewById(R.id.view_details);
        context = itemView.getContext();
        postCardView= itemView.findViewById(R.id.card_view);
    }


    public void setData(final PostModel postData, final String key) {
        if(postData.getTitle() != null)
            post_title.setText(postData.getTitle());
        else
            post_title.setText("   ");

        if(postData.getBody() != null)
            body.setText(postData.getBody());
        else
            body.setText("   ");

        if(postData.getAuthor()!= null)
            post_author.setText(postData.getAuthor());
        else
            post_author.setText("Anonymous");

        Glide.with(context).load(postData.getAuthorImageUrl()).error(R.drawable.ic_person_black_24dp).transform(new CircleTransform(context)).into(post_author_photo);
        /*
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       Toast.makeText(context, postData.getBody(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, key);
                context.startActivity(intent);

            }
        });
        */
    }

}
