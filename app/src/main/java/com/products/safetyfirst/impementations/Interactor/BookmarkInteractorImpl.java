package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.BookmarkInteractor;
import com.products.safetyfirst.interfaces.presenter.BookmarkPresenter;
import com.products.safetyfirst.utils.FirebaseUtils;

import java.util.ArrayList;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;
/**
 * Created by ishita sharma on 11/4/2017.
 */

public class BookmarkInteractorImpl implements BookmarkInteractor {

    private BookmarkPresenter bookmarkPresenter;
    ArrayList<Object> mListOfItems= new ArrayList<>();
    ArrayList<String> mListOfEvents = new ArrayList<>();
    ArrayList<String> mListOfNews = new ArrayList<>();
    ArrayList<String> mListOfPosts = new ArrayList<>();
    public BookmarkInteractorImpl(BookmarkPresenter presenter){
        bookmarkPresenter=presenter;
    }
    @Override
    public void requestItem(String type) {

        Query query;
        //for events
        query= getDatabase().getReference().child("events").child("eventsBookmarks").child(FirebaseUtils.getCurrentUserId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfEvents.add(x.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BookMark Interacter", "Could not fetch items");
            }
        });
        for(int i=0;i<mListOfEvents.size();i++) {
            query = getDatabase().getReference().child("events").equalTo(mListOfEvents.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mListOfItems.add(dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        /**
        query= getDatabase().getReference().child("news").child("newsBookmarks").child(FirebaseUtils.getCurrentUserId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfItems.add(x.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         **/
        bookmarkPresenter.getChildren(mListOfItems);
    }

    @Override
    public void onRemoveBookMark() {
    }
}
