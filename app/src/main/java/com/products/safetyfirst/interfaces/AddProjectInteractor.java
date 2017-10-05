package com.products.safetyfirst.interfaces;

/**
 * Created by vikas on 04/10/17.
 */

public interface AddProjectInteractor {

    void addProject(String name, String company, String designation, AddProjectInteractor.OnUpdateFinishedListener listener);

    void requestProjects();

    interface OnUpdateFinishedListener {

        void onUsernameError();

        void onCompanyError();

        void onDescriptionError();

        void onSuccess();
    }

}
