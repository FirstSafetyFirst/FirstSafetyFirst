package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.Interest_model;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public interface AddInterestPresenter {

    void validateInterest(ArrayList<Interest_model> interest);

    void onDestroy();

    void request();

    void getChildren(ArrayList<Interest_model> interests);
}
