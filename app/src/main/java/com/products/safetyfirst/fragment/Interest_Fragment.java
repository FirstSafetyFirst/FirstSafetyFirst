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
import android.widget.Button;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.AddInterestAdapter;
import com.products.safetyfirst.impementations.AddInterestPresenterImpl;
import com.products.safetyfirst.interfaces.AddInterestPresenter;
import com.products.safetyfirst.interfaces.AddInterestView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Interest_Fragment extends Fragment implements View.OnClickListener, AddInterestView {
    private RecyclerView recyclerView;
    private AddInterestAdapter adapter;
    private AddInterestPresenter presenter;
    private Button mUpdateBtn;
    public Interest_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interest, container, false);
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
        adapter = new AddInterestAdapter(getContext());
        adapter.request();
        recyclerView.setAdapter(adapter);
    }

    private void createUI(View view) {
        mUpdateBtn = (Button) view.findViewById(R.id.update);
        recyclerView = (RecyclerView) view.findViewById(R.id.interest_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new AddInterestPresenterImpl(this);

        mUpdateBtn.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.update) {
            //  presenter.validateInterest();
        }
    }

    @Override
    public void showInterestDialog() {

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
