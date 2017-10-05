package com.products.safetyfirst.impementations;

import com.products.safetyfirst.interfaces.AddProjectInteractor;
import com.products.safetyfirst.interfaces.AddProjectPresenter;
import com.products.safetyfirst.interfaces.AddProjectView;

/**
 * Created by vikas on 04/10/17.
 */

public class AddProjectPresenterImpl implements AddProjectPresenter, AddProjectInteractor.OnUpdateFinishedListener {

    private AddProjectView addProjectView;
    private AddProjectInteractor addProjectInteractor;

    public AddProjectPresenterImpl(AddProjectView addProjectView) {
        this.addProjectView = addProjectView;
        this.addProjectInteractor = new AddProjectInteractorImpl();
    }

    @Override
    public void validateCredentials(String name, String company, String description) {
        if (addProjectView != null) {
            addProjectView.showProgress();
        }
        addProjectInteractor.addProject(name, company, description, this);
    }

    @Override
    public void onDestroy() {
        addProjectView = null;
    }

    @Override
    public void onUsernameError() {
        if (addProjectView != null) {
            addProjectView.setUsernameError();
            addProjectView.hideProgress();
        }
    }

    @Override
    public void onCompanyError() {
        if (addProjectView != null) {
            addProjectView.setCompanyError();
            addProjectView.hideProgress();
        }
    }

    @Override
    public void onDescriptionError() {
        if (addProjectView != null) {
            addProjectView.setDesignationError();
            addProjectView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (addProjectView != null) {
            addProjectView.hideProgress();
            addProjectView.hideProgress();
        }
    }
}
