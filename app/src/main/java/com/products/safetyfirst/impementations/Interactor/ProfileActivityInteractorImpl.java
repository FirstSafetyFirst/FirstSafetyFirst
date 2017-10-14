package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.ProfileActivityInteractor;
import com.products.safetyfirst.interfaces.presenter.ProfileActivityPresenter;
import com.products.safetyfirst.models.UserModel;

import java.util.HashMap;
import java.util.Map;

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
    public void addFollower(final String currentUserId,final String followedUserId, OnUpdateFinishedListener listener) {

        DatabaseReference mPeopleRef = getDatabase().getReference().child("users");

        mPeopleRef.child(currentUserId).child("following").child(followedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> updatedUserData = new HashMap<>();
                if (dataSnapshot.exists()) {
                    // Already following, need to unfollow
                    updatedUserData.put("users/" + currentUserId + "/following/" + followedUserId, null);
                    updatedUserData.put("followers/" + followedUserId + "/" + currentUserId, null);
                } else {
                    updatedUserData.put("users/" + currentUserId + "/following/" + followedUserId, true);
                    updatedUserData.put("followers/" + followedUserId + "/" + currentUserId, true);
                }
                getDatabase().getReference().updateChildren(updatedUserData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {

                            Log.e("Follow error: ", firebaseError.getMessage());

                            presenter.onFollowError();
                        }else{
                            presenter.onFollowSuccess();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
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
                    Log.e("Add Project Interactor", "Could not fetch projects");
                }
            });


        }
    }
}
