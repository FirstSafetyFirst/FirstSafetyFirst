package com.products.safetyfirst.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.presenter.PostDetailPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.CommentsAdapterView;
import com.products.safetyfirst.interfaces.presenter.PostDetailPresenter;
import com.products.safetyfirst.Pojos.Comment;
import com.products.safetyfirst.viewholder.CommentViewHolder;

import java.util.ArrayList;

/**
 * Created by vikas on 08/11/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> implements CommentsAdapterView{

    private final ArrayList<Comment> mCommentssList = new ArrayList<>();
    private final ArrayList<String> mKeysList = new ArrayList<>();
    private final PostDetailPresenter presenter;
    private final Context context;
    private final String mPostKey;


    public CommentsAdapter (Context context, String mPostKey){
        this.presenter = new PostDetailPresenterImpl(this);
        this.context = context;
        this.mPostKey = mPostKey;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(CommentViewHolder holder, final int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        Comment comment = mCommentssList.get(position);

        if(comment.getAuthor() != null) holder.title.setText(comment.getAuthor());

        if(comment.getXmlText() != null) holder.comment.setText(comment.getXmlText());


        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.addLike(mPostKey, mKeysList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommentssList.size();
    }

    @Override
    public void addAllComments(ArrayList<Comment> comments) {
        mCommentssList.clear();
        mCommentssList.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public void addAllKeys(ArrayList<String> keys) {
        mKeysList.clear();
        mKeysList.addAll(keys);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.requestAnswers(mPostKey);
    }
}

