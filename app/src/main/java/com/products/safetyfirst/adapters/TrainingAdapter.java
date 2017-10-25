package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.MapsActivity;
import com.products.safetyfirst.impementations.presenter.TrainingPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.TrainingAdapterView;
import com.products.safetyfirst.interfaces.presenter.TrainingPresenter;

import java.util.ArrayList;


/**
 * Created by vikas on 25/10/17.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> implements TrainingAdapterView {

    private final TrainingPresenter presenter;
    private Context context;
    private final ArrayList<String> mKeysList = new ArrayList<>();

    public TrainingAdapter(Context context){
        this.presenter = new TrainingPresenterImpl(this);
        this.context = context;
    }

    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_list_item, parent, false);

        return new TrainingAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrainingAdapter.ViewHolder holder, final int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        public TextView type;


        public ViewHolder(View view) {

            super(view);
            mView = view;
            type = (TextView) view.findViewById(R.id.type);
        }
    }
}
