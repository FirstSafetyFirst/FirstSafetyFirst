package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.KnowItInteractor;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.Pojos.KnowItItem;
import com.products.safetyfirst.Pojos.KnowItItemType;

import java.util.ArrayList;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by ishita sharma on 10/18/2017.
 */

public class KnowItInteractorImpl implements KnowItInteractor {

    private final KnowItPresenter presenter;

    public KnowItInteractorImpl(KnowItPresenter presenter){
        this.presenter= presenter;
    }

    @Override
    public void getKnowit() {
        final Query query = getDatabase().getReference().child("knowIt").orderByChild("timestamp");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<KnowItItem> mListOfItems= new ArrayList<>();
                for(DataSnapshot x : dataSnapshot.getChildren()){

                    mListOfItems.add(x.getValue(KnowItItem.class));
                }
                presenter.getChildren(mListOfItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("KnowItInteractorImpl","Fetching of KnowItItems failed");

            }
        });
    }

    @Override
    public void getKnowitItem(String itemName) {
        DatabaseReference mPostReference = getDatabase().getReference()
                .child("knowitTypes").child(itemName);

        mPostReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                KnowItItemType p = mutableData.getValue( KnowItItemType.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                p.setNumViews(p.getNumViews() + 1);

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("KnowItInteractorImpl", "knowitTransaction:onComplete:" + databaseError);
            }
        });

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KnowItItemType knowItItemType= dataSnapshot.getValue(KnowItItemType.class);
                presenter.setSpecificItem(knowItItemType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("KnowItInteractorImpl", "loadItem:onCancelled", databaseError.toException());

            }
        });

    }



}