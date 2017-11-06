package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public interface BookmarkInteractor {
     void requestItem(String type);
    interface  OnUpdateFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }
    void onRemoveBookMark();
}
