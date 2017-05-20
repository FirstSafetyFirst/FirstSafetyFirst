package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.Discussion_Adapter;
import com.products.safetyfirst.adapters.Home_News_Adapter;
import com.products.safetyfirst.models.Discussion_model;
import com.products.safetyfirst.models.News_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by profileconnect on 20/04/17.
 */

public class Discussion_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
    RecyclerView home_recycler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discussion_fragment, container, false);

        List<Discussion_model> data=new ArrayList<>();
        data.add(new Discussion_model("aa","vbb","cc","ddd",9009));
        data.add(new Discussion_model("aa","vbb","cc","ddd",9009));
        data.add(new Discussion_model("aa","vbb","cc","ddd",9009));
        data.add(new Discussion_model("aa","vbb","cc","ddd",9009));
        data.add(new Discussion_model("aa","vbb","cc","ddd",9009));
        data.add(new Discussion_model("aa","vbb","cc","ddd",9009));

        home_recycler=(RecyclerView)rootView.findViewById(R.id.discussion_recycler);
        home_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        home_recycler.setLayoutManager(mLayoutManager);
        home_recycler.setItemAnimator(new DefaultItemAnimator());
        home_recycler.setAdapter(new Discussion_Adapter(getActivity(),data));
        return rootView;
    }

}
