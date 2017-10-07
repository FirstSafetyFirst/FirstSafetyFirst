package com.products.safetyfirst.interfaces;

import android.net.Uri;

/**
 * Created by vikas on 04/10/17.
 */

public interface UpdateProfileInteractor {

    void updateProfile(String name, String phone, String company, String designation, String certificate, String city, OnUpdateFinishedListener listener);

    void getProfile();

    void changeProfilePic(Uri imagepath, OnUpdateFinishedListener listener);

    interface OnUpdateFinishedListener {
        void onUsernameError();

        void onPhoneError();

        void onCompanyError();

        void onDesignationdError();

        void onCertificateError();

        void onCityError();

        void onSuccess();

        void onError();
    }

}
