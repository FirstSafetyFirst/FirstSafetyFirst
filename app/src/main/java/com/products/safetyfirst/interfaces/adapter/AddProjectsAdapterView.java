package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.Pojos.ProjectModel;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public interface AddProjectsAdapterView {

    void addAll(ArrayList<ProjectModel> projects);

    void request(String mProfileKey);
}
