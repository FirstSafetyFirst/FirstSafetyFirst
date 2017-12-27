package com.products.safetyfirst.impementations.Interactor;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.products.safetyfirst.Pojos.UserModel;
import com.products.safetyfirst.interfaces.interactor.UpdateProfileInteractor;
import com.products.safetyfirst.interfaces.presenter.UpdateProfilePresenter;
import com.products.safetyfirst.utils.DatabaseUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 04/10/17.
 */

public class UpdateProfileInteractorImpl implements UpdateProfileInteractor {

    private final UpdateProfilePresenter presenter;

    public UpdateProfileInteractorImpl(UpdateProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateProfile(String name, String phone, String company, String designation,
                              String certificate, String city, OnUpdateFinishedListener listener) {

        boolean error = false;
        if (TextUtils.isEmpty(name)) {
            listener.onUsernameError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            listener.onPhoneError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(company)) {
            listener.onCompanyError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(designation)) {
            listener.onDesignationdError();
            error = true;
            return;
        }
     /*   if (TextUtils.isEmpty(certificate)) {
            listener.onCertificateError();
            error = true;
            return;
        } */
        if (TextUtils.isEmpty(city)) {
            listener.onCityError();
            error = true;
            return;
        }
        if (!error) {
            Log.e("UpdateProfileInteractor", "going to update");
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("company", company);
            childUpdates.put("designation", designation);
            childUpdates.put("city", city);
           // childUpdates.put("certificate", certificate);
            childUpdates.put("phone",phone.toString());
            DocumentReference mProfileReference;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String mProfileKey = null;

            if (user != null) {

            mProfileKey = user.getUid();
            mProfileReference = DatabaseUtil.getFireStore().collection("users").document(mProfileKey);
            mProfileReference.set(childUpdates, SetOptions.merge());
            }
            listener.onSuccess();
        }
    }

    @Override
    public void getProfile() {

        DocumentReference mProfileReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mProfileKey = null;



        if (user != null) {
            mProfileKey = user.getUid();
            mProfileReference= DatabaseUtil.getFireStore().collection("users").document(mProfileKey);
            mProfileReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if(e!=null){
                      e.printStackTrace();
                    }
                    else if(documentSnapshot.exists()){
                        UserModel user = documentSnapshot.toObject(UserModel.class);
                        presenter.getProfile(user);
                    }
                }
            });
        }


    }

    @Override
    public void changeProfilePic(Uri imagepath, final OnUpdateFinishedListener listener) {

        StorageReference profilephotoRef;
        StorageReference mstorageRef = FirebaseStorage.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            profilephotoRef = mstorageRef.child(user.getUid() + "/ProfilePhoto.jpg");

            profilephotoRef.putFile(imagepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            listener.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            listener.onError();
                        }
                    });
        }


    }

}
