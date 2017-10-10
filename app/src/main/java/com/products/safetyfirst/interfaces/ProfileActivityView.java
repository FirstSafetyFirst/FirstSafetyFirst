package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 07/10/17.
 */

public interface ProfileActivityView {

    void setViewWithUser(UserModel model);

    void showProgress();

    void hideProgress();

    void navigateToHome();

    void onFollowError();

    void onSuccess();

    void onFollowSuccess();


}
