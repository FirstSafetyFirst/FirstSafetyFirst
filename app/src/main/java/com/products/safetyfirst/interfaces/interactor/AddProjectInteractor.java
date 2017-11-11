package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 04/10/17.
 */

public interface AddProjectInteractor {

    void addProject(String name, String company, String designation,String years, String evaluation, AddProjectInteractor.OnUpdateFinishedListener listener);

    void requestProjects(String mProfileKey);

    interface OnUpdateFinishedListener {

        void onUsernameError();

        void onCompanyError();

        void onDescriptionError();

        void onSuccess();
    }

}
