package com.products.safetyfirst.interfaces.view;

/**
 * Created by vikas on 14/11/17.
 */

public interface PostsView {

    void onError(String message);

    void onSuccess(String message);

    void showProgress();

    void hideProgress();

    void navigateToHome();

}
