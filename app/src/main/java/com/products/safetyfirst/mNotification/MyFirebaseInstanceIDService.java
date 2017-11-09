package com.products.safetyfirst.mNotification;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Vikas on 15-07-2016.
 */
@SuppressWarnings({"ALL", "EmptyMethod"})
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.child("instanceId").setValue(refreshedToken);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("instanceID", refreshedToken);
        editor.apply();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}