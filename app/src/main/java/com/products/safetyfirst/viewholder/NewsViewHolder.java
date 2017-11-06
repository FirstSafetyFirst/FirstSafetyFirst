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

public class NewsViewHolder extends RecyclerView.ViewHolder{

    public ImageView images, favicon, bookmark;
    public TextView title, timestamp;
    public Button detail;

    public NewsViewHolder(View view) {

        super(view);
        images = (ImageView) view.findViewById(R.id.news_avtar);
        favicon = (ImageView) view.findViewById(R.id.favicon);
        bookmark = (ImageView) view.findViewById(R.id.bookmark);
        title = (TextView) view.findViewById(R.id.title);
        timestamp = (TextView) view.findViewById(R.id.dateTime);
        detail = (Button) view.findViewById(R.id.view_details);

    }
}
