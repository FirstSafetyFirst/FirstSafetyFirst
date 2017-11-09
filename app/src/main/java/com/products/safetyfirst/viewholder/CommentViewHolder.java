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
    private final ImageView images;
    public final TextView title;
    public final JustifiedWebView comment;
    public ImageView bookmark;
    public TextView going;
    public final CheckableImageButton likeBtn;


    public CommentViewHolder(View view) {

        super(view);
        mView   = view;
        images  = view.findViewById(R.id.user_favicon);
        title   = view.findViewById(R.id.user_name);
        comment = view.findViewById(R.id.comment_body);
        likeBtn = view.findViewById(R.id.likeBtn);


    }

}
