package com.products.safetyfirst.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vikas on 30/09/17.
 */

public class FirebaseUtils {
    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }
    public static DatabaseReference getPeopleRef() {
        return getBaseRef().child("people");
    }
    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }
}