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

public class EventViewHolder extends RecyclerView.ViewHolder{
    public final View mView;
    public final ImageView images;
    public final TextView title;
    public final TextView dateTime;
    private final ImageView bookmark;
    public final TextView going;
    public final TextView maybe;
    public final TextView views;
    public final CardView eventCardView;
    public final CheckableImageButton share;

    public EventViewHolder(View view) {

        super(view);
        eventCardView= view.findViewById(R.id.event_item_card_view);
        mView = view;
        images = view.findViewById(R.id.event_avtar);
        title = view.findViewById(R.id.title);
        dateTime = view.findViewById(R.id.dateTime);
        bookmark = view.findViewById(R.id.bookmark);
        views = view.findViewById(R.id.noOfViews);
        going = view.findViewById(R.id.going);
        maybe = view.findViewById(R.id.interested);
        share = view.findViewById(R.id.share);
    }
}


