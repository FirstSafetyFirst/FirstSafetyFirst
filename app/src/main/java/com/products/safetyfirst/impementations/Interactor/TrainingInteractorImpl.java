package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.TrainingInteractor;
import com.products.safetyfirst.interfaces.presenter.TrainingPresenter;

import java.util.ArrayList;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 25/10/17.
 */

public class TrainingInteractorImpl implements TrainingInteractor {

    private TrainingPresenter presenter;

    public TrainingInteractorImpl(TrainingPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void requestTrainingCenterTypes() {
        Query query;

        query = getDatabase().getReference()
                .child("training");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> trainCenterTypes = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    trainCenterTypes.add(x.getKey());
                }
                presenter.getKeys(trainCenterTypes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Training Interacter", "Could not fetch centers");
            }
        });
    }
}
