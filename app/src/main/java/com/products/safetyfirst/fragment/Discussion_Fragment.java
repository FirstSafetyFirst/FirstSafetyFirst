package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

    private DatabaseReference mDatabase;
    private ProgressBar mpaginateprogbar;

    public Discussion_Fragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discussion_fragment, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mpaginateprogbar=(ProgressBar) rootView.findViewById(R.id.newspaginateprogbar);


        home_recycler=(RecyclerView)rootView.findViewById(R.id.discussion_recycler);
        home_recycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        home_recycler.setLayoutManager(mLayoutManager);
        home_recycler.setItemAnimator(new DefaultItemAnimator());

        Query postQuery =  mDatabase.child("posts").orderByKey().limitToLast(10);
        home_recycler.setAdapter(new Discussion_Adapter(getActivity(),postQuery, mDatabase, mpaginateprogbar));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
