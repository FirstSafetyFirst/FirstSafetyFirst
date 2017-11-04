package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder{
    public View mView;
    public ImageView images;
    public TextView title;
    public TextView dateTime;
    public ImageView bookmark;
    public TextView going;
    public TextView maybe;
    public Button details;


    public EventViewHolder(View view) {

        super(view);
        mView = view;
        images = (ImageView) view.findViewById(R.id.event_avtar);
        title = (TextView) view.findViewById(R.id.title);
        dateTime = (TextView) view.findViewById(R.id.dateTime);
        bookmark = (ImageView) view.findViewById(R.id.bookmark);

        going = (TextView) view.findViewById(R.id.going);
        maybe = (TextView) view.findViewById(R.id.interested);
        details = (Button) view.findViewById(R.id.view_details);

    }
}


