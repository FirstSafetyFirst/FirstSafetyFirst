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
    public final View mView;
    public final TextView title;
    public final ImageView imageView;

    public KnowItViewHolder(View itemView) {
        super(itemView);
        mView=itemView;
        title= itemView.findViewById(R.id.know_it_item_title);
        imageView= itemView.findViewById(R.id.know_it_item_image);

    }
}
