package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.NewsInteractor;
import com.products.safetyfirst.interfaces.presenter.EventsPresenter;
import com.products.safetyfirst.interfaces.presenter.NewsPresenter;
import com.products.safetyfirst.models.EventModel;
import com.products.safetyfirst.models.NewsModel;
import com.products.safetyfirst.viewholder.NewsViewHolder;

import java.util.ArrayList;
import java.util.Collections;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 14/11/17.
 */

public class NewsInteractorImpl implements NewsInteractor {

    private final NewsPresenter presenter;

    public NewsInteractorImpl(NewsPresenter pre){

        this.presenter = pre;

    }

    @Override
    public void requestNews() {
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
