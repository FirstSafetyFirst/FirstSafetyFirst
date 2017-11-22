package com.products.safetyfirst.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.products.safetyfirst.Pojos.UserModel;
import com.products.safetyfirst.activity.HomeActivity;
import com.products.safetyfirst.utils.Analytics;

import java.util.HashMap;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 22/11/17.
 */

public class FirebaseRepository implements LoginRepository {
    private static FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private UserModel user;

    @Override
    public UserModel getUser() {
        if(user != null){
            return user;
        }

        return null;
    }

    @Override
    public void loginUser(String email, String password) {

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAuth.addAuthStateListener(mAuthListener);

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Log.e("LOGIN", "Login failed");
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        this.user = new UserModel(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
        //Analytics.logEventLogin(this,1);
        String username;
        if(user.getDisplayName()==null){
            username = usernameFromEmail(user.getEmail());
        }
        else username = user.getDisplayName();

        writeNewUser(user.getUid(), username, user.getEmail(), user.getPhotoUrl()!= null ?user.getPhotoUrl().toString():null);

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email, String image) {
        UserModel user = new UserModel(name, email, image);

        HashMap<String, Boolean> mListOfInterests = new HashMap<>();

        mListOfInterests.put("PPE", false);
        mListOfInterests.put("Fire Safety", false);
        mListOfInterests.put("Ladder Safety", false);
        mListOfInterests.put("Health Safety", false);
        mListOfInterests.put("Chemical", false);
        mListOfInterests.put("Others", false);

        getDatabase().getReference().child("users").child(userId).setValue(user);
        getDatabase().getReference().child("user-interests").child(userId).setValue(mListOfInterests);
    }

}
