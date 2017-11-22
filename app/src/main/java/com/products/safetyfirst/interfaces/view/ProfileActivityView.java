package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.Pojos.UserModel;

/**
 * Created by vikas on 07/10/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface ProfileActivityView {

    void setViewWithUser(UserModel model);

    void showProgress();

    void hideProgress();

    void navigateToHome();

    void onFollowError();

    void onSuccess();

    void onFollowSuccess();


}
