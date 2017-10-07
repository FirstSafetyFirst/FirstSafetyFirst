package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 04/10/17.
 */

public interface UpdateProfileView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPhoneError();

    void setCompanyError();

    void setDesignationdError();

    void setCertificateError();

    void setCityError();

    void navigateToHome();

    void setUser(UserModel user);

    void onSuccess();

    void onError();

}
