package com.products.safetyfirst.recycler.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.products.safetyfirst.R;

/**
 * Created by profileconnect on 12/04/17.
 */

class Statics extends RecyclerView.ViewHolder {

    private ImageView statics;

    public Statics(View v) {
        super(v);
        statics = v.findViewById(R.id.images);
    }

    public ImageView getStatics() {
        return statics;
    }

    public void setStatics(ImageView statics) {
        this.statics = statics;
    }
}