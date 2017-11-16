package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
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
        holder.setData(postModel, mKeysList.get(position));

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
