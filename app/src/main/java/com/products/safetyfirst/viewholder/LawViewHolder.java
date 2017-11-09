package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class LawViewHolder extends RecyclerView.ViewHolder{
    private final ImageView images;

    public LawViewHolder(View view) {

        super(view);
        images = view.findViewById(R.id.know_img);

    }
}
