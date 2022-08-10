package com.example.lucky13.activities.common_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;
import com.example.lucky13.activities.doctor_path.CreateDoctorAccountActivity;
import com.example.lucky13.activities.patient_path.PatientInfoActivity;

import java.util.Objects;

public class GenderSelectActivity extends AppCompatActivity {

    ImageButton mMaleButton,
                mFemaleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_choosing);

        Intent incomingIntent = getIntent();
        String incomingRole = incomingIntent.getStringExtra("role");

        Intent patientIntent = new Intent(GenderSelectActivity.this, PatientInfoActivity.class);
        Intent doctorIntent = new Intent(GenderSelectActivity.this, CreateDoctorAccountActivity.class);

        mMaleButton = findViewById(R.id.choosinggenderMaleButton);
        mFemaleButton = findViewById(R.id.choosinggenderFemaleButton);

        mMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patientIntent.putExtra("gender", "male");
                doctorIntent.putExtra("gender", "male");

                if (Objects.equals(incomingRole, "patient"))
                    startActivity(patientIntent);
                else
                    startActivity(doctorIntent);
            }
        });
        mFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patientIntent.putExtra("gender", "female");
                doctorIntent.putExtra("gender", "female");

                if (Objects.equals(incomingRole, "patient"))
                    startActivity(patientIntent);
                else
                    startActivity(doctorIntent);
            }
        });
    }
}