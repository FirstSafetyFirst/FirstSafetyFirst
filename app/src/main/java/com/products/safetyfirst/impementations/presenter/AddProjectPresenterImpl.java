package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.adapters.ProjectsAdapter;
import com.products.safetyfirst.impementations.Interactor.AddProjectInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.AddProjectInteractor;
import com.products.safetyfirst.interfaces.presenter.AddProjectPresenter;
import com.products.safetyfirst.interfaces.view.AddProjectView;
import com.products.safetyfirst.interfaces.adapter.AddProjectsAdapterView;
import com.products.safetyfirst.models.ProjectModel;

import java.util.ArrayList;

/**
 * Created by vikas on 04/10/17.
 */

public class AddProjectPresenterImpl implements AddProjectPresenter, AddProjectInteractor.OnUpdateFinishedListener {

    private AddProjectView addProjectView;
    private final AddProjectInteractor addProjectInteractor;

    private AddProjectsAdapterView adapterView;

    public AddProjectPresenterImpl(AddProjectView addProjectView) {
        this.addProjectView = addProjectView;
        this.addProjectInteractor = new AddProjectInteractorImpl(this);
    }

    public AddProjectPresenterImpl(ProjectsAdapter projectsAdapter) {
        this.adapterView = projectsAdapter;
        this.addProjectInteractor = new AddProjectInteractorImpl(this);
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
    public void getChildren(ArrayList<ProjectModel> projects) {
        adapterView.addAll(projects);
    }

    @Override
    public void request(String mProfileKey) {
        addProjectInteractor.requestProjects(mProfileKey);
    }

    @Override
    public void noProjects() {
        if(addProjectView != null)
            addProjectView.noProjects();
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
