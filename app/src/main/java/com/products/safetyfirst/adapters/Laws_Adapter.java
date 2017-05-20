package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.models.Knowit_model;
import com.products.safetyfirst.models.Law_model;
import com.products.safetyfirst.models.News_model;

import java.util.List;

/**
 * Created by JHON on 02-Apr-17.
 */

public class Laws_Adapter extends RecyclerView.Adapter<Laws_Adapter.MyViewHolder> {

    private final Context context;
    private List<Law_model> horizontalList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView images;

        public MyViewHolder(View view) {

            super(view);
            images= (ImageView) view.findViewById(R.id.know_img);

        }
    }


    public Laws_Adapter(Context cont, List<Law_model> horizontalList) {
        this.horizontalList = horizontalList;
        this.context=cont;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.law_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        Glide.with(context).load("http://fscl01.fonpit.de/userfiles/6727621/image/2016/Nougat/AndroidPIT-Android-N-Nougat-2480.jpg").into(holder.images);
//
//        holder.images.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, horizontalList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}