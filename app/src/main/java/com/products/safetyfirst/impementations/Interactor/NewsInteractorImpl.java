package com.products.safetyfirst.impementations.Interactor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.products.safetyfirst.interfaces.interactor.NewsInteractor;
import com.products.safetyfirst.interfaces.presenter.NewsPresenter;
import com.products.safetyfirst.Pojos.NewsModel;

import java.util.ArrayList;
import java.util.Collections;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;
import static com.products.safetyfirst.utils.DatabaseUtil.getFireStore;

/**
 * Created by vikas on 14/11/17.
 */

public class NewsInteractorImpl implements NewsInteractor {

    private final NewsPresenter presenter;
    private final String TAG = "NewsInteractor";
    public NewsInteractorImpl(NewsPresenter pre){

        this.presenter = pre;

    }

    @Override
    public void requestNews() {

        getFireStore().collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<NewsModel> mListOfNews = new ArrayList<>();
                            ArrayList<String> newsArrayKey = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Query query;
        query = getDatabase().getReference()
                .child("news");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<NewsModel> mListOfEvents = new ArrayList<>();
                ArrayList<String> eventsArrayKey = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfEvents.add(x.getValue(NewsModel.class));
                    eventsArrayKey.add(x.getKey());
                }
                Collections.reverse(mListOfEvents);
                Collections.reverse(eventsArrayKey);
                presenter.getChildren(mListOfEvents);
                presenter.getKeys(eventsArrayKey);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Events Interacter", "Could not fetch events");
            }
        });
    }

    @Override
    public void addAction(String mEventId, String mProfileKey, int mAction) {

    }
}
