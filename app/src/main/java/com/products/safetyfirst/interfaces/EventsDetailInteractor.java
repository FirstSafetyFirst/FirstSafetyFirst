package com.products.safetyfirst.interfaces;

/**
 * Created by vikas on 12/10/17.
 */

public interface EventsDetailInteractor {

    void requestEvent(String mEventKey);

    void setBookMark(String mEventKey);

    void setAction(String mEventKey, int actionId);

    interface  OnUpdateFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }
}
