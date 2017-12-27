package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.products.safetyfirst.Pojos.InterestModel;
import com.products.safetyfirst.interfaces.interactor.AddInterestInteractor;
import com.products.safetyfirst.interfaces.presenter.AddInterestPresenter;
import com.products.safetyfirst.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 05/10/17.
 */

public class AddInterestInteractorImpl implements AddInterestInteractor {

    private final AddInterestPresenter presenter;

    public AddInterestInteractorImpl(AddInterestPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void addInterest(InterestModel interests, final OnUpdateFinishedListener listener) {


        DocumentReference mInterestReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            String mProfileKey = user.getUid();

            mInterestReference = DatabaseUtil.getFireStore()
                    .collection("users-interests").document(mProfileKey);

            Map<String , Object> data = new HashMap<>();
            if (interests != null)
             data.put(interests.getInterest(), interests.getLiked());
             mInterestReference.update(data)
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     Log.v("AddInterestInteractor","Interests added");
                     listener.onSuccess();
                 }
             });

        }
        //listener.onSuccess();


    }

    @Override
    public void requestInterest() {

        DocumentReference query;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mProfileKey;

        if(user!=null){
            mProfileKey= user.getUid();

            query= DatabaseUtil.getFireStore().collection("users-interests").document(mProfileKey);

            query.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.v("AddInterestInteractor", "Listen failed.", e);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {

                        ArrayList<InterestModel>mListofInterests = new ArrayList<>();
                        Object[] keys= documentSnapshot.getData().keySet().toArray();
                        Object[] values= documentSnapshot.getData().values().toArray();
                        for(int i=0; i<keys.length;i++){
                            mListofInterests.add(new InterestModel(keys[i].toString(),(Boolean) values[i]));
                        }
                        presenter.getChildren(mListofInterests);
                        Log.v("AddInterestInteractor",mListofInterests.toString());

                    } else {
                        Log.v("AddInterestInteractor", "Current data: null");
                    }
                }
            });
        }
    }
}
