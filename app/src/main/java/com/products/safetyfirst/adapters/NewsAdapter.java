package com.products.safetyfirst.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.NewsDetailActivity;
import com.products.safetyfirst.models.NewsModel;
import com.products.safetyfirst.viewholder.NewsViewHolder;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private final Context context;
    private ArrayList<NewsModel> getNews = new ArrayList<>();
    private ArrayList<NewsModel> tempNews = new ArrayList<>();
    private ArrayList<String> tempkeys = new ArrayList<>();
    private ArrayList<String> getKeys = new ArrayList<>();
    private final DatabaseReference mDatabase;
    private ArrayList<NewsModel> newsArrayList = new ArrayList<>();
    private ArrayList<String> newsArrayKey=new ArrayList<>();
    private final Query newsquery;
    private String mLastkey;
    private final ProgressBar mpaginateprogbar;

    public NewsAdapter(Context cont, Query newsquery, DatabaseReference mDatabase, ProgressBar mpaginateprogbar ) {
        this.context=cont;
        this.newsquery = newsquery;
        this.mDatabase = mDatabase;
        this.mpaginateprogbar = mpaginateprogbar;
        newsquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                newsArrayKey.add(dataSnapshot.getKey());
                newsArrayList.add(dataSnapshot.getValue(NewsModel.class));
                notifyItemInserted(newsArrayList.size()-1);
                if(newsArrayList.size()==1){
                    mLastkey=dataSnapshot.getKey();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getMoreData(){
        tempkeys=newsArrayKey;
        tempNews=newsArrayList;
        Query Getmorenewsquery=mDatabase.child("news").orderByKey().endAt(mLastkey).limitToLast(10);
        Getmorenewsquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getNews.add(dataSnapshot.getValue(NewsModel.class));
                getKeys.add(dataSnapshot.getKey());
                if(getNews.size()==10){
                    getNews.remove(9);
                    getKeys.remove(9);
                    newsArrayList=getNews;
                    newsArrayKey=getKeys;
                    for(int i=0;i<tempNews.size();i++){
                        newsArrayList.add(tempNews.get(i));
                        newsArrayKey.add(tempkeys.get(i));
                    }
                    notifyItemRangeInserted(0,9);
                    mpaginateprogbar.setVisibility(View.GONE);
                    getKeys=new ArrayList<>();
                    getNews=new ArrayList<>();
                }
                if(getNews.size()==1){
                    mLastkey=dataSnapshot.getKey();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {

      //  Glide.with(context).load(newsArrayList.get(position).getImg_url()).into(holder.images);
        if(newsArrayList.get(position).getImgUrl() != null)
        Glide.with(context).load(newsArrayList.get(position).getImgUrl()).into(holder.images);

        holder.title.setText( newsArrayList.get(position).getTitle());
        holder.timestamp.setText("10 May, 2017");
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.EXTRA_NEWS_KEY, newsArrayKey.get(position));
                context.startActivity(intent);

            }
        });

        if (position==0){
            mpaginateprogbar.setVisibility(View.VISIBLE);
            getMoreData();
        }
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

}