package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.Pojos.InterestModel;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public interface AddInterestPresenter {

    void validateInterest(InterestModel interest);

    void onDestroy();

    void request();

    void getChildren(ArrayList<InterestModel> interests);
}
