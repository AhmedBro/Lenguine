package com.example.linguine.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.linguine.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mSignup, mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instviews();
    }

    private void instviews() {
        mLogin = findViewById(R.id.mLogin);
        mLogin.setOnClickListener(this);

        mSignup = findViewById(R.id.mSignUp);
        mSignup.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mLogin:
                startActivity(new Intent(this, Login.class));

                break;
            case R.id.mSignUp:
                startActivity(new Intent(this, SignUp.class));

                break;
        }
    }
}
