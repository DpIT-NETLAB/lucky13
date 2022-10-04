package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.SignInActivity;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.notification.FcmNotificationsSender;
import com.example.lucky13.service.DoctorService;

import java.util.Objects;

public class AfterAppointmentActivity extends AppCompatActivity {

    DoctorService doctorService = new DoctorService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent incomingIntent = getIntent();

        String uid = incomingIntent.getStringExtra("doctor");

        doctorService.getAllDoctors();
        doctorService.doctorList.observe(AfterAppointmentActivity.this, doctorsList -> {
            for (Doctor doctor : doctorsList) {
                if (Objects.equals(doctor.getUID(), uid)) {
                    Log.d("TAG: ", "TOKEN: " + doctor.getToken());
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(doctor.getToken(), "abcd", "aaaaaaaa", getApplicationContext(), AfterAppointmentActivity.this);
                    notificationsSender.SendNotifications();
                }
            }
        });
    }
}
