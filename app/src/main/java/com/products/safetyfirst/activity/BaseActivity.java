package com.products.safetyfirst.activity;

/*
  Created by krishna on 5/6/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.products.safetyfirst.R;


public class BaseActivity extends AppCompatActivity {
    public static GoogleApiClient mGoogleApiClient;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getCurrentUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();

        else return null;
    }

    public void logout() {

        FirebaseAuth.getInstance().signOut();

        Toast.makeText(getBaseContext(), "You have been Logged out", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getBaseContext(),SignInActivity.class));

    }


    public FirebaseUser getCurrentUser() {
        if (user == null) return null;
        return user;
    }

    public Uri getPhotoUrl() {
        if (user != null && user.getPhotoUrl() != null) return user.getPhotoUrl();
        return null;
    }

    public String getEmail() {
        if (user != null && user.getEmail() != null) return user.getEmail();
        return null;
    }

    public String getName() {
        if (user != null && user.getDisplayName() != null)
            return user.getDisplayName();
        else
            return null;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Create an auto-managed GoogleApiClient with access to App Invites.
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
       // Firebase.setAndroidContext(this);


    }


}