package com.products.safetyfirst.interfaces;

/**
 * Created by vikas on 04/10/17.
 */

public interface AddProjectView {

    void showAddProjectDialog();

    void setCompanyError();

    void setUsernameError();

    void setDesignationError();

    void showProgress();

    void hideProgress();

    void navigateToHome();
}
