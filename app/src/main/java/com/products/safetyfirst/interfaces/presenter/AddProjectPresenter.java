package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.Project_model;

import java.util.ArrayList;

/**
 * Created by vikas on 04/10/17.
 */

public interface AddProjectPresenter {

    void validateCredentials(String name, String company, String designation);

    void onDestroy();

    void getChildren(ArrayList<Project_model> projects);

    void request(String mProfileKey);

}
