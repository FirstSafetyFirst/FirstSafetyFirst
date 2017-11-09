package com.products.safetyfirst.interfaces.presenter;

import java.util.ArrayList;

/**
 * Created by vikas on 25/10/17.
 */

public interface TrainingPresenter {

    void onDestroy();

    void request();

    void onError(String message);

    void onSuccess(String message);

    void getKeys(ArrayList<String> trainingArrayKey);
}
