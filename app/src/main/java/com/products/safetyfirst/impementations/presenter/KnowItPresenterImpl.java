package com.products.safetyfirst.impementations.presenter;

import android.util.Log;

import com.products.safetyfirst.adapters.KnowItAdapter;
import com.products.safetyfirst.impementations.Interactor.KnowItInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.KnowItAdapterView;
import com.products.safetyfirst.interfaces.interactor.KnowItInteractor;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.interfaces.view.KnowItView;
import com.products.safetyfirst.models.KnowItItem;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

public class KnowItPresenterImpl implements KnowItPresenter{
    private KnowItView knowItView;
    private KnowItInteractor knowItInteractor;
    private KnowItAdapterView knowItAdapterView;

    public KnowItPresenterImpl(KnowItView knowItView){
        this.knowItView=knowItView;
        this.knowItInteractor= new KnowItInteractorImpl(this);
    }

    public KnowItPresenterImpl(KnowItAdapter adapter){
     this.knowItAdapterView=adapter;
        this.knowItInteractor= new KnowItInteractorImpl(this);
    }
    @Override
    public void onDestroy() {
          knowItView=null;
    }

    @Override
    public void getChildren(ArrayList<KnowItItem> items) {
         knowItAdapterView.addAllItems(items);
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnError() {
        Log.e("KnowItPresenterImpl","Could not add all items.");
    }

    @Override
    public void request() {
        knowItInteractor.getKnowit();
    }

}
