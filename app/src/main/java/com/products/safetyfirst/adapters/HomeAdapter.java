package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CustomLinearLayoutManager;
import com.products.safetyfirst.customview.SpacesItemDecoration;
import com.products.safetyfirst.models.News_model;
import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.models.Slider_Model;
import com.products.safetyfirst.models.Statics_model;
import com.products.safetyfirst.recycler.home.News;
import com.products.safetyfirst.recycler.home.Events;
import com.products.safetyfirst.recycler.home.Slider;
import com.products.safetyfirst.recycler.home.Statics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 02-Apr-17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;
    Context ctx;
    private final int SLIDER = 0, NEWS = 1,STATICS=2,EVENTS=3,PROGRESS=4;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter(Context ctx, List<Object> items) {
        this.items = items;
        this.ctx=ctx;
    }


    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Slider_Model ) {
            return SLIDER;
        } else if (items.get(position) instanceof News_model) {
            return NEWS;
        } else if (items.get(position) instanceof Statics_model){
            return STATICS;
        } else if(items.get(position) instanceof Event_model){
            return EVENTS;
        }
        return PROGRESS;
    }
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case SLIDER:
                View v1 = inflater.inflate(R.layout.slider_viewholder, parent, false);
                viewHolder = new Slider(v1);
                break;
            case NEWS:
                View v2 = inflater.inflate(R.layout.news_viewholder, parent, false);
                viewHolder = new News(v2);
                break;
            case STATICS:
                View v3 = inflater.inflate(R.layout.statics_viewholder, parent, false);
                viewHolder = new Statics(v3);
                break;
            case EVENTS:
                View v4 = inflater.inflate(R.layout.event_viewholder, parent, false);
                viewHolder = new Events(v4);
                break;
            case PROGRESS:
                View v5 = inflater.inflate(R.layout.progress, parent, false);
                viewHolder = new ViewHolder5(v5);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                //viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SLIDER:
                Slider vh1 = (Slider) holder;
                configureSlider(vh1, position);
                break;
            case NEWS:
                News vh2 = (News) holder;
                configureNEWS(vh2,position);
                break;
            case STATICS:
                Statics vh3 = (Statics) holder;
                configureStatics(vh3,position);
                break;
            case EVENTS:
                Events vh4 = (Events) holder;
                configureEvents(vh4,position);
                break;

        }
    }

    private void configureSlider(Slider vh1, int position) {
        List<Slider_Model> data=new ArrayList<>();
        data.add(new Slider_Model("a","b","c"));
        data.add(new Slider_Model("a","b","c"));
        data.add(new Slider_Model("a","b","c"));
        data.add(new Slider_Model("a","b","c"));
        vh1.getSliders().setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false);
        vh1.getSliders().setLayoutManager(mLayoutManager);
        vh1.getSliders().addItemDecoration(new SpacesItemDecoration(15,-1));
        Home_Slider_Adapter adapter=new Home_Slider_Adapter(ctx, data);
        vh1.getSliders().setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    private void configureNEWS(News vh2, int position) {

        List<News_model> data=new ArrayList<>();
        data.add(new News_model("aa","vbb","cc","ddd",9009));
        data.add(new News_model("aa","vbb","cc","ddd",9009));
        vh2.getNews().setHasFixedSize(true);
        vh2.getNews().setNestedScrollingEnabled(false);
        CustomLinearLayoutManager mLayoutManager = new CustomLinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
        vh2.getNews().setLayoutManager(mLayoutManager);
        Home_News_Adapter adapter=new Home_News_Adapter(ctx, data);
        vh2.getNews().setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void configureStatics(final Statics vh3,int position) {

        vh3.getStatics().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.getContext().startActivity(new Intent(ctx, Stores_Activity.class));
            }
        });

    }

    private void configureEvents(Events vh3,int position) {

        List<Event_model> data=new ArrayList<>();
        data.add(new Event_model("abc","abc","abc","abcx","abcz"));
        data.add(new Event_model("abc","abc","abc","abcx","abcz"));
        data.add(new Event_model("abc","abc","abc","abcx","abcz"));
        vh3.getEvents().setHasFixedSize(true);
        vh3.getEvents().setNestedScrollingEnabled(false);
        CustomLinearLayoutManager mLayoutManager = new CustomLinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
        vh3.getEvents().setLayoutManager(mLayoutManager);
        Home_Events_Adapter adapter =new Home_Events_Adapter(ctx, data);
        vh3.getEvents().setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }



    public class ViewHolder5 extends RecyclerView.ViewHolder {

        ProgressBar img;

        public ViewHolder5(View v) {
            super(v);
            img = (ProgressBar) v.findViewById(R.id.progressBar);

        }

    }
}
