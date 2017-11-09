package com.products.safetyfirst.interfaces.presenter;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 11/4/2017.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface BookmarkPresenter {
    void onDestroy();

    void getChildren(ArrayList<Object> items);

    void OnSuccess();

    void OnError();

}
