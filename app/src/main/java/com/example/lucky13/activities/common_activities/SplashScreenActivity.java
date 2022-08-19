package com.example.lucky13.activities.common_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.lucky13.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // TODO: TOP DOC LOGO ANIMATION - ar fi frumos sa avem asa ceva:
        // https://developer.android.com/guide/topics/ui/splash-screen ca si asta de la gmail

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreenActivity.this, WelcomePage.class));
                finish();
            }
        }, 1750);
    }
}