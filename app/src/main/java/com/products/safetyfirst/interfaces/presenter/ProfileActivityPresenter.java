package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 07/10/17.
 */

public interface ProfileActivityPresenter {

    void addFollower(String currentUserId, String FollowedUserId);

    void onDestroy();

    void getRequestedUser(UserModel user);

    void requestUser(String mProfileKey);

    void onFollowError();

    void onFollowSuccess();

}
