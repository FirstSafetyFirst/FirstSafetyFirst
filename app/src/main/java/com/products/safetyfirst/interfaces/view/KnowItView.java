package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.Pojos.KnowItItemType;

/**
 * Created by ishita sharma on 10/20/2017.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface KnowItView {

    void showProgress();

    void hideProgress();

    void navigateToHome();

    void onSuccess();

    void onError();

    void setViewWithSpecificItem(KnowItItemType knowItItemType);
}
