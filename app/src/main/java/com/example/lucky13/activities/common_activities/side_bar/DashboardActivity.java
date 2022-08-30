package com.example.lucky13.activities.common_activities.side_bar;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lucky13.R;
import com.example.lucky13.databinding.ActivityDashboardBinding;

import java.util.Objects;

public class DashboardActivity extends DrawerBaseActivity {

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
    }
}