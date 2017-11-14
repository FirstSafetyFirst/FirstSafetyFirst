package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.PostInteractor;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.models.EventModel;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;
import java.util.Collections;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 01/11/17.
 */

public class PostInteractorImpl implements PostInteractor {

    private final PostPresenter presenter;

    public PostInteractorImpl(PostPresenter presenter){
        this.presenter = presenter;
    }



    @Override
    public void requestPosts() {
        Query query;

        query = getDatabase().getReference()
                .child("posts").orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PostModel> mListOfEvents = new ArrayList<>();
                ArrayList<String> eventsArrayKey = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfEvents.add(x.getValue(PostModel.class));
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
