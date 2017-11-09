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
import com.google.firebase.database.Query;
import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.NewsAdapter;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by profileconnect on 20/04/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public class News_Fragment extends Fragment {
    private static final String TAG = "NewsListFragment";
    private RecyclerView news_recycler;

    private DatabaseReference mDatabase;
    private ProgressBar mProgressBar;
    private ProgressBar mpaginateprogbar;

    public News_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        Bundle bundle=getArguments();
        if( bundle!=null && bundle.getString("action").equals("bookmark")){
            //TODO:
        }

        mDatabase = getDatabase().getReference();
        mpaginateprogbar= rootView.findViewById(R.id.newspaginateprogbar);

        mProgressBar = rootView.findViewById(R.id.progressBar);

        news_recycler = rootView.findViewById(R.id.news_recycler);
        news_recycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        news_recycler.setLayoutManager(mLayoutManager);
        news_recycler.setItemAnimator(new DefaultItemAnimator());

        Query newsQuery =  mDatabase.child("news").orderByKey().limitToLast(10);
        news_recycler.setAdapter(new NewsAdapter(getActivity(),newsQuery, mDatabase, mpaginateprogbar));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
