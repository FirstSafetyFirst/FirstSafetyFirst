package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.Pojos.LawModel;
import com.products.safetyfirst.utils.Analytics;
import com.products.safetyfirst.viewholder.LawViewHolder;

import java.util.List;


/**
 * Created by JHON on 02-Apr-17.
 */

public class Laws_Adapter extends RecyclerView.Adapter<LawViewHolder> {

    private final Context context;
    private final List<LawModel> horizontalList;

    public Laws_Adapter(Context cont, List<LawModel> horizontalList) {
        this.horizontalList = horizontalList;
        this.context=cont;
    }

    @Override
    public LawViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.law_items, parent, false);

        return new LawViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LawViewHolder holder, final int position) {

//        Glide.with(context).load("http://fscl01.fonpit.de/userfiles/6727621/image/2016/Nougat/AndroidPIT-Android-N-Nougat-2480.jpg").into(holder.images);
//
//        holder.images.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, horizontalList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
//            }
//        });
        Analytics.logEventViewItem(context,Long.toString(holder.getItemId()),"national law"+position,"national law");
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

}