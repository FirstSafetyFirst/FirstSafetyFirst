package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 01/11/17.
 */

public interface PostInteractor {

    void requestPosts();

    void addAction(final String mEventId, final String mProfileKey, int mAction);

    @SuppressWarnings("EmptyMethod")
    interface  OnUpdateFinishedListener{
        void onError(String message);
        void onSuccess(String message);
    }

}
