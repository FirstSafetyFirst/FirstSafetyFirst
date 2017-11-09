package com.products.safetyfirst.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.MapsActivity;
import com.products.safetyfirst.impementations.presenter.TrainingPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.TrainingAdapterView;
import com.products.safetyfirst.interfaces.presenter.TrainingPresenter;
import com.products.safetyfirst.viewholder.TrainingViewHolder;

import java.util.ArrayList;


/**
 * Created by vikas on 25/10/17.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingViewHolder> implements TrainingAdapterView {

    private final TrainingPresenter presenter;
    private final Context context;
    private final ArrayList<String> mKeysList = new ArrayList<>();

    public TrainingAdapter(Context context){
        this.presenter = new TrainingPresenterImpl(this);
        this.context = context;
    }

    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_list_item, parent, false);

        return new TrainingViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(TrainingViewHolder holder, final int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        holder.type.setText(mKeysList.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra(MapsActivity.EXTRA_CENTER_KEY, mKeysList.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mKeysList.size();
    }

    @Override
    public void addAllKeys(ArrayList<String> keys) {
        mKeysList.clear();
        mKeysList.addAll(keys);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.request();
    }

}
