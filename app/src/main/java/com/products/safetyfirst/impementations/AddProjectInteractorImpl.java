package com.products.safetyfirst.impementations;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.products.safetyfirst.interfaces.AddProjectInteractor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 04/10/17.
 */

public class AddProjectInteractorImpl implements AddProjectInteractor {
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
            childUpdates.put("desription", description);
            childUpdates.put("name", name);
            DatabaseReference mProjectReference;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String mProfileKey = null;
            String mProjectKey = null;

            if (user != null) {
                mProfileKey = user.getUid();
                mProjectKey = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(mProfileKey).child("projects").push().getKey();
                mProjectReference = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(mProfileKey).child("projects").child(mProjectKey);
                mProjectReference.updateChildren(childUpdates);
            }
            listener.onSuccess();
        }
    }
}
