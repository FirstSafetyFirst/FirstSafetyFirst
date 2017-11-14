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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.EventsAdapter;
import com.products.safetyfirst.adapters.NewsAdapter;
import com.products.safetyfirst.impementations.presenter.EventsPresenterImpl;
import com.products.safetyfirst.impementations.presenter.NewsPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.EventsPresenter;
import com.products.safetyfirst.interfaces.presenter.NewsPresenter;
import com.products.safetyfirst.interfaces.view.NewsView;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;
import static com.products.safetyfirst.utils.FirebaseUtils.getCurrentUserId;

/**
 * Created by profileconnect on 20/04/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public class News_Fragment extends Fragment implements NewsView{
    private static final String TAG = "NewsListFragment";
    private NewsPresenter presenter;
    private ProgressBar mProgressbar;
    private RecyclerView recycler;
    private NewsAdapter adapter;

    public News_Fragment() {}

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
        adapter = new NewsAdapter(getContext(), getCurrentUserId());


        adapter.request();

        recycler.setAdapter(adapter);
    }

    private void createUI(View view) {
        recycler = view.findViewById(R.id.news_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mProgressbar = view.findViewById(R.id.newspaginateprogbar);

        presenter = new NewsPresenterImpl(this);

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
}
