package com.products.safetyfirst.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.interfaces.adapter.KnowItAdapterView;
import com.products.safetyfirst.models.KnowItItem;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

public class KnowItAdapter extends RecyclerView.Adapter<KnowItAdapter.ViewHolder> implements KnowItAdapterView{


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void addAllItems(ArrayList<KnowItItem> items) {

    }

    @Override
    public void request(String mProfileKey) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
