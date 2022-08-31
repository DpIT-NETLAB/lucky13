package com.example.lucky13.activities.patient_path.side_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.lucky13.R;
import com.example.lucky13.activities.patient_path.GeneralSymptomSelect;
import com.example.lucky13.activities.patient_path.menu_options.SettingsActivity;
import com.example.lucky13.activities.patient_path.menu_options.UpdateBMIActivity;
import com.example.lucky13.activities.patient_path.menu_options.YourHistoryActivity;
import com.example.lucky13.activities.patient_path.menu_options.YourProfileActivity;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {

        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.sidebarToolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.sidebarNavView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open_drawer, R.string.menu_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {

            //TODO: add the rest of options

            case R.id.nav_your_profile:
                startActivity(new Intent(DrawerBaseActivity.this, YourProfileActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_new_diagnosis:
                startActivity(new Intent(DrawerBaseActivity.this, GeneralSymptomSelect.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_your_history:
                startActivity(new Intent(DrawerBaseActivity.this, YourHistoryActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_bmi_calculator:
                startActivity(new Intent(DrawerBaseActivity.this, UpdateBMIActivity.class));
                overridePendingTransition(0, 0);
                break;
//            case R.id.nav_map:
//                startActivity(new Intent(DrawerBaseActivity.this, MapActivity.class)); *** nu stiu ce ar trebui sa fie asta :')
//                overridePendingTransition(0, 0);
//                break;
            case R.id.nav_settings:
                startActivity(new Intent(DrawerBaseActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                break;
        }

        return false;
    }

    public void allocateActivityTitle(String titleString) {

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(titleString);
    }
}