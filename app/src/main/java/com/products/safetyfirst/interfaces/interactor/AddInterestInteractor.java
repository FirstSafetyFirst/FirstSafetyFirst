package com.products.safetyfirst.interfaces.interactor;

import com.products.safetyfirst.models.Interest_model;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public interface AddInterestInteractor {

    void addInterest(ArrayList<Interest_model> interests, AddInterestInteractor.OnUpdateFinishedListener listener);

    void requestInterest();

    interface OnUpdateFinishedListener {
        void onSuccess();
    }
}
