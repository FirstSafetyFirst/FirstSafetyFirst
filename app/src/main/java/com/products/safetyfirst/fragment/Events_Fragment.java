package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.EventsAdapter;
import com.products.safetyfirst.impementations.presenter.EventsPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.EventsPresenter;
import com.products.safetyfirst.interfaces.view.EventsView;

import static com.products.safetyfirst.utils.FirebaseUtils.getCurrentUserId;

/**
 * Created by profileconnect on 20/04/17.
 */

public class Events_Fragment extends Fragment implements EventsView {
    public static final String ARG_TITLE = "arg_title";

    private EventsPresenter presenter;
    private ProgressBar mProgressbar;
    private RecyclerView recycler;
    private EventsAdapter adapter;

    public Events_Fragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.news_fragment, container, false);

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
        adapter = new EventsAdapter(getContext(), getCurrentUserId());


            adapter.request();

        recycler.setAdapter(adapter);
    }

    private void createUI(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.news_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mProgressbar = (ProgressBar) view.findViewById(R.id.newspaginateprogbar);

        presenter = new EventsPresenterImpl(this);

    }
    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showProgress() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {

    }
}
