package com.example.lucky13.activities.common_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.activities.doctor_path.ApptEventFormActivity;
import com.example.lucky13.activities.doctor_path.CalendarActivity;
import com.example.lucky13.activities.doctor_path.GeneralScheduleActivity;
import com.example.lucky13.activities.patient_path.FindDoctorsNearby;
import com.example.lucky13.activities.patient_path.GeneralSymptomSelect;
import com.example.lucky13.activities.patient_path.PatientChoicesActivity;
import com.example.lucky13.activities.patient_path.ShowClinics;
import com.example.lucky13.activities.common_activities.side_bar.DashboardActivity;

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

                Intent intent = new Intent(WelcomePage.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WelcomePage.this, GeneralScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
}