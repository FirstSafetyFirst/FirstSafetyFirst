package com.products.safetyfirst.interfaces.view;

/**
 * Created by vikas on 11/10/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface EventsView {

    void onError(String message);

    void onSuccess(String message);

    void showProgress();

    void hideProgress();

    void navigateToHome();

}
