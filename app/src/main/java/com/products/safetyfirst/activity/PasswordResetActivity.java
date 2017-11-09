package com.products.safetyfirst.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.products.safetyfirst.R;

/**
 * Created by krishna on 7/6/17.
 */

public class PasswordResetActivity extends BaseActivity {
    private final String TAG = "PasswordResetActivity";
    private EditText email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        email = findViewById(R.id.email);
    }

    public void SendEmail(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        String emailAddress = email.getText().toString();
        if(emailAddress.contains("@")&& emailAddress.contains(".") && (emailAddress.indexOf('@')-emailAddress.indexOf('.') >2))
        {
            Toast.makeText(PasswordResetActivity.this,"Enter email address correctyl",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG,"Password reset Email is sent");
                        Toast.makeText(PasswordResetActivity.this,"Password reset Email is sent",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
