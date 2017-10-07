package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 07/10/17.
 */

public interface ProfileActivityPresenter {

    void validateFollower(ProfileActivityView profileActivityView, String followerUserId, String FollowedUserId);

    void onDestroy();

    void getRequestedUser(UserModel user);

    void requestUser(String mProfileKey);

}
