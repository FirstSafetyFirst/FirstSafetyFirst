package com.products.safetyfirst.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.HomeActivity;
import com.products.safetyfirst.activity.PasswordResetActivity;
import com.products.safetyfirst.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View{

    @Inject
    LoginActivityMVP.Presenter presenter;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button customSigninButton;
    private SignInButton mGoogleSignInButton;
    private Button mSkipButton;
    private TextView mSignup;
    private TextView mForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.login);

        ((App) getApplication()).getComponent().inject(this);

        mGoogleSignInButton = findViewById(R.id.sign_in_button);
        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        customSigninButton = findViewById(R.id.button_sign_in);
        mSkipButton = findViewById(R.id.button_skip);
        mSignup = findViewById(R.id.create_account);
        mForgotPassword = findViewById(R.id.forgot_password);

        customSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginButtonClicked();
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.skipButtonClicked();
            }
        });

        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.googleLogin();
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signupButtonClicked();
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.forgotPasswordClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

    }

    @Override
    public String getEmail() {
        return mEmailField.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordField.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "User not available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHomeScreen(){
        Toast.makeText(this, "Launch Home", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void skip() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void showSignup() {
        //startActivity(new Intent(this,SignUpActivity.class));
        finish();
    }

    @Override
    public void showForgotPass() {
        startActivity(new Intent(this,PasswordResetActivity.class));
    }
}
