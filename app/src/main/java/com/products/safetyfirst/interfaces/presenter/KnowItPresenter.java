package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.KnowItItem;
import com.products.safetyfirst.models.KnowItItemType;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface KnowItPresenter {

    void onDestroy();

    void getChildren(ArrayList<KnowItItem> items);

    void OnSuccess();

    void OnError();

    void request();

    void requestSpecificItem(String itemName);

    void setSpecificItem(KnowItItemType knowItItemType);
}
