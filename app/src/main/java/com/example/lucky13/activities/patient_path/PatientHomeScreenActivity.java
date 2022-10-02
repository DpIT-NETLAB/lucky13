package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.activities.patient_path.side_bar.DrawerBaseActivity;

public class PatientHomeScreenActivity extends DrawerBaseActivity {

    com.example.lucky13.databinding.ActivityDashboardBinding activityDashboardBinding;

    AppCompatButton mTakeQuizButton,
                    mFindDoctorsNearbyButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityDashboardBinding = com.example.lucky13.databinding.ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());


        mTakeQuizButton = findViewById(R.id.takeQuizButton);
        mFindDoctorsNearbyButton = findViewById(R.id.findDoctorsNearbyButton);

        mTakeQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientHomeScreenActivity.this, GeneralSymptomSelect.class));
            }
        });

        mFindDoctorsNearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientHomeScreenActivity.this, ShowClinics.class);
                startActivity(intent);
            }
        });
    }
}