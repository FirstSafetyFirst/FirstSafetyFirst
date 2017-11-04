package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.models.SliderModel;
import com.products.safetyfirst.viewholder.HomeSliderViewHolder;

import java.util.List;

/**
 * Created by JHON on 02-Apr-17.
 */

public class Home_Slider_Adapter extends RecyclerView.Adapter<HomeSliderViewHolder> {

    private final Context context;
    private List<SliderModel> horizontalList;

    public Home_Slider_Adapter(Context cont, List<SliderModel> horizontalList) {
        this.horizontalList = horizontalList;
        this.context=cont;
    }

    @Override
    public HomeSliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);

        return new HomeSliderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeSliderViewHolder holder, final int position) {

//        Glide.with(context).load("http://fscl01.fonpit.de/userfiles/6727621/image/2016/Nougat/AndroidPIT-Android-N-Nougat-2480.jpg").into(holder.slider_image);
//
//        holder.slider_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, horizontalList.get(position).getBody(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

}