package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.Pojos.ProjectModel;

import java.util.ArrayList;

/**
 * Created by vikas on 04/10/17.
 */

public interface AddProjectPresenter {

    void validateCredentials(String name, String company, String designation, String years, String evaluation);

    void onDestroy();

    void getChildren(ArrayList<ProjectModel> projects);

    void request(String mProfileKey);

    void noProjects();

}
