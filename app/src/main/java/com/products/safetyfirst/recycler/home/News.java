package com.products.safetyfirst.recycler.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.products.safetyfirst.R;

/**
 * Created by profileconnect on 12/04/17.
 */

class News extends RecyclerView.ViewHolder {

    private RecyclerView news;

    public News(View v) {
        super(v);
        news = v.findViewById(R.id.news_holder);
    }


    public RecyclerView getNews() {
        return news;
    }

    public void setNews(RecyclerView newss) {
        this.news = newss;
    }
}