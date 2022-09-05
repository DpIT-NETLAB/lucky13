package com.example.lucky13.activities.patient_path;

import android.os.Bundle;

import com.example.lucky13.activities.patient_path.side_bar.DrawerBaseActivity;
import com.example.lucky13.databinding.ActivityDashboardBinding;

public class PatientHomeScreenActivity extends DrawerBaseActivity {

    com.example.lucky13.databinding.ActivityDashboardBinding activityDashboardBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityDashboardBinding = com.example.lucky13.databinding.ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
    }
}