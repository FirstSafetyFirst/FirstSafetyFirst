package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.KnowItSecondActivity;
import com.products.safetyfirst.impementations.presenter.KnowItPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.KnowItAdapterView;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.Pojos.KnowItItem;
import com.products.safetyfirst.utils.Analytics;
import com.products.safetyfirst.viewholder.KnowItViewHolder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

public class KnowItAdapter extends RecyclerView.Adapter<KnowItViewHolder> implements KnowItAdapterView{

    private final Context context;
    private final ArrayList<KnowItItem> knowItItemArrayList=new ArrayList<>();
    private final KnowItPresenter presenter;


    public KnowItAdapter(Context context){
        this.context=context;
        presenter= new KnowItPresenterImpl(this);
    }

    @Override
    public KnowItViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.know_it_item, parent, false);

        return new KnowItViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final KnowItViewHolder holder, final int position) {
        URL url=null;
        final KnowItItem knowItItem = knowItItemArrayList.get(position);
        if(knowItItem.getItem_name()!=null)
            holder.title.setText(knowItItem.getItem_name());
        if(knowItItem.getThumb_url()!=null) {
            try {
                url= new URL(knowItItem.getThumb_url());
            } catch (MalformedURLException e){
                Log.e("KnowItAdapter","Error in url");
            }
            Glide.with(context).load(url).into(holder.imageView);
        }
        holder.mView.isClickable();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, KnowItSecondActivity.class);

                intent.putExtra("Name", knowItItem.getItem_name());
                intent.putExtra("Info", knowItItem.getItem_info());
                intent.putExtra("Checklist", knowItItem.getSafety_checklist());
                intent.putExtra("KnowItItemList",knowItItem.getTypes());
                Analytics.logEventViewItem(context,Long.toString(holder.getItemId()),knowItItem.getItem_name(),"know_it_item");
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return knowItItemArrayList.size();
    }

    @Override
    public void addAllItems(ArrayList<KnowItItem> items) {
        knowItItemArrayList.clear();
        knowItItemArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.request();
    }

}
