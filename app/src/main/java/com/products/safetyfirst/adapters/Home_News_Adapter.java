package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.models.News_model;

import java.util.ArrayList;

public class Home_News_Adapter extends RecyclerView.Adapter<Home_News_Adapter.NewsViewholder> {

    private final Context context;
    private DatabaseReference mDatabase;
    private ArrayList<News_model> newsArrayList = new ArrayList<>();
    private ArrayList<String> newsArrayKey=new ArrayList<>();
    private Query newsquery;
    private String mLastkey;
    private ProgressBar mpaginateprogbar;
    ArrayList<News_model> getNews=new ArrayList<>();
    ArrayList<News_model> tempNews=new ArrayList<>();
    ArrayList<String> tempkeys=new ArrayList<>();
    ArrayList<String> getKeys=new ArrayList<>();

public class NewsViewholder extends RecyclerView.ViewHolder {

    private ImageView images,favicon, bookmark;
    private TextView title, timestamp;
    private Button detail;

    private NewsViewholder(View view) {

        super(view);
        images= (ImageView) view.findViewById(R.id.news_avtar);
        favicon= (ImageView) view.findViewById(R.id.favicon);
        bookmark = (ImageView) view.findViewById(R.id.bookmark);
        title = (TextView) view.findViewById(R.id.title);
        timestamp = (TextView) view.findViewById(R.id.dateTime);
        detail = (Button) view.findViewById(R.id.view_details);

    }
}


    public Home_News_Adapter(Context cont, Query newsquery,DatabaseReference mDatabase, ProgressBar mpaginateprogbar ) {
        this.context=cont;
        this.newsquery = newsquery;
        this.mDatabase = mDatabase;
        this.mpaginateprogbar = mpaginateprogbar;
        newsquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                newsArrayKey.add(dataSnapshot.getKey());
                newsArrayList.add(dataSnapshot.getValue(News_model.class));
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

    public void getMoreData(){
        tempkeys=newsArrayKey;
        tempNews=newsArrayList;
        Query Getmorenewsquery=mDatabase.child("news").orderByKey().endAt(mLastkey).limitToLast(10);
        Getmorenewsquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getNews.add(dataSnapshot.getValue(News_model.class));
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
    public NewsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final NewsViewholder holder, final int position) {

        Glide.with(context).load(newsArrayList.get(position).getImg_url()).transform(new CircleTransform(context)).into(holder.images);
        if(newsArrayList.get(position).getImg_url() != null)
        Glide.with(context).load(newsArrayList.get(position).getImg_url()).into(holder.favicon);

        holder.title.setText( newsArrayList.get(position).getTitle());
        holder.timestamp.setText("10 May, 2017");
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, newsArrayList.get(position).getImg_url(),Toast.LENGTH_SHORT).show();
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