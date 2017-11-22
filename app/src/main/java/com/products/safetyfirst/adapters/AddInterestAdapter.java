package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.presenter.AddInterestPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.AddInterestAdapterView;
import com.products.safetyfirst.Pojos.InterestModel;
import com.products.safetyfirst.viewholder.AddInterestViewHolder;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public class AddInterestAdapter extends RecyclerView.Adapter<AddInterestViewHolder> implements AddInterestAdapterView {

    private final ArrayList<InterestModel> mInterestList = new ArrayList<>();
    private final AddInterestPresenterImpl presenter;
    private final Context context;

    public AddInterestAdapter(Context context) {
        this.presenter = new AddInterestPresenterImpl(this);
        this.context = context;
    }

    @Override
    public AddInterestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, parent, false);
        return new AddInterestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddInterestViewHolder holder, int position) {
       // Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
       // holder.mView.startAnimation(animation);

        final InterestModel current = mInterestList.get(position);
        holder.mInterest.setText(current.getInterest());
        holder.mCheck.setChecked(current.getLiked());

        holder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateInterest(new InterestModel(current.getInterest(), !current.getLiked()));
            }
        });
        // holder.mIcon.setImageDrawable(current.getDrawable());
    }

    @Override
    public int getItemCount() {
        return mInterestList.size();
    }

    @Override
    public void addAll(ArrayList<InterestModel> interest) {
        mInterestList.clear();
        mInterestList.addAll(interest);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.request();
    }


}
