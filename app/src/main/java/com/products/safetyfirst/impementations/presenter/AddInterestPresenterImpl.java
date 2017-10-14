package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.adapters.AddInterestAdapter;
import com.products.safetyfirst.impementations.Interactor.AddInterestInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.AddInterestAdapterView;
import com.products.safetyfirst.interfaces.interactor.AddInterestInteractor;
import com.products.safetyfirst.interfaces.presenter.AddInterestPresenter;
import com.products.safetyfirst.interfaces.view.AddInterestView;
import com.products.safetyfirst.models.Interest_model;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public class AddInterestPresenterImpl implements AddInterestPresenter, AddInterestInteractor.OnUpdateFinishedListener {

    private AddInterestView addInterestView;
    private AddInterestInteractor addInterestInteractor;

    private AddInterestAdapterView adapterView;

    public AddInterestPresenterImpl(AddInterestView addInterestView) {
        this.addInterestView = addInterestView;
        this.addInterestInteractor = new AddInterestInteractorImpl(this);
    }

    public AddInterestPresenterImpl(AddInterestAdapter addInterestAdapter) {
        this.adapterView = addInterestAdapter;
        this.addInterestInteractor = new AddInterestInteractorImpl(this);
    }

    @Override
    public void validateInterest(ArrayList<Interest_model> interest) {
        if (addInterestView != null) {
            addInterestView.showProgress();
        }
        addInterestInteractor.addInterest(interest, this);
    }

    @Override
    public void onDestroy() {
        addInterestView = null;
    }

    @Override
    public void request() {
        addInterestInteractor.requestInterest();
    }

    @Override
    public void getChildren(ArrayList<Interest_model> interests) {
        adapterView.addAll(interests);
    }


    @Override
    public void onSuccess() {

    }
}
