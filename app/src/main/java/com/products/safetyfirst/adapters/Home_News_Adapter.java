package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.models.News_model;

import java.util.List;

/**
 * Created by JHON on 02-Apr-17.
 */

public class Home_News_Adapter extends RecyclerView.Adapter<Home_News_Adapter.MyViewHolder> {

    private final Context context;
    private List<News_model> horizontalList;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView images,favicon;

    public MyViewHolder(View view) {

        super(view);
        images= (ImageView) view.findViewById(R.id.news_avtar);
        favicon= (ImageView) view.findViewById(R.id.favicon);

    }
}


    public Home_News_Adapter(Context cont, List<News_model> horizontalList) {
        this.horizontalList = horizontalList;
        this.context=cont;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String URL="http://fscl01.fonpit.de/userfiles/6727621/image/2016/Nougat/AndroidPIT-Android-N-Nougat-2480.jpg";
        String URL1="http://ndtvimages.yuppcdn.net/images/Sun_News_News_8748.jpg";

        Glide.with(context).load(URL).transform(new CircleTransform(context)).into(holder.favicon);


        Glide.with(context).load(URL1).into(holder.images);

        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, horizontalList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}