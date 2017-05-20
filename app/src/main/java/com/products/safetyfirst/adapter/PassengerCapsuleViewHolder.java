package com.products.safetyfirst.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.products.safetyfirst.R;


/**
 * Created by danylo.volokh on 10/17/2015.
 */
public class PassengerCapsuleViewHolder extends RecyclerView.ViewHolder {

    public final TextView mCapsuleName;
    public final View mItemView;

    public PassengerCapsuleViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mCapsuleName = (TextView)itemView.findViewById(R.id.capsule_name);
    }
}
