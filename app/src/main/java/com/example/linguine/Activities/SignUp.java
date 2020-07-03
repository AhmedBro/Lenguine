package com.example.linguine.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.linguine.Common.SessionMangment;
import com.example.linguine.Models.UserModel;
import com.example.linguine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText mMail, mName, mPhone, mPassword;
    Button mSingUp;
    private FirebaseAuth mAuth;
    SessionMangment mSessionMangment;
    ProgressBar mProgressBar;
    static String USER_ID = "";
    DatabaseReference mUserDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        instViews();

    }

    private void instViews() {
        mUserDataRef = FirebaseDatabase.getInstance().getReference("User Data");

        mSessionMangment = new SessionMangment(SignUp.this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        mMail = findViewById(R.id.mMail);
        mName = findViewById(R.id.mName);
        mPhone = findViewById(R.id.mPhone);
        mPassword = findViewById(R.id.mPassword);


        mSingUp = findViewById(R.id.mSignUp);
        mSingUp.setOnClickListener(this);

        mProgressBar = findViewById(R.id.simpleProgressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mSignUp:

                if (validate()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    SigIn();
                }
                break;

        }
    }

    void SigIn() {

        mAuth.createUserWithEmailAndPassword(mMail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(SignUp.this, Home.class);
                            USER_ID = FirebaseAuth.getInstance().getUid();
                            intent.putExtra("USER_ID", USER_ID);
                            mSessionMangment.createLoginSession(true, USER_ID, mName.getText().toString(), mMail.getText().toString(), mPhone.getText().toString() , "" , "");
                            AddNote();
                            mProgressBar.setVisibility(View.GONE);
                            startActivity(intent);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.

                            Snackbar.make(findViewById(android.R.id.content), "Sign Up failed" + " " + task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
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
        } else if (mPhone.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your Phone", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (mName.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your Name", Snackbar.LENGTH_LONG).show();
            return false;
        } else {

            return true;

        }
    }

    void AddNote() {

        UserModel model = new UserModel(mName.getText().toString(), mPhone.getText().toString(), mMail.getText().toString(), "", USER_ID, "", "");
        mUserDataRef.child(USER_ID).setValue(model);

    }
}
