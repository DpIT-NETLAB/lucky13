package com.example.lucky13.activities.start_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;

public class WelcomePage extends AppCompatActivity {

    Button mGetStartedButton,
            mLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        mGetStartedButton = findViewById(R.id.WelcomeGetStartedButton);
        mLogInButton = findViewById(R.id.WelcomeLogInButton);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WelcomePage.this, WelcomeBackPage.class));
            }
        });
    }
}