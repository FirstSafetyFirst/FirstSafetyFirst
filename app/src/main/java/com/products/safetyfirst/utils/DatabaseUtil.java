package com.products.safetyfirst.utils;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/**
 * Created by vikas on 06/10/17.
 */

public class DatabaseUtil {

    private static FirebaseDatabase mDatabase;

    private static FirebaseFirestore mFirestoreDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }

    public static FirebaseFirestore getFireStore(){
        if (mFirestoreDatabase == null) {
            mFirestoreDatabase = FirebaseFirestore.getInstance();

            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();

            mFirestoreDatabase.setFirestoreSettings(settings);

        }

        return mFirestoreDatabase;
    }
}