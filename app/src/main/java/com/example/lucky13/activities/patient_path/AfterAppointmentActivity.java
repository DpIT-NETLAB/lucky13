package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

        setContentView(R.layout.activity_after_appt);

        AppCompatButton mGoHome;
        mGoHome = findViewById(R.id.goHomeButton);

        Intent incomingIntent = getIntent();

        String token = incomingIntent.getStringExtra("doctor");
        Log.d("TAG: ", "TOKENNNNN: " + token);
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("fVfA1dtYTYqrUWCwPvHxLq:APA91bEp7Yfdt79s55htawMSCOas3lvUqcUvZoNdk_1qVaIgJ8LpF5nly2avWOqFv4l2Oq8sg0zifEtwllfjy0gMQDG2CYtNn5V3U-R8IV6vRVnPbLkxsXEL5ki7SGEET7-8XP8O5H5W", "New appointment!", "Someone has booked an appointment with you. Tap to find out more.", getApplicationContext(), AfterAppointmentActivity.this);
        notificationsSender.SendNotifications();

        mGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterAppointmentActivity.this, PatientHomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
