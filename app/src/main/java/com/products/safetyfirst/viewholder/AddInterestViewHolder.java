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
 private final View mView;
    private final ImageView mIcon;
    public final TextView mInterest;
    public final CheckBox mCheck;

    public AddInterestViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mIcon = itemView.findViewById(R.id.icon);
        mInterest = itemView.findViewById(R.id.interest);
        mCheck = itemView.findViewById(R.id.check);
    }
}

