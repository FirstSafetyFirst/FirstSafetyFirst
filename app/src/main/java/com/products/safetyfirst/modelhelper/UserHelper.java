package com.products.safetyfirst.modelhelper;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.products.safetyfirst.Pojos.UserModel;

import static com.products.safetyfirst.utils.DatabaseUtil.getFireStore;

/**
 * Created by rishabh on 14/10/17.
 */

public class UserHelper {

    private static final UserHelper instance = new UserHelper();

    public static UserHelper getInstance() {
        return instance;
    }

    private UserHelper() {
    }

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public boolean writeUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //WRITE TO FIREBASE
        FirebaseFirestore db = getFireStore();

        UserModel userModel = new UserModel(user.getDisplayName(), user.getPhotoUrl()==null?"":user.getPhotoUrl().toString());

        db.collection("users").document(user.getUid())
                .set(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("LOGIN", "DocumentSnapshot added with ID: " + user.getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("LOGIN", "Error adding document", e);
                    }
                });
        return  false;
    }

    public boolean isSignedIn() {
        return (auth.getCurrentUser() != null);
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public String getUserId() {
        return auth.getCurrentUser().getUid();
    }

    public String getUserEmail(){
        if( auth.getCurrentUser().getEmail() != null)
            return  auth.getCurrentUser().getEmail();
        else
            return "";
    }

    public String getImageUrl(){
        if(auth.getCurrentUser().getPhotoUrl().toString() != null)
            return auth.getCurrentUser().getPhotoUrl().toString();
        else
            return "";
    }

    public String getUserName() {
        return auth.getCurrentUser().getDisplayName();
    }

    public String getUserImgUrl() {
        if(auth.getCurrentUser().getPhotoUrl() != null) {
            return auth.getCurrentUser().getPhotoUrl().toString();
        }
        return "";  // return empty non-null string to prevent error
    }
}
