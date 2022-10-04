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

        String token = incomingIntent.getStringExtra("doctor");
        Log.d("TAG: ", "TOKENNN: " + token);
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token, "New appointment!", "Someone has booked an appointment with you! Tap to find out more", getApplicationContext(), AfterAppointmentActivity.this);
        notificationsSender.SendNotifications();


    }
}
