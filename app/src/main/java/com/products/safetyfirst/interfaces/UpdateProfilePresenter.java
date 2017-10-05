package com.products.safetyfirst.interfaces;

/**
 * Created by vikas on 04/10/17.
 */

public interface UpdateProfilePresenter {

    void validateCredentials(String name, String phone, String company, String designation, String certificate, String city);

    void onDestroy();

}
