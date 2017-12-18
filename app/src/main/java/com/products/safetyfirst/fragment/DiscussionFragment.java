package com.products.safetyfirst.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.google.firebase.firestore.DocumentSnapshot;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.NewPostActivity;
import com.products.safetyfirst.impementations.presenter.PostPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.interfaces.view.PostsView;
import com.products.safetyfirst.modelhelper.UserHelper;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class DiscussionFragment extends Fragment implements PostsView{
    public static final String ARG_TITLE = "arg_title";

    private PostPresenter presenter;
    private ProgressBar mProgressbar;
    private RecyclerView recycler;
    private com.products.safetyfirst.adaptersnew.PostAdapter adapter;
    private FloatingActionButton mFab;


    public DiscussionFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null && bundle.getString("action").equals("bookmark")){
            //TODO:
        }
        return inflater.inflate(R.layout.discussion_fragment, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createUI(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillUI();
    }

    private void fillUI() {

        adapter=new com.products.safetyfirst.adaptersnew.PostAdapter( new com.products.safetyfirst.adaptersnew.PostAdapter.OnPostSelectedListener() {
            @Override
            public void onPostSelected(DocumentSnapshot restaurant) {
                //TODO: do something here, till then this temporary snackbar
                Snackbar.make(getView(),"Selected", BaseTransientBottomBar.LENGTH_LONG);
            }
        });
        recycler.setAdapter(adapter);
    }

    private void createUI(View view) {
        recycler = view.findViewById(R.id.discussion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mFab = view.findViewById(R.id.new_post);

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

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if(dy+10 > adapter.getItemCount()){
                    adapter.makeNextSetOfQuery();
                }
            }
        });
       // mProgressbar = view.findViewById(R.id.newspaginateprogbar);

        presenter = new PostPresenterImpl(this);

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
}
