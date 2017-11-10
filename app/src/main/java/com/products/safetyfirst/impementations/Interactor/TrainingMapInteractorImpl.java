package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.TrainingMapInteractor;
import com.products.safetyfirst.interfaces.presenter.TrainingMapPresenter;
import com.products.safetyfirst.models.TrainingCenterModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 19/10/17.
 */

public class TrainingMapInteractorImpl implements TrainingMapInteractor {

    private final TrainingMapPresenter presenter;

    public TrainingMapInteractorImpl(TrainingMapPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void requestTrainingCenters(String mTrainingCenterType) {

          Query query = getDatabase().getReference()
                    .child("training").child(mTrainingCenterType);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<TrainingCenterModel> mListOfTrainingCenters = new ArrayList<>();

                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        mListOfTrainingCenters.add(x.getValue(TrainingCenterModel.class));
                    }

                    Collections.reverse(mListOfTrainingCenters);
                    presenter.getTrainingCenters(mListOfTrainingCenters);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Training Interacter", "Could not fetch centers");
                }
            });


    }

    @Override
    public void getContact(String TrainingCenterId) {

    }

    @Override
    public void saveLocation(String location) {

        if(location == null) return;

        DatabaseReference mInterestReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            String mProfileKey = user.getUid();

            mInterestReference = getDatabase().getReference()
                    .child("user-locations").child(location);

            Map<String , Object> data = new HashMap<>();

                data.put(mProfileKey , true);
            mInterestReference.updateChildren(data);

        }


    }
}
