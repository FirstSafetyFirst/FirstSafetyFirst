package com.products.safetyfirst.impementations;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.NewsDetailInteractor;
import com.products.safetyfirst.interfaces.NewsDetailPresenter;
import com.products.safetyfirst.models.News_model;

/**
 * Created by vikas on 06/10/17.
 */

public class NewsDetailInteractorImpl implements NewsDetailInteractor {

    private NewsDetailPresenter newsDetailPresenter;

    public NewsDetailInteractorImpl(NewsDetailPresenterImpl newsDetailPresenter) {
        this.newsDetailPresenter = newsDetailPresenter;
    }

    @Override
    public void requestNews(String mPostKey) {

        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("news").child(mPostKey);

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                News_model news = dataSnapshot.getValue(News_model.class);
                newsDetailPresenter.getNews(news);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("NewsDetailInteractor", "loadPost:onCancelled", databaseError.toException());

            }
        });


    }

    @Override
    public void setBookMark(String mNewsKey) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mProfileKey = null;

        if (user != null) {
            mProfileKey = user.getUid();

            DatabaseReference newsRef = FirebaseDatabase.getInstance().getReference()
                    .child("news").child(mNewsKey);
            //TODO add a db reference for user/bookmarkedNews and run transaction to add news key if it is absent otherwise remove the key

            onBookMarkClicked(newsRef, mProfileKey);
        }


    }

    private void onBookMarkClicked(DatabaseReference newsRef, final String mProfileKey) {
        newsRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                News_model p = mutableData.getValue(News_model.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.bookmarks.containsKey(mProfileKey)) {
                    p.bookmarks.remove(mProfileKey);
                } else {
                    p.bookmarks.put(mProfileKey, true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("News Bookmark", "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}