package com.products.safetyfirst.modelhelper;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.models.UserModel;
import com.products.safetyfirst.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rishabh on 21/10/17.
 */

public class AuthorHelper {
    private static final AuthorHelper ourInstance = new AuthorHelper();

    public static AuthorHelper getInstance() {
        return ourInstance;
    }

    private AuthorHelper() {
    }

    public void getPeerImage(final String uid, final OnReceiveCallback onReceiveCallback) {
        final CompositeDisposable disposable = new CompositeDisposable();
        Disposable subs = getPeerImage(uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        onReceiveCallback.onReceive(s);
                        disposable.dispose();
                    }
                });
        disposable.add(subs);
    }

    public Single<String> getPeerImage(final String uid) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<String> emitter) throws Exception {
                DatabaseUtil.getDatabase().getReference().child(UserModel.USER_LINK).child(uid).child(UserModel.USER_PHOTO_LINK)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String link = dataSnapshot.getValue(String.class);
                            emitter.onSuccess(link);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }
        });
    }

    public Single<String> getPeerName(final String uid) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<String> emitter) throws Exception {
                DatabaseUtil.getDatabase().getReference().child(UserModel.USER_LINK).child(uid).child(UserModel.USER_NAME_LINK)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String user = dataSnapshot.getValue(String.class);
                                emitter.onSuccess(user);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    public Single<String> getPeerEmail(final String uid) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<String> emitter) throws Exception {
                DatabaseUtil.getDatabase().getReference().child(UserModel.USER_LINK).child(uid).child(UserModel.USER_EMAIL_LINK)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String email = dataSnapshot.getValue(String.class);
                                emitter.onSuccess(email);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    public Single<List<String>> getPeerPosts(final String uid) {
        return Single.create(new SingleOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<List<String>> emitter) throws Exception {
                final List<String> postKeys = new ArrayList<>();
                DatabaseUtil.getDatabase().getReference().child(UserModel.USER_POSTS_LINK).child(uid)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
                                for(DataSnapshot data : datas) {
                                    postKeys.add(data.getKey());
                                }
                                emitter.onSuccess(postKeys);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }



    public interface OnReceiveCallback {
        void onReceive(String required);
    }
}
