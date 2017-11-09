package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.EventsInteractor;
import com.products.safetyfirst.interfaces.presenter.EventsPresenter;
import com.products.safetyfirst.models.EventModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 11/10/17.
 */

public class EventsInteractorImpl implements EventsInteractor {

    private final EventsPresenter presenter;

    public EventsInteractorImpl(EventsPresenter pre){

        this.presenter = pre;

    }

    @Override
    public void requestEvents() {

        Query query;

            query = getDatabase().getReference()
                    .child("events").orderByChild("timestamp");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<EventModel> mListOfEvents = new ArrayList<>();
                    ArrayList<String> eventsArrayKey = new ArrayList<>();
                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        mListOfEvents.add(x.getValue(EventModel.class));
                        eventsArrayKey.add(x.getKey());
                    }
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
    public void addAction(final String mEventId, final String mProfileKey, int mAction) {


        DatabaseReference mEventsRef = getDatabase().getReference().child("events");

        mEventsRef.child(mEventId).child("action").child(mProfileKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> updatedUserData = new HashMap<>();
                if (dataSnapshot.exists()) {
                    // Already following, need to unfollow
                    updatedUserData.put("events/" + mEventId + "/action/" + mProfileKey, null);
                } else {
                    updatedUserData.put("events/" + mEventId + "/action/" + mProfileKey, true);
                }
                getDatabase().getReference().updateChildren(updatedUserData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {

                            Log.e("Event error: ", firebaseError.getMessage());

                            presenter.onActionError("Could not add event");
                        }else{
                            presenter.onActionSuccess("");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("Follow error: ", firebaseError.getMessage());
            }
        });

    }
}
