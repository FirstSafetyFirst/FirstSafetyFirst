package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.impementations.presenter.PostPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.PostAdapterView;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.viewholder.PostViewHolder;

import java.util.ArrayList;

/**
 * Created by vikas on 14/11/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> implements PostAdapterView {

    private final ArrayList<PostModel> mEventsList = new ArrayList<>();
    private final ArrayList<String> mKeysList = new ArrayList<>();
    private final PostPresenter presenter;
    private final Context context;
    private final String mUserId;

    public PostAdapter(Context context, String userId){
        this.presenter = new PostPresenterImpl(this);
        this.context = context;
        this.mUserId = userId;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {


        final PostModel postModel = mEventsList.get(position);
        holder.setIsRecyclable(false);
        holder.setData(postModel);

       /* if(postModel.getTitle() != null) holder.post_title.setText(postModel.getTitle());

        if(postModel.getAuthor() != null) holder.post_author.setText(postModel.getAuthor());

        if(postModel.getAuthorImageUrl() != null){
            Glide.with(context).load(postModel.getAuthorImageUrl())
                    .transform(new CircleTransform(context)).into(holder.post_author_photo);
        }
        else {
            Glide.with(context).load(R.drawable.ic_person_black_24dp)
                    .transform(new CircleTransform(context)).into(holder.post_author_photo);
        }

        if(postModel.getBody() != null ) holder.body.setText(postModel.getBody());
        else holder.body.setText("");

        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, postModel.getBody(), Toast.LENGTH_SHORT).show();
             //   Intent intent = new Intent(context, PostDetailActivity.class);
             //   intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, mKeysList.get(position));
             //   context.startActivity(intent);

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void addAllEvents(ArrayList<PostModel> events) {
        mEventsList.clear();
        mEventsList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public void addAllKeys(ArrayList<String> keys) {
        mKeysList.clear();
        mKeysList.addAll(keys);
    }

    @Override
    public void request() {
        presenter.request();
    }
}
