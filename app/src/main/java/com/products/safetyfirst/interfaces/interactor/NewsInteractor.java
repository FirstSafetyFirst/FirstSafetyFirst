package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 14/11/17.
 */

public interface NewsInteractor {

    void requestNews();

    void addAction(final String mEventId, final String mProfileKey, int mAction);

    @SuppressWarnings("EmptyMethod")
    interface  OnUpdateFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }

}
