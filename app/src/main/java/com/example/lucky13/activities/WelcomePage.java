package com.example.lucky13.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;

public class WelcomePage extends AppCompatActivity {

    AppCompatButton mGetStartedButton,
                    mLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mGetStartedButton = findViewById(R.id.welcomeGetStartedButton);
        mLogInButton = findViewById(R.id.welcomeLogInButton);

        mGetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WelcomePage.this, RoleSelectActivity.class));
            }
        });

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: schimba inapoi in log in normal
                startActivity(new Intent(WelcomePage.this, GeneralSymptomSelect.class));
            }
        });
    }
}