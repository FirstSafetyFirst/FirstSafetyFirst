package com.products.safetyfirst.interfaces;

/**
 * Created by vikas on 04/10/17.
 */

public interface AddProjectPresenter {

    void validateCredentials(String name, String company, String designation);

    void onDestroy();

}
