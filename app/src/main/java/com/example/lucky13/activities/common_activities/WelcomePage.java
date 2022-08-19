package com.example.lucky13.activities.common_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.activities.patient_path.DiseasesShowActivity;
import com.example.lucky13.activities.patient_path.GeneralSymptomSelect;
import com.example.lucky13.activities.patient_path.PatientChoicesActivity;

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
                startActivity(new Intent(WelcomePage.this, PatientChoicesActivity.class));
            }
        });
    }
}