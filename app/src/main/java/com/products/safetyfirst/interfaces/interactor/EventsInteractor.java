package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsInteractor {

    void requestEvents();

    void addAction(final String mEventId, final String mProfileKey, int mAction);

    interface  OnUpdateFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }

}
