package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.presenter.AddInterestPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.AddInterestAdapterView;
import com.products.safetyfirst.models.Interest_model;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public class AddInterestAdapter extends RecyclerView.Adapter<AddInterestAdapter.ViewHolder> implements AddInterestAdapterView {

    private final ArrayList<Interest_model> mInterestList = new ArrayList<>();
    private final AddInterestPresenterImpl presenter;
    private Context context;

    public AddInterestAdapter(Context context) {
        this.presenter = new AddInterestPresenterImpl(this);
        this.context = context;
    }

    @Override
    public AddInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, parent, false);
        return new AddInterestAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddInterestAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        Interest_model current = mInterestList.get(position);
        holder.mInterest.setText(current.getInterest());
        holder.mCheck.setChecked(current.getLiked());
        // holder.mIcon.setImageDrawable(current.getDrawable());
    }

    @Override
    public int getItemCount() {
        return mInterestList.size();
    }

    @Override
    public void addAll(ArrayList<Interest_model> interest) {
        mInterestList.clear();
        mInterestList.addAll(interest);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.request();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private ImageView mIcon;
        private TextView mInterest;
        private CheckBox mCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mInterest = (TextView) itemView.findViewById(R.id.interest);
            mCheck = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
