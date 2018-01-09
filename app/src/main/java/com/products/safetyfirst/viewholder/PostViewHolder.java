package com.products.safetyfirst.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.utils.JustifiedWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.sql.Types.NULL;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class PostViewHolder  extends RecyclerView.ViewHolder{

    @BindView(R.id.post_title)
    TextView postTitle;

    @BindView(R.id.post_body)
    JustifiedWebView postBody;

    @BindView(R.id.post_image)
    ImageView imageView;

    @BindView(R.id.discussion_item_card_view)
    CardView postCardView;

    @BindView(R.id.share)
    CheckableImageButton share;

    @BindView(R.id.noOfViews)
    TextView numViews;

    private final Context context;

    public PostViewHolder(View itemView){
        super(itemView);

        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }


    public void setData(final PostModel postModel, final String key) {

        if(!postModel.getTitle().isEmpty())
            postTitle.setText(postModel.getTitle());
        else
            postTitle.setText("");

        if(!postModel.getBody().isEmpty())
            postBody.setText(postModel.getBody());
        else
            postBody.setText("");

        if(postModel.getNumViews() != NULL)
            numViews.setText(String.valueOf(postModel.getNumViews()) + " Views");
        else
            numViews.setText("0 Views");


        postCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, key);
                context.startActivity(intent);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postModel.getDeeplink() != null) {
                    Intent intent = new Intent();
                    String msg = postModel.getTitle()+ "  " + postModel.getDeeplink();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                    intent.setType("text/plain");
                    context.startActivity(intent);
                }

            }
        });
    }
}
