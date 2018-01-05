package com.products.safetyfirst.activity;

/*
  Created by krishna on 5/6/17.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.products.safetyfirst.BuildConfig;
import com.products.safetyfirst.R;

import java.util.Arrays;
import java.util.List;


public class BaseActivity extends AppCompatActivity {
    private static GoogleApiClient mGoogleApiClient;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 123;

    void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    String getCurrentUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        else return null;
    }

    void logout() {

        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getBaseContext(), "You have been Logged out", Toast.LENGTH_SHORT).show();

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

    boolean isLoggedIn() {
        return user != null;
    }

    public class MySignInListener implements View.OnClickListener{

        final Context context;

        public MySignInListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {

                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                        // new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                        // new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setTosUrl("https://superapp.example.com/terms-of-service.html")
                                .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                .build(),
                        RC_SIGN_IN);


        }
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