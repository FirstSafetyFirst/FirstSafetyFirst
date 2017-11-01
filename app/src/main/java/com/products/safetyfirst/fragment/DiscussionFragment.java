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

public class DiscussionFragment extends Fragment implements PostView{
    public static final String ARG_TITLE = "arg_title";
    RecyclerView rvItems;

    private DatabaseReference mDatabase;
    private ProgressBar mpaginateprogbar;
    private FloatingActionButton mFab;

    private UserHelper user;
    private PostHelper postHelper;

    private EndlessRecyclerViewScrollListener scrollListener;

    private PostPresenter presenter;

    final List<PostModel> allPosts =  new ArrayList<>();
    final DiscussionAdapter adapter = new DiscussionAdapter(allPosts);

    public DiscussionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discussion_fragment, container, false);

        user = UserHelper.getInstance();
        postHelper = PostHelper.getInstance();

        mDatabase = getDatabase().getReference();
        mpaginateprogbar=(ProgressBar) rootView.findViewById(R.id.newspaginateprogbar);
        mFab = (FloatingActionButton) rootView.findViewById(R.id.new_post);


        rvItems =(RecyclerView)rootView.findViewById(R.id.discussion_recycler);
        rvItems.setHasFixedSize(true);

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

    @Override
    public void getInitialPosts(List<PostModel> initialPosts) {
        allPosts.addAll(initialPosts);


        rvItems.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvItems.setLayoutManager(linearLayoutManager);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                presenter.requestPostByKey("", page  );

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
    public void getNextPost(List<PostModel> posts) {
       // List<PostModel> morePosts = PostModel.createPostList(10, page);
        final int curSize = adapter.getItemCount();

        //allPosts.addAll(morePosts);
        allPosts.addAll(posts);
        adapter.notifyItemRangeInserted(curSize, allPosts.size() - 1);

       /* view.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemRangeInserted(curSize, allPosts.size() - 1);
            }
        });*/
    }
}
