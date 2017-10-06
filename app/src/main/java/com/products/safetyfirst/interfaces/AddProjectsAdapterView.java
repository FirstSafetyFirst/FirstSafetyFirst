package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.Project_model;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public interface AddProjectsAdapterView {

    void addAll(ArrayList<Project_model> projects);

    void request(String mProfileKey);
}
