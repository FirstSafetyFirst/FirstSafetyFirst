package com.products.safetyfirst.viewholder;

import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder{

    public final ImageView images;
    public final TextView title;
    public final TextView views;
    public final CheckableImageButton share;
    public final CardView newsCardView;

    public NewsViewHolder(View view) {

        super(view);
        newsCardView= view.findViewById(R.id.news_item_card_view);
        images = view.findViewById(R.id.news_avtar);
        title = view.findViewById(R.id.title);
        share = view.findViewById(R.id.share);
        views = view.findViewById(R.id.noOfViews);
        //timestamp = view.findViewById(R.id.dateTime);
    }
}
