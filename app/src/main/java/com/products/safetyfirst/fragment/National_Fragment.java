package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.Laws_Adapter;
import com.products.safetyfirst.customview.SpacesItemDecoration;
import com.products.safetyfirst.models.LawModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by profileconnect on 20/04/17.
 */

public class National_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
RecyclerView home_recycler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.national_fragment, container, false);
        List<LawModel> data=new ArrayList<>();
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        data.add(new LawModel("aa","vbb"));
        home_recycler=(RecyclerView)rootView.findViewById(R.id.national_recycler);
        home_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        home_recycler.setLayoutManager(mLayoutManager);
        home_recycler.addItemDecoration(new SpacesItemDecoration(10,2));

        home_recycler.setAdapter(new Laws_Adapter(getActivity(),data));

        return rootView;
    }

}
