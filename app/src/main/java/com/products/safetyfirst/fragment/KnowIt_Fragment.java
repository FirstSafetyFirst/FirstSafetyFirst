package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.KnowItAdapter;
import com.products.safetyfirst.impementations.presenter.KnowItPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.interfaces.view.KnowItView;
import com.products.safetyfirst.models.KnowItItemType;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class KnowIt_Fragment extends Fragment implements KnowItView{

    public final static String tool = "tool";

    private RecyclerView itemsRecycler;
    private KnowItPresenter presenter;
    private KnowItAdapter knowItAdapter;

    public KnowIt_Fragment(){
        //required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_know_it, container, false);

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
        knowItAdapter= new KnowItAdapter(getContext());


        knowItAdapter.request();

        itemsRecycler.setAdapter(knowItAdapter);
    }

    private void createUI(View view) {
        itemsRecycler = view.findViewById(R.id.know_it_recycler);
        itemsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setItemAnimator(new DefaultItemAnimator());
        presenter= new KnowItPresenterImpl(this);

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
    public void onSuccess() {

    }

    @Override
    public void onError() {
        Log.e("Know_ItFragment","Error in populating views");
    }

    @Override
    public void setViewWithSpecificItem(KnowItItemType knowItItemType) {
        
    }

}
