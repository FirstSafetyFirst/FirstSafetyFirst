package com.products.safetyfirst.viewholder;

import android.content.Context;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.Pojos.Comment;
import com.products.safetyfirst.R;
import com.products.safetyfirst.utils.JustifiedWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by vikas on 08/11/17.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.rating_item_date)
    TextView ratingDate;

    @BindView(R.id.rating_item_rating)
    MaterialRatingBar rating;

    @BindView(R.id.rating_item_text)
    TextView comment;

    private final Context context;

    public CommentViewHolder(View itemView) {

        super(itemView);

        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setData(final Comment comment, final String key) {
         if(comment.getAuthor() != null) userName.setText(comment.getAuthor());

         if(comment.getText() != null) comment.setText(comment.getText());

    }

}
