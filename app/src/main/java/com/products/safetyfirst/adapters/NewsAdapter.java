package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.NewsDetailActivity;
import com.products.safetyfirst.impementations.presenter.NewsPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.NewsAdapterView;
import com.products.safetyfirst.Pojos.NewsModel;
import com.products.safetyfirst.viewholder.NewsViewHolder;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> implements NewsAdapterView{
    private final ArrayList<NewsModel> mEventsList = new ArrayList<>();
    private final ArrayList<String> mKeysList = new ArrayList<>();
    private final NewsPresenterImpl presenter;
    private final Context context;
    private final String mUserId;

    public NewsAdapter(Context context, String userId){
        this.presenter = new NewsPresenterImpl(this);
        this.context = context;
        this.mUserId = userId;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {

        final NewsModel event = mEventsList.get(position);

        if(event.getTitle() != null) holder.title.setText(event.getTitle());

        if(event.getImgUrl() != null){
            Glide.with(context).load(event.getImgUrl()).fitCenter().into(holder.images);
        }

        if(event.getNumViews() != NULL)
        holder.views.setText(String.valueOf(event.getNumViews()) + " Views");


        holder.newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.EXTRA_NEWS_KEY, mKeysList.get(position));
                context.startActivity(intent);

            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event.getDeeplink() != null) {
                    Intent intent = new Intent();
                    String msg = "Hey see this News: " + event.getDeeplink();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                    intent.setType("text/plain");
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void addAllEvents(ArrayList<NewsModel> events) {
        mEventsList.clear();
        mEventsList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public void addAllKeys(ArrayList<String> keys) {
        mKeysList.clear();
        mKeysList.addAll(keys);
    }

    @Override
    public void request() {
        presenter.request();
    }
}