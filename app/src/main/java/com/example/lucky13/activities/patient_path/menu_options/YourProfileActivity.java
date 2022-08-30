package com.example.lucky13.activities.patient_path.menu_options;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.side_bar.DrawerBaseActivity;
import com.example.lucky13.databinding.ActivityDashboardBinding;

public class YourProfileActivity extends DrawerBaseActivity {

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        allocateActivityTitle("Your Profile");
    }
}