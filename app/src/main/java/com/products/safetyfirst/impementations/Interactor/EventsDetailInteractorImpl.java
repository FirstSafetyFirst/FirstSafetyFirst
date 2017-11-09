package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.EventsDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.EventsDetailPresenter;
import com.products.safetyfirst.models.EventModel;

import java.util.HashMap;
import java.util.Map;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

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
        DatabaseReference mPostReference = getDatabase().getReference()
                .child("events").child(mEventKey);

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EventModel event = dataSnapshot.getValue(EventModel.class);
                presenter.getEvent(event);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("EventDetailInteractor", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    @Override
    public void setBookMark(final String mEventKey) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            final String mProfileKey = user.getUid();
            DatabaseReference mEventRef = getDatabase().getReference().child("events");

            mEventRef.child(mEventKey).child("bookmarks").child(mProfileKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> updatedUserData = new HashMap<>();
                    if (dataSnapshot.exists()) {
                        // Already following, need to unfollow

                        updatedUserData.put("events/" + mEventKey + "/bookmark/" + mProfileKey, null);
                        updatedUserData.put("eventsBookmarks/" + mProfileKey + "/" + mEventKey, null);

                    } else {
                        updatedUserData.put("events/" + mEventKey + "/bookmark/" + mProfileKey, true);
                        updatedUserData.put("eventsBookmarks/" + mProfileKey + "/" + mEventKey, true);
                    }
                    getDatabase().getReference().updateChildren(updatedUserData, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                            if (firebaseError != null) {

                                Log.e("Event bookmark error: ", firebaseError.getMessage());

                                presenter.onActionError(firebaseError.getMessage());
                            } else {
                                presenter.onActionSuccess();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });
        }

        else {
            presenter.onActionError("Please sign in");
        }

    }

    @Override
    public void setAction(final String mEventKey, final int actionId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            final String mProfileKey = user.getUid();
            DatabaseReference mEventRef = getDatabase().getReference().child("events");

            mEventRef.child(mEventKey).child("action").child(mProfileKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> updatedUserData = new HashMap<>();
                    if (dataSnapshot.exists()) {
                        // Already following, need to unfollow

                        long val = (long) dataSnapshot.getValue();

                        if((val==0 && actionId == 0) || (val==1 && actionId == 1)){
                            //the value in the database and action are same, i.e. the user is trying to undo the action
                            updatedUserData.put("events/" + mEventKey + "/action/" + mProfileKey, null);
                            updatedUserData.put("eventsActions/" + mProfileKey + "/" + mEventKey, null);
                        } else{
                            updatedUserData.put("events/" + mEventKey + "/action/" + mProfileKey, actionId);
                            updatedUserData.put("eventsActions/" + mProfileKey + "/" + mEventKey, actionId);
                        }

                    } else {
                        updatedUserData.put("events/" + mEventKey + "/action/" + mProfileKey, actionId);
                        updatedUserData.put("eventsActions/" + mProfileKey + "/" + mEventKey, actionId);
                    }
                    getDatabase().getReference().updateChildren(updatedUserData, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                            if (firebaseError != null) {

                                Log.e("Event update error: ", firebaseError.getMessage());

                                presenter.onActionError(firebaseError.getMessage());
                            } else {
                                presenter.onActionSuccess();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });
        }

        else {
            presenter.onActionError("Please sign in");
        }
    }
}
