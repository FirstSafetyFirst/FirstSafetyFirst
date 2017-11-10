package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.TrainingMapInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.TrainingMapInteractor;
import com.products.safetyfirst.interfaces.presenter.TrainingMapPresenter;
import com.products.safetyfirst.interfaces.view.TrainingMapView;
import com.products.safetyfirst.models.TrainingCenterModel;

import java.util.ArrayList;

/**
 * Created by vikas on 19/10/17.
 */

public class TrainingMapPresenterImpl implements TrainingMapPresenter, TrainingMapInteractor.OnRequestFinishedListener {

    private TrainingMapView trainingMapView;
    private final TrainingMapInteractor trainingMapInteractor;

    public TrainingMapPresenterImpl(TrainingMapView trainingMapView){
        this.trainingMapView = trainingMapView;
        this.trainingMapInteractor = new TrainingMapInteractorImpl(this);
    }

    @Override
    public void onDestroy() {
        trainingMapView = null;
    }

    @Override
    public void request(String mTrainingCenterType) {
        trainingMapInteractor.requestTrainingCenters(mTrainingCenterType);
    }

    @Override
    public void getTrainingCenters(ArrayList<TrainingCenterModel> trainingCenterModels) {
        if( trainingMapView != null ){
            trainingMapView.onSuccess(trainingCenterModels);
            trainingMapView.hideProgress();
        }

    }

    @Override
    public void onRequestError(String message) {

        if( trainingMapView != null ){
            trainingMapView.hideProgress();
            trainingMapView.onError("");
        }

    }

    @Override
    public void onRequestSuccess(String message) {
        if( trainingMapView != null ){
            trainingMapView.hideProgress();
            //This will be used when actions are added to map view
        }
    }

    @Override
    public void saveLocation(String location) {
        if( trainingMapInteractor != null){
            trainingMapInteractor.saveLocation(location);
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }
}
