package com.example.lucky13.activities.common_activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;

public class HomeScreenActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView homeText;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        homeText = findViewById(R.id.homeText);
    }
}
