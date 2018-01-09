package com.products.safetyfirst.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.Pojos.NewsModel;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.NewsDetailActivity;
import com.products.safetyfirst.activity.PostDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.sql.Types.NULL;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.news_avtar)
    ImageView images;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.noOfViews)
    TextView views;

    @BindView(R.id.share)
    CheckableImageButton share;

    @BindView(R.id.news_item_card_view)
    CardView newsCardView;

    private final Context context;

    public NewsViewHolder(View view) {
        super(view);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setData(final NewsModel newsModel, final String key) {

        if(newsModel.getTitle() != null)
            title.setText(newsModel.getTitle());
        else
            title.setText("");

        if(newsModel.getImgUrl() != null){
            Glide.with(context).load(newsModel.getImgUrl()).fitCenter().into(images);
        }

        if(newsModel.getNumViews() != NULL)
            views.setText(String.valueOf(newsModel.getNumViews()) + " Views");
        else
            views.setText("0 Views");


        newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.EXTRA_NEWS_KEY, key);
                context.startActivity(intent);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsModel.getDeeplink() != null) {
                    Intent intent = new Intent();
                    String msg = "Hey see this News: " + newsModel.getDeeplink();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                    intent.setType("text/plain");
                    context.startActivity(intent);
                }

            }
        });

    }
}
