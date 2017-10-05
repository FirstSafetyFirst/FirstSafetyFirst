package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 04/10/17.
 */

public interface UpdateProfileInteractor {

    void updateProfile(String name, String phone, String company, String designation, String certificate, String city, OnUpdateFinishedListener listener);

    UserModel getProfile();

    interface OnUpdateFinishedListener {
        void onUsernameError();

        void onPhoneError();

        void onCompanyError();

        void onDesignationdError();

        void onCertificateError();

        void onCityError();

        void onSuccess();
    }

}
