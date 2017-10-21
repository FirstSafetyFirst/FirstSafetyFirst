package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.KnowItSecondActivity;
import com.products.safetyfirst.impementations.presenter.KnowItPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.KnowItAdapterView;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.models.KnowItItem;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

public class KnowItAdapter extends RecyclerView.Adapter<KnowItAdapter.ViewHolder> implements KnowItAdapterView{

    private Context context;
    private ArrayList<KnowItItem> knowItItemArrayList;
    private KnowItPresenter presenter;
    private KnowItItem knowItItem;

    public KnowItAdapter(Context context){
        this.context=context;
        presenter= new KnowItPresenterImpl(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.know_it_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        knowItItem = knowItItemArrayList.get(position);
        if(knowItItem.getItem_name()!=null)
            holder.title.setText(knowItItem.getItem_name());
        if(knowItItem.getThumb_url()!=null)
            Glide.with(context).load(knowItItem.getThumb_url()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return knowItItemArrayList.size();
    }

    @Override
    public void addAllItems(ArrayList<KnowItItem> items) {
          knowItItemArrayList.clear();
        knowItItemArrayList.addAll(items);
    }

    @Override
    public void request() {
        presenter.request();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView title;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            title= (TextView) itemView.findViewById(R.id.know_it_item_title);
            imageView= (ImageView) itemView.findViewById(R.id.know_it_item_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, KnowItSecondActivity.class);
                       intent.putExtra("KnowItItem",knowItItem);
                    context.startActivity(intent);
                }
            });
        }
    }
}
