package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 11/10/17.
 */

@SuppressWarnings("ALL")
public interface EventsInteractor {

    void requestEvents();

    void addAction(final String mEventId, final String mProfileKey, int mAction);

    @SuppressWarnings("EmptyMethod")
    interface  OnUpdateFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }

}
