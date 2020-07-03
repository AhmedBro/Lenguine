package com.example.linguine.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.linguine.Common.SessionMangment;
import com.example.linguine.Models.UserModel;
import com.example.linguine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText mMail, mPassword;
    Button mLogIn;
    TextView mForgetPassword;
    ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    SessionMangment mSessionMangment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InstView();
    }

    private void InstView() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("User Data");
        mSessionMangment = new SessionMangment(this);
        mAuth = FirebaseAuth.getInstance();

        mForgetPassword = findViewById(R.id.mForgetPassword);
        mForgetPassword.setOnClickListener(this);

        mLogIn = findViewById(R.id.mLogin);
        mLogIn.setOnClickListener(this);


        mMail = findViewById(R.id.mMail);

        mPassword = findViewById(R.id.mPassword);
        mProgressBar = findViewById(R.id.simpleProgressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mForgetPassword:
                startActivity(new Intent(this, ForgetPassword.class));
                break;

            case R.id.mLogin:

                if (validate()) {
                    if (mMail.getText().toString().equalsIgnoreCase("admin") && mPassword.getText().toString().equalsIgnoreCase("admin")) {
                        startActivity(new Intent(this, Admin.class));
                        finish();
                    }
                    else {
                        mProgressBar.setVisibility(View.VISIBLE);
                        Rigester();
                    }

                }
                break;
        }

    }

    private void Rigester() {
        mAuth.signInWithEmailAndPassword(mMail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Login.this, Home.class);
                            final String USER_ID = FirebaseAuth.getInstance().getUid();
                            mDatabaseReference.child(USER_ID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    mSessionMangment.createLoginSession(true, snapshot.child("mId").getValue().toString(), snapshot.child("mName").getValue().toString(), snapshot.child("mMail").getValue().toString(), snapshot.child("mPhone").getValue().toString(), "", "");

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            mProgressBar.setVisibility(View.GONE);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(findViewById(android.R.id.content), "Log in failed" + task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                            mProgressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });
    }

    private boolean validate() {


        if (mMail.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your E-Mail", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (mPassword.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your Password", Snackbar.LENGTH_LONG).show();
            return false;
        } else {

            return true;

        }
    }


}
