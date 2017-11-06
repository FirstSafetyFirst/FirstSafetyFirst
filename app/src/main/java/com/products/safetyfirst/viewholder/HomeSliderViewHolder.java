package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class HomeSliderViewHolder extends RecyclerView.ViewHolder{
    public ImageView slider_image;

    public HomeSliderViewHolder(View view) {

        super(view);
        slider_image = (ImageView) view.findViewById(R.id.sliders_img);

    }
}
