package com.example.linguine.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.linguine.Common.SessionMangment;
import com.example.linguine.R;

public class SplashActivity extends AppCompatActivity {
SessionMangment msSessionMangment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
msSessionMangment = new SessionMangment(this);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               if (msSessionMangment.isLoggedIn()){
                   Intent mainIntent = new Intent(SplashActivity.this, Home.class);
                   SplashActivity.this.startActivity(mainIntent);
                   SplashActivity.this.finish();
               }
               else {
                   Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                   SplashActivity.this.startActivity(mainIntent);
                   SplashActivity.this.finish();
               }
            }
        },700);
    }
}
