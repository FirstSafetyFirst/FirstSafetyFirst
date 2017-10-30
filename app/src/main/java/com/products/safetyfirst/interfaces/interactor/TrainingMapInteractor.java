package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 19/10/17.
 */

public interface TrainingMapInteractor {

    void requestTrainingCenters(String mTrainingCenterType);

    void getContact(String TrainingCenterId);

    interface  OnRequestFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }


}
