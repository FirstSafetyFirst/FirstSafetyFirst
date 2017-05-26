package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.Home_News_Adapter;
import com.products.safetyfirst.adapters.Laws_Adapter;
import com.products.safetyfirst.customview.SpacesItemDecoration;
import com.products.safetyfirst.models.Law_model;
import com.products.safetyfirst.models.News_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by profileconnect on 20/04/17.
 */

public class News_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
RecyclerView home_recycler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        List<News_model> data=new ArrayList<>();
        data.add(new News_model("Fu-5 things for Monday, April 24,North korea, Trump,Afganistan","vbb","cc","ddd","TOI",9009));
        data.add(new News_model("aa","vbb","cc","ddd","TOI",9009));
        data.add(new News_model("aa","vbb","cc","ddd","TOI",9009));
        data.add(new News_model("aa","vbb","cc","ddd","TOI",9009));
        data.add(new News_model("aa","vbb","cc","ddd","TOI",9009));
        data.add(new News_model("aa","vbb","cc","ddd","TOI",9009));

        home_recycler=(RecyclerView)rootView.findViewById(R.id.home_recycler);
        home_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        home_recycler.setLayoutManager(mLayoutManager);
        home_recycler.setItemAnimator(new DefaultItemAnimator());
        home_recycler.setAdapter(new Home_News_Adapter(getActivity(),data));
        return rootView;
    }

}
