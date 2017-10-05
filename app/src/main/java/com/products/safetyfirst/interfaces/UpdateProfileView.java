package com.products.safetyfirst.interfaces;

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

}
