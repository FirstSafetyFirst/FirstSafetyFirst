package com.products.safetyfirst.modelhelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.models.UserModel;

/**
 * Created by rishabh on 14/10/17.
 */

public class UserHelper {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public boolean isSignedIn() {
        return (auth.getCurrentUser() != null);
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public String getUserId() {
        return auth.getCurrentUser().getUid();
    }

    public String getUserImgUrl() {
        if(auth.getCurrentUser().getPhotoUrl() != null) {
            return auth.getCurrentUser().getPhotoUrl().toString();
        }
        return "";  // return empty non-null string to prevent error
    }
}
