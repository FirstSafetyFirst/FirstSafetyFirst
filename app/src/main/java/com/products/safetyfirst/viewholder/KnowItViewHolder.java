package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class KnowItViewHolder extends RecyclerView.ViewHolder{
    public View mView;
    public TextView title;
    public ImageView imageView;

    public KnowItViewHolder(View itemView) {
        super(itemView);
        mView=itemView;
        title= (TextView) itemView.findViewById(R.id.know_it_item_title);
        imageView= (ImageView) itemView.findViewById(R.id.know_it_item_image);

    }
}
