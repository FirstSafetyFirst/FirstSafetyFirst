package com.products.safetyfirst.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.NewPostActivity;
import com.products.safetyfirst.adaptersnew.PostAdapter;
import com.products.safetyfirst.modelhelper.UserHelper;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionFragmentOld extends Fragment {
    private static final String TAG = "PostListFragment";
    private RecyclerView post_recycler;

    private DatabaseReference mDatabase;
    private ProgressBar mpaginateprogbar;
    private FloatingActionButton mFab;


    public DiscussionFragmentOld() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discussion_fragment, container, false);

        Bundle bundle=getArguments();
        if( bundle!=null && bundle.getString("action").equals("bookmark")){
            //TODO:
        }

        mDatabase = getDatabase().getReference();
        mpaginateprogbar= rootView.findViewById(R.id.newspaginateprogbar);

        mFab = rootView.findViewById(R.id.new_post);

        post_recycler = rootView.findViewById(R.id.discussion_recycler);
      //  post_recycler.setHasFixedSize(true);

        final UserHelper user  = UserHelper.getInstance();
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

        post_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                        mFab.hide();
                } else {
                        mFab.show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        post_recycler.setLayoutManager(mLayoutManager);
        post_recycler.setItemAnimator(new DefaultItemAnimator());

        //com.google.firebase.database.Query postQuery =  mDatabase.child("posts").orderByKey().limitToLast(10);
        Query query= FirebaseFirestore.getInstance().collection("posts");
       // post_recycler.setAdapter(new DiscussionAdapterOld(getActivity(),postQuery, mDatabase, mpaginateprogbar));
        post_recycler.setAdapter(new PostAdapter(new PostAdapter.OnPostSelectedListener() {
            @Override
            public void onPostSelected(DocumentSnapshot restaurant) {
                Snackbar.make(getView(),"Selected", BaseTransientBottomBar.LENGTH_LONG);
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
