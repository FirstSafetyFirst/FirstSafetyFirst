package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 07/10/17.
 */

public interface ProfileActivityInteractor {

    void addFollower(String followerUserId, String FollowedUserId, ProfileActivityInteractor.OnUpdateFinishedListener listener);

    void requestUser(String mProfileKey);

    interface OnUpdateFinishedListener {

        void onFollowError();

        void onSuccess();
    }

}
