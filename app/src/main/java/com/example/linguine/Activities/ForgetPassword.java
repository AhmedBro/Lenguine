package com.example.linguine.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.linguine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPassword extends AppCompatActivity {
    Button mResetPassword;
    EditText mMail;
    FirebaseAuth mFirebaseAuth;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        InstViews();
    }

    private void InstViews() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mMail = findViewById(R.id.mMail);


        mProgressBar = findViewById(R.id.simpleProgressBar);

        mResetPassword = findViewById(R.id.mResetPassword);

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    ForgetPAss();
                }

            }
        });
    }

    void ForgetPAss() {
        mFirebaseAuth.sendPasswordResetEmail(mMail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "check your Mail To Reset Password", Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    startActivity(new Intent(ForgetPassword.this, MainActivity.class));
                    finish();

                }
            }
        });
    }

    private boolean Validate() {
        if (mMail.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your User Name Or E-Mail", Snackbar.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }
}
