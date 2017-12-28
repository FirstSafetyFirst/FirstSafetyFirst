package com.products.safetyfirst.impementations.Interactor;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.products.safetyfirst.Pojos.ProjectModel;
import com.products.safetyfirst.interfaces.interactor.AddProjectInteractor;
import com.products.safetyfirst.interfaces.presenter.AddProjectPresenter;
import com.products.safetyfirst.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 04/10/17.
 */

@SuppressWarnings("ConstantConditions")
public class AddProjectInteractorImpl implements AddProjectInteractor {

    private final AddProjectPresenter presenter;

    public AddProjectInteractorImpl(AddProjectPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void addProject(String name, String company, String description,String years, String evaluation, OnUpdateFinishedListener listener) {
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
        if (TextUtils.isEmpty(years)) {
            listener.onDescriptionError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(evaluation)) {
            listener.onDescriptionError();
            error = true;
            return;
        }

        if (!error) {
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("company", company);
            childUpdates.put("description", description);
            childUpdates.put("designation", name);
            childUpdates.put("years", years);
            childUpdates.put("evaluation", evaluation);
            childUpdates.put("timestamp", ServerValue.TIMESTAMP);
            Map<String,Object> map= new HashMap<>();
            map.put(System.currentTimeMillis()+"",childUpdates);
            DocumentReference mProjectReference;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String mProfileKey = null;
            String mProjectKey = null;

            if (user != null) {
              mProfileKey= user.getUid();
              mProjectReference= DatabaseUtil.getFireStore().collection("user-projects").document(mProfileKey);
              mProjectKey= mProjectReference.getId();
              mProjectReference.set(map, SetOptions.merge());
            }
            listener.onSuccess();
        }
    }

    @Override
    public void requestProjects(String mProfileKey) {
        DocumentReference query;
        if ( mProfileKey != null) {
            query= DatabaseUtil.getFireStore().collection("user-projects").document(mProfileKey);
            query.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if(e!=null){
                       e.printStackTrace();
                    }
                    else if(documentSnapshot.exists()&& documentSnapshot!=null){
                        HashMap<String,Object> map= new HashMap<>(documentSnapshot.getData());
                        Object[] o= map.values().toArray();

                        if(map!=null) {
                            ArrayList<ProjectModel> mListOfProjects = new ArrayList<>();
                            for (int i = 0; i< o.length; i++) {
                                mListOfProjects.add(new ProjectModel((HashMap<String,Object>)o[i]));
                            }
                            if(mListOfProjects.size() == 0){
                                presenter.noProjects();
                            }else {
                                presenter.getChildren(mListOfProjects);
                            }
                        }
                    }
                }
            });
        }


    }
}
