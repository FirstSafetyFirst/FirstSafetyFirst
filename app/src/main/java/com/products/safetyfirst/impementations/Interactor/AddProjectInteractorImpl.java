package com.products.safetyfirst.impementations.Interactor;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.AddProjectInteractor;
import com.products.safetyfirst.interfaces.presenter.AddProjectPresenter;
import com.products.safetyfirst.models.Project_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 04/10/17.
 */

public class AddProjectInteractorImpl implements AddProjectInteractor {

    private AddProjectPresenter presenter;

    public AddProjectInteractorImpl(AddProjectPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void addProject(String name, String company, String description, OnUpdateFinishedListener listener) {
        boolean error = false;
        if (TextUtils.isEmpty(name)) {
            listener.onUsernameError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(company)) {
            listener.onCompanyError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(description)) {
            listener.onDescriptionError();
            error = true;
            return;
        }

        if (!error) {
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("company", company);
            childUpdates.put("description", description);
            childUpdates.put("username", name);
            childUpdates.put("timestamp", ServerValue.TIMESTAMP);
            DatabaseReference mProjectReference;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String mProfileKey = null;
            String mProjectKey = null;

            if (user != null) {
                mProfileKey = user.getUid();
                mProjectKey = getDatabase().getReference()
                        .child("user-projects").child(mProfileKey).push().getKey();
                mProjectReference = FirebaseDatabase.getInstance().getReference()
                        .child("user-projects").child(mProfileKey).child(mProjectKey);
                mProjectReference.updateChildren(childUpdates);
            }
            listener.onSuccess();
        }
    }

    @Override
    public void requestProjects(String mProfileKey) {
        Query query;
        if ( mProfileKey != null) {

            query = getDatabase().getReference()
                    .child("user-projects").child(mProfileKey).orderByChild("timestamp");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Project_model> mListOfProjects = new ArrayList<>();

                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        mListOfProjects.add(x.getValue(Project_model.class));
                    }

                    Collections.reverse(mListOfProjects);
                    presenter.getChildren(mListOfProjects);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Add Project Interacter", "Could not fetch projects");
                }
            });


        }


    }
}
