package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.AddInterestInteractor;
import com.products.safetyfirst.interfaces.presenter.AddInterestPresenter;
import com.products.safetyfirst.models.Interest_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 05/10/17.
 */

public class AddInterestInteractorImpl implements AddInterestInteractor {

    private AddInterestPresenter presenter;

    public AddInterestInteractorImpl(AddInterestPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void addInterest(Interest_model interests, OnUpdateFinishedListener listener) {


        DatabaseReference mInterestReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            String mProfileKey = user.getUid();

            mInterestReference = getDatabase().getReference()
                    .child("user-interests").child(mProfileKey);

            Map<String , Object> data = new HashMap<>();
            if (interests != null)
             data.put(interests.getInterest(), interests.getLiked());
            mInterestReference.updateChildren(data);

        }
        listener.onSuccess();


    }

    @Override
    public void requestInterest() {

        Query query;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mProfileKey = null;

        if (user != null) {
            mProfileKey = user.getUid();

            query = FirebaseDatabase.getInstance().getReference()
                    .child("user-interests").child(mProfileKey);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Interest_model> mListOfInterests = new ArrayList<>();

                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        mListOfInterests.add(new Interest_model(x.getKey(), (Boolean) x.getValue()));
                    }
                    presenter.getChildren(mListOfInterests);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Add Project Interacter", "Could not fetch projects");
                }
            });


        }
    }
}
