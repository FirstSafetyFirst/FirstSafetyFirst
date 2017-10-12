package com.products.safetyfirst.impementations;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.EventsDetailInteractor;
import com.products.safetyfirst.interfaces.EventsDetailPresenter;
import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.models.News_model;

/**
 * Created by vikas on 12/10/17.
 */

public class EventsDetailInteractorImpl implements EventsDetailInteractor {

    private EventsDetailPresenter presenter;

    public EventsDetailInteractorImpl(EventsDetailPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void requestEvent(String mEventKey) {
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("events").child(mEventKey);

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event_model event = dataSnapshot.getValue(Event_model.class);
                presenter.getEvent(event);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("EventDetailInteractor", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    @Override
    public void setBookMark(String mEventKey) {

    }

    @Override
    public void setAction(String mEventKey, int actionId) {

    }
}
