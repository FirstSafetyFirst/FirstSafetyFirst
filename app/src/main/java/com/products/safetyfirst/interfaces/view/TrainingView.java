package com.products.safetyfirst.interfaces.view;

/**
 * Created by vikas on 25/10/17.
 */

public interface TrainingView {
    void onError(String message);

    void onSuccess(String message);

    void showProgress();

    void hideProgress();

    void navigateToHome();
}
