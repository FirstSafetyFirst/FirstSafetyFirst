package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.models.TrainingCenterModel;

import java.util.ArrayList;

/**
 * Created by vikas on 19/10/17.
 */

public interface TrainingMapView {

    void onError(String message);

    void onSuccess(ArrayList<TrainingCenterModel> trainingCenterModels);

    void showProgress();

    void hideProgress();

    void navigateToHome();


}
