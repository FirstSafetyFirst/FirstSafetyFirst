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
import com.products.safetyfirst.adapters.HomeAdapter;
import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.models.News_model;
import com.products.safetyfirst.models.Slider_Model;

import java.util.ArrayList;

/**
 * Created by profileconnect on 20/04/17.
 */

public class Home_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
    private RecyclerView home_recycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        home_recycler=(RecyclerView)rootView.findViewById(R.id.home_recycler);
        home_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        home_recycler.setLayoutManager(mLayoutManager);
        home_recycler.setItemAnimator(new DefaultItemAnimator());
        home_recycler.setAdapter(new HomeAdapter(getActivity(),getSampleArrayList()));

        return rootView;
    }


    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new Slider_Model("Dany Targaryen", "Valyria",""));
        items.add(new News_model("Rob Stark", "Winterfell","","","TOI",99));
        items.add(new Event_model("","","","",""));
        items.add(new News_model("Tyrion Lanister", "King's Landing","","","TOI",99));
        items.add(new Event_model("Tyrion Lanister", "King's Landing","","",""));
        return items;
    }


}
