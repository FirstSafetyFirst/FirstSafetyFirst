package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.ProfileActivityInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.ProfileActivityInteractor;
import com.products.safetyfirst.interfaces.presenter.ProfileActivityPresenter;
import com.products.safetyfirst.interfaces.view.ProfileActivityView;
import com.products.safetyfirst.Pojos.UserModel;

/**
 * Created by vikas on 07/10/17.
 */

public class ProfileActivityPresenterImpl implements ProfileActivityPresenter, ProfileActivityInteractor.OnUpdateFinishedListener {

    private ProfileActivityView profileActivityView;
    private final ProfileActivityInteractor profileActivityInteractor;
    private String currentUserId, followedUserId;

    public ProfileActivityPresenterImpl(ProfileActivityView profileActivityView) {
        this.profileActivityView = profileActivityView;
        this.profileActivityInteractor = new ProfileActivityInteractorImpl(this);
    }

    @Override
    public void addFollower(String currentUserId, String followedUserId) {
        this.currentUserId = currentUserId;
        this.followedUserId = followedUserId;
        profileActivityInteractor.addFollower(currentUserId, followedUserId, this);

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
    public void onFollowSuccess() {
        profileActivityView.onFollowSuccess();
    }

    @Override
    public void onSuccess() {
        profileActivityView.onSuccess();
    }
}
