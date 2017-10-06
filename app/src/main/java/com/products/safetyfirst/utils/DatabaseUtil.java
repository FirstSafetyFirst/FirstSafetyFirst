package com.products.safetyfirst.utils;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vikas on 06/10/17.
 */

public class DatabaseUtil {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }
}