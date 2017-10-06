package com.products.safetyfirst.impementations;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.UpdateProfileInteractor;
import com.products.safetyfirst.models.UserModel;

import java.util.HashMap;
import java.util.Map;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 04/10/17.
 */

public class UpdateProfileInteractorImpl implements UpdateProfileInteractor {

    @Override
    public void updateProfile(String name, String phone, String company, String designation, String certificate, String city, OnUpdateFinishedListener listener) {

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
        if (TextUtils.isEmpty(certificate)) {
            listener.onCertificateError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(city)) {
            listener.onCityError();
            error = true;
            return;
        }
        if (!error) {
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("company", company);
            childUpdates.put("designation", designation);
            childUpdates.put("city", city);
            childUpdates.put("certificate", certificate);
            DatabaseReference mProfileReference;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String mProfileKey = null;

            if (user != null) {
                mProfileKey = user.getUid();
                mProfileReference = getDatabase().getReference()
                        .child("users").child(mProfileKey);
                mProfileReference.updateChildren(childUpdates);
            }
            listener.onSuccess();
        }
    }

    @Override
    public UserModel getProfile() {

        DatabaseReference mProfileReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mProfileKey = null;

        final UserModel[] currentUser = new UserModel[1];

        if (user != null) {
            mProfileKey = user.getUid();

            mProfileReference = getDatabase().getReference()
                    .child("users").child(mProfileKey);

            mProfileReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    currentUser[0] = user;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return currentUser[0];
    }

}
