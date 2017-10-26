package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.KnowItItem;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

public interface KnowItPresenter {

    void onDestroy();

    void getChildren(ArrayList<KnowItItem> items);

    void OnSuccess();

    void OnError();

    void request();
}
