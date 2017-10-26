package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.TrainingInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.TrainingAdapterView;
import com.products.safetyfirst.interfaces.interactor.TrainingInteractor;
import com.products.safetyfirst.interfaces.presenter.TrainingPresenter;
import com.products.safetyfirst.interfaces.view.TrainingView;

import java.util.ArrayList;

/**
 * Created by vikas on 25/10/17.
 */

public class TrainingPresenterImpl implements TrainingPresenter {

    private TrainingView trainingView;
    private TrainingInteractor trainingInteractor;
    private TrainingAdapterView trainingAdapterView;

    public TrainingPresenterImpl(TrainingView trainingView){
        this.trainingView = trainingView;
        this.trainingInteractor = new TrainingInteractorImpl(this);
    }

    public TrainingPresenterImpl(TrainingAdapterView trainingAdapterView){
        this.trainingAdapterView = trainingAdapterView;
        this.trainingInteractor = new TrainingInteractorImpl(this);

    }

    @Override
    public void onDestroy() {
        trainingView = null;
    }

    @Override
    public void request() {
        trainingInteractor.requestTrainingCenterTypes();
    }

    @Override
    public void onError(String message) {
        if(trainingView != null){
            trainingView.onError(message);
        }
    }

    @Override
    public void onSuccess(String message) {
        if(trainingView != null){
            trainingView.onSuccess(message);
        }
    }

    @Override
    public void getKeys(ArrayList<String> trainingArrayKey) {
        if(trainingAdapterView != null){
            trainingAdapterView.addAllKeys(trainingArrayKey);
        }
    }
}
