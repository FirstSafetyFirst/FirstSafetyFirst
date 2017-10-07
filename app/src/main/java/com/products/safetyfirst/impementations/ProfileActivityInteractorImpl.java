package com.products.safetyfirst.impementations;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.ProfileActivityInteractor;
import com.products.safetyfirst.interfaces.ProfileActivityPresenter;
import com.products.safetyfirst.models.UserModel;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 07/10/17.
 */

public class ProfileActivityInteractorImpl implements ProfileActivityInteractor {

    ProfileActivityPresenter presenter;

    public ProfileActivityInteractorImpl(ProfileActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addFollower(String followerUserId, String FollowedUserId, OnUpdateFinishedListener listener) {

    }

    @Override
    public void requestUser(String mProfileKey) {
        Query query;
        if (mProfileKey != null) {

            query = getDatabase().getReference()
                    .child("users").child(mProfileKey);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    presenter.getRequestedUser(user);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Add Project Interacter", "Could not fetch projects");
                }
            });


        }
    }
}
