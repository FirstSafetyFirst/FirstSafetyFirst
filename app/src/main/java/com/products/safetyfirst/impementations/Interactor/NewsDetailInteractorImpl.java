package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.impementations.presenter.NewsDetailPresenterImpl;
import com.products.safetyfirst.interfaces.interactor.NewsDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.NewsDetailPresenter;
import com.products.safetyfirst.modelhelper.UserHelper;
import com.products.safetyfirst.Pojos.NewsModel;
import com.products.safetyfirst.Pojos.UserModel;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 06/10/17.
 */

public class NewsDetailInteractorImpl implements NewsDetailInteractor {

    private final NewsDetailPresenter newsDetailPresenter;
    private final UserHelper user;

    public NewsDetailInteractorImpl(NewsDetailPresenterImpl newsDetailPresenter) {
        this.newsDetailPresenter = newsDetailPresenter;
        this.user = UserHelper.getInstance();
    }

    @Override
    public void requestNews(String mPostKey) {

        DatabaseReference mPostReference =getDatabase().getReference()
                .child("news").child(mPostKey);

        mPostReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                NewsModel p = mutableData.getValue(NewsModel.class);
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
                Log.d("NewsDetailInteractor", "newsTransaction:onComplete:" + databaseError);
            }
        });

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NewsModel news = dataSnapshot.getValue(NewsModel.class);
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

        if (user.isSignedIn()) {
            DatabaseReference newsRef = getDatabase().getReference()
                    .child("news").child(mNewsKey);
            DatabaseReference userRef = getDatabase().getReference()
                    .child("users").child(user.getUserId());
            addBookmarkToNews(newsRef, user.getUserId());
            addBookMarkToUser(userRef, mNewsKey);
        }


    }


    private void addBookMarkToUser(DatabaseReference userRef, final String mNewsKey) {
        userRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                UserModel p = mutableData.getValue(UserModel.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.newsBookmarks.containsKey(mNewsKey)) {
                    p.newsBookmarks.remove(mNewsKey);
                } else {
                    p.newsBookmarks.put(mNewsKey, true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("NewsDetailInteractor", "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    private void addBookmarkToNews(DatabaseReference newsRef, final String mProfileKey) {
        newsRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                NewsModel p = mutableData.getValue(NewsModel.class);
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
