package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class ProjectsViewHolder extends RecyclerView.ViewHolder {

    public View mView;
    public TextView mUserTextView;
    public TextView mComapnyTextView;
    public TextView mDescriptionTextView;

    public ProjectsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mUserTextView = (TextView) itemView.findViewById(R.id.username);
        mComapnyTextView = (TextView) itemView.findViewById(R.id.company);
        mDescriptionTextView = (TextView) itemView.findViewById(R.id.description);
    }
}

