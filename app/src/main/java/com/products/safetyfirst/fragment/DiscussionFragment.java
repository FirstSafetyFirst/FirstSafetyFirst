package com.products.safetyfirst.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.NewPostActivity;
import com.products.safetyfirst.adapters.DiscussionAdapter;
import com.products.safetyfirst.adapters.EndlessRecyclerViewScrollListener;
import com.products.safetyfirst.impementations.presenter.PostPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.interfaces.view.PostView;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.modelhelper.UserHelper;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;
import java.util.List;

import static com.products.safetyfirst.activity.HomeActivity.bottomNavigationView;
import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class DiscussionFragment extends Fragment implements PostView{
    public static final String ARG_TITLE = "arg_title";
    private RecyclerView rvItems;

    private DatabaseReference mDatabase;
    private ProgressBar mpaginateprogbar;
    private FloatingActionButton mFab;

    private UserHelper user;
    private PostHelper postHelper;

    private EndlessRecyclerViewScrollListener scrollListener;

    private PostPresenter presenter;

    private final List<PostModel> allPosts =  new ArrayList<>();
    private final DiscussionAdapter adapter = new DiscussionAdapter(allPosts, getActivity());
   // final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());


    public DiscussionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discussion_fragment, container, false);

        user = UserHelper.getInstance();
        postHelper = PostHelper.getInstance();

        mDatabase = getDatabase().getReference();
        mpaginateprogbar= rootView.findViewById(R.id.newspaginateprogbar);
        mFab = rootView.findViewById(R.id.new_post);


        rvItems = rootView.findViewById(R.id.discussion_recycler);
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter = new PostPresenterImpl(this);

        bottomNavigationView.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.requestFirstPosts();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void navigateToHome() {

    }

    private String lastPostKey = null;
    @Override
    public void getInitialPosts(List<PostModel> initialPosts, final String lastKey) {

        allPosts.addAll(initialPosts);

        rvItems.setAdapter(adapter);



        lastPostKey = lastKey;

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) rvItems.getLayoutManager()) {
            @Override
            public void onLoadMore( int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                String requestKey = lastPostKey;
                presenter.requestPostByKey(requestKey);
            }
        };



        rvItems.addOnScrollListener(scrollListener);

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
    public void getNextPost(List<PostModel> posts, String lastKey) {
        lastPostKey = lastKey;
        final int curSize = adapter.getItemCount();
        allPosts.addAll(posts);
        adapter.notifyItemRangeInserted(curSize, allPosts.size() - 1);
    }
}
