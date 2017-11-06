package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.presenter.AddProjectPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.AddProjectsAdapterView;
import com.products.safetyfirst.models.Project_model;
import com.products.safetyfirst.viewholder.ProjectsViewHolder;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsViewHolder> implements AddProjectsAdapterView {

    private final ArrayList<Project_model> mProjectsList = new ArrayList<>();
    private final AddProjectPresenterImpl presenter;
    private Context context;

    public ProjectsAdapter(Context context) {
        this.presenter = new AddProjectPresenterImpl(this);
        this.context = context;
    }


    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ProjectsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProjectsViewHolder holder, int position) {

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        Project_model current = mProjectsList.get(position);
        holder.mUserTextView.setText(current.getUsername());
        holder.mComapnyTextView.setText(current.getCompany());
        holder.mDescriptionTextView.setText(current.getDescription());
    }

    @Override
    public int getItemCount() {
        return mProjectsList.size();
    }

    @Override
    public void addAll(ArrayList<Project_model> projects) {
        mProjectsList.clear();
        mProjectsList.addAll(projects);
        notifyDataSetChanged();
    }

    @Override
    public void request(String mProfileKey) {
        presenter.request(mProfileKey);
    }


    }

