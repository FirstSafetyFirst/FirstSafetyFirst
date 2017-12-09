package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.Pojos.NewsModel;

import java.util.ArrayList;

/**
 * Created by vikas on 14/11/17.
 */

public interface NewsPresenter {
    void onDestroy();

    void request();

    void getChildren(ArrayList<NewsModel> news);

    void addAction(String mNewsId, String mProfileKey, int mAction);

    void onActionError(String message);

    void onActionSuccess(String message);

    void getKeys(ArrayList<String> newsArrayKey);
}
