package com.products.safetyfirst.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.KnowItSecondActivity;
import com.products.safetyfirst.utils.JustifiedWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by profileconnect on 20/04/17.
 */

public class State_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.state_laws_fragment, container, false);
        JustifiedWebView laws_info = (JustifiedWebView) rootView.findViewById(R.id.law_info);
        RecyclerView stateLawsRecycler = (RecyclerView) rootView.findViewById(R.id.state_laws_recycler);
        FastItemAdapter lawsAdapter = new FastItemAdapter();
        List<LawItem> laws = new ArrayList<>();

        /*Set Laws Text*/
        laws_info.setText(getResources().getString(R.string.lorem_ipsum),  // Change here with text fetched from database
                "<span style=\" color: #2c3b42; font-size: 20px; \"><strong>"
                        + "State Laws"
                        + "</strong></span><hr>");

        stateLawsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        stateLawsRecycler.setAdapter(lawsAdapter);
        stateLawsRecycler.setNestedScrollingEnabled(false);

        //TODO: Add state laws dummy data
        laws.add(new LawItem("State 1", null));
        laws.add(new LawItem("State 2", null));
        laws.add(new LawItem("State 3", null));
        laws.add(new LawItem("State 4", null));

        lawsAdapter.add(laws);

        lawsAdapter.withSelectable(true);
        lawsAdapter.withOnClickListener(new FastAdapter.OnClickListener<State_Fragment.LawItem>() {
            @Override
            public boolean onClick(View v, IAdapter<State_Fragment.LawItem> adapter, State_Fragment.LawItem item, int position) {
                Intent intent = new Intent(getContext(), KnowItSecondActivity.class);
                intent.putExtra(KnowIt_Fragment.tool, position);
                startActivity(intent);
                return true;
            }
        });

        return rootView;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public View mainView;

        public ViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.know_it_item);
            title = (TextView) view.findViewById(R.id.know_it_item_title);
            image = (ImageView) view.findViewById(R.id.know_it_item_image);
        }
    }

    private class LawItem extends AbstractItem<State_Fragment.LawItem, State_Fragment.ViewHolder> {

        private String title;
        private Drawable image;

        LawItem(String title, Drawable image){
            this.title = title;
            this.image = image;
        }

        @Override
        public State_Fragment.ViewHolder getViewHolder(View v) {
            return new State_Fragment.ViewHolder(v);
        }

        @Override
        public int getType() {
            return R.id.know_it_item;
        }

        @Override
        public int getLayoutRes() {
            return R.layout.know_it_item;
        }

        @Override
        public void bindView(State_Fragment.ViewHolder holder, List<Object> payloads) {
            super.bindView(holder, payloads);

            holder.title.setText(title);
            //holder.image.setImageDrawable(image);
        }

        @Override
        public void unbindView(State_Fragment.ViewHolder holder) {
            super.unbindView(holder);

            holder.title.setText(null);
            //holder.image.setImageDrawable(null);
        }
    }

}
