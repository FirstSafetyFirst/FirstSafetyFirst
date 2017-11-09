package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class TrainingViewHolder extends RecyclerView.ViewHolder{
    public final View mView;
    public final TextView type;


    public TrainingViewHolder(View view) {

        super(view);
        mView = view;
        type = view.findViewById(R.id.type);
    }
}
