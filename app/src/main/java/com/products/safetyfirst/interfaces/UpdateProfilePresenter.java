package com.products.safetyfirst.interfaces;

import android.net.Uri;

import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 04/10/17.
 */

public interface UpdateProfilePresenter {

    void validateCredentials(String name, String phone, String company, String designation, String certificate, String city);

    void getProfile(UserModel user);

    void requestCurrentDetails();

    void onDestroy();

    void updatePhoto(Uri imagePath);


}
