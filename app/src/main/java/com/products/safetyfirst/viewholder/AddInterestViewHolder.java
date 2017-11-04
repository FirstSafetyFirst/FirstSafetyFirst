package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class AddInterestViewHolder extends RecyclerView.ViewHolder{
 public View mView;
    public ImageView mIcon;
    public TextView mInterest;
    public CheckBox mCheck;

    public AddInterestViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mIcon = (ImageView) itemView.findViewById(R.id.icon);
        mInterest = (TextView) itemView.findViewById(R.id.interest);
        mCheck = (CheckBox) itemView.findViewById(R.id.check);
    }
}

