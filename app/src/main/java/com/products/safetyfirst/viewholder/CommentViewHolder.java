package com.products.safetyfirst.viewholder;

import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * Created by vikas on 08/11/17.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder{

    public final View mView;
    public final TextView title;
    public final TextView comment;
    public ImageView bookmark;
    public TextView going;


    public CommentViewHolder(View view) {

        super(view);
        mView   = view;
        title   = view.findViewById(R.id.user_name);
        comment = view.findViewById(R.id.comment_body);


    }

}
