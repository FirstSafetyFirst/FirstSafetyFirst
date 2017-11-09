package com.products.safetyfirst.fragment;

import android.content.Context;
import android.net.Uri;
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
import com.products.safetyfirst.adapters.TrainingAdapter;
import com.products.safetyfirst.impementations.presenter.TrainingPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.TrainingPresenter;
import com.products.safetyfirst.interfaces.view.TrainingView;


@SuppressWarnings({"ALL", "EmptyMethod"})
public class TrainingFragment extends Fragment implements TrainingView{

    private TrainingPresenter presenter;
    private TrainingAdapter adapter;
    private ProgressBar mProgressbar;
    private RecyclerView recycler;
    public TrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createUI(view);
    }

    private void createUI(View view) {
        recycler = view.findViewById(R.id.training_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mProgressbar = view.findViewById(R.id.newspaginateprogbar);

        presenter = new TrainingPresenterImpl(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        fillUI();
    }

    private void fillUI() {
        adapter = new TrainingAdapter(getContext());


        adapter.request();

        recycler.setAdapter(adapter);
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

    @SuppressWarnings("EmptyMethod")
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
