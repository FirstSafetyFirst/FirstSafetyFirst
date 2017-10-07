package com.products.safetyfirst.impementations;

import com.products.safetyfirst.interfaces.ProfileActivityInteractor;
import com.products.safetyfirst.interfaces.ProfileActivityPresenter;
import com.products.safetyfirst.interfaces.ProfileActivityView;
import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 07/10/17.
 */

public class ProfileActivityPresenterImpl implements ProfileActivityPresenter, ProfileActivityInteractor.OnUpdateFinishedListener {

    ProfileActivityView profileActivityView;
    ProfileActivityInteractor profileActivityInteractor;
    String currentUserId, followedUserId;

    public ProfileActivityPresenterImpl(ProfileActivityView profileActivityView) {
        this.profileActivityView = profileActivityView;
    }

    @Override
    public void validateFollower(ProfileActivityView profileActivityView, String currentUserId, String followedUserId) {
        this.currentUserId = currentUserId;
        this.followedUserId = followedUserId;
        this.profileActivityView = profileActivityView;
        this.profileActivityInteractor = new ProfileActivityInteractorImpl(this);
    }

    @Override
    public void onDestroy() {
        profileActivityView = null;
    }

    @Override
    public void getRequestedUser(UserModel user) {
        profileActivityView.setViewWithUser(user);
    }

    @Override
    public void requestUser(String mProfileKey) {
        profileActivityInteractor.requestUser(mProfileKey);
    }

    @Override
    public void onFollowError() {
        profileActivityView.onFollowError();
    }

    @Override
    public void onSuccess() {
        profileActivityView.onSuccess();
    }
}
