package com.example.lucky13.activities.doctor_path;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;

public class DoctorHomeScreenActivity extends AppCompatActivity {

    AppCompatButton mSetUpSchedule,
                    mSeeAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_page);

        mSeeAppointments = findViewById(R.id.seeAppointments);
        mSetUpSchedule = findViewById(R.id.setUpGeneralSchedule);

        mSeeAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DoctorHomeScreenActivity.this, ShowAppts.class);

                startActivity(intent);
            }
        });

        mSetUpSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DoctorHomeScreenActivity.this, GeneralScheduleActivity.class);

                startActivity(intent);
            }
        });
    }
}