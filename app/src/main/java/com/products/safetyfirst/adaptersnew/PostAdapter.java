package com.products.safetyfirst.adaptersnew;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.R;
import com.products.safetyfirst.androidhelpers.PostHelper;
import com.products.safetyfirst.viewholder.PostViewHolder;

/**
 * RecyclerView adapter for a list of Posts.
 */
public class PostAdapter extends FirestoreAdapter<PostViewHolder>{


    private static final int THRESHOLD = 10;
    private int total_count;

    public PostAdapter(PostHelper.NotifyAdapter notifyAdapter) {
        super(notifyAdapter);
        Log.e("PostAdapter","Post Adapter Constructor called");
        makeQuery();
        total_count = getItemCount();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int position) {

        final PostModel postModel= new PostModel(getSnapshot(position).getPostDocument().getData(),getSnapshot(position).getUserDocument().getData()) ;
        String key = getSnapshot(position).getPostDocument().getId();
        Log.e("Post key", key);
        holder.setData(postModel, key);
        if(position + THRESHOLD > getItemCount() && total_count <= getItemCount()){

            makeNextSetOfQuery();
            total_count= total_count + THRESHOLD;
        }

    }


}
