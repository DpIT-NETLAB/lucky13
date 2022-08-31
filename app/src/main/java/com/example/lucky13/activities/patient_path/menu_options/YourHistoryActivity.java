package com.example.lucky13.activities.patient_path.menu_options;

import android.os.Bundle;

import com.example.lucky13.activities.patient_path.side_bar.DrawerBaseActivity;
import com.example.lucky13.databinding.ActivityDashboardBinding;

public class YourHistoryActivity extends DrawerBaseActivity {

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        allocateActivityTitle("Your History");
    }
}