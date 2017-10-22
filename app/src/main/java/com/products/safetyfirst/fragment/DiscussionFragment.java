package com.products.safetyfirst.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.products.safetyfirst.activity.NewPostActivity;
import com.products.safetyfirst.adapters.PostAdapter;
import com.products.safetyfirst.modelhelper.UserHelper;

import static com.products.safetyfirst.activity.HomeActivity.bottomNavigationView;
import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by profileconnect on 20/04/17.
 */

public class DiscussionFragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
    RecyclerView home_recycler;

    private DatabaseReference mDatabase;
    private ProgressBar mpaginateprogbar;
    private FloatingActionButton mFab;

    private UserHelper user;

    public DiscussionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discussion_fragment, container, false);

        mDatabase = getDatabase().getReference();
        mpaginateprogbar=(ProgressBar) rootView.findViewById(R.id.newspaginateprogbar);
        mFab = (FloatingActionButton) rootView.findViewById(R.id.new_post);


        home_recycler=(RecyclerView)rootView.findViewById(R.id.discussion_recycler);
        home_recycler.setHasFixedSize(true);

        //setting detection of scrolling
        home_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }
        });
        bottomNavigationView.setVisibility(View.VISIBLE);
        user = UserHelper.getInstance();
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
        home_recycler.setAdapter(new PostAdapter(getActivity(),postQuery, mDatabase, mpaginateprogbar));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(user.isSignedIn()) {
                Intent intent = new Intent(getContext(), NewPostActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(getView(), "Sign In first", Snackbar.LENGTH_LONG).show();
            }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
