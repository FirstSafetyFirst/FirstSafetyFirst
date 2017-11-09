package com.products.safetyfirst.interfaces.interactor;

import com.products.safetyfirst.models.InterestModel;

/**
 * Created by vikas on 05/10/17.
 */

public interface AddInterestInteractor {

    void addInterest(InterestModel interests, AddInterestInteractor.OnUpdateFinishedListener listener);

    void requestInterest();

    interface OnUpdateFinishedListener {
        void onSuccess();
    }
}
