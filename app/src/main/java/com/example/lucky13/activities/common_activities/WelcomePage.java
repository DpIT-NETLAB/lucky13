package com.example.lucky13.activities.common_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.activities.doctor_path.GeneralScheduleActivity;
import com.example.lucky13.activities.patient_path.FindDoctorsNearby;
import com.example.lucky13.activities.patient_path.GeneralSymptomSelect;
import com.example.lucky13.activities.patient_path.PatientChoicesActivity;
import com.example.lucky13.activities.patient_path.ShowClinics;
import com.example.lucky13.notification.FcmNotificationsSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Calendar;
import com.example.lucky13.activities.patient_path.PatientHomeScreenActivity;

public class WelcomePage extends AppCompatActivity {

    AppCompatButton mGetStartedButton,
                    mLogInButton;

    String token;

    Task<String> firebaseMessaging = FirebaseMessaging.getInstance().getToken()
        .addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                token = task.getResult();

                // Log and toast
                Log.d("TAG", token);
                Toast.makeText(WelcomePage.this, "Token: " + token, Toast.LENGTH_SHORT).show();
            }
    });






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mGetStartedButton = findViewById(R.id.welcomeGetStartedButton);
        mLogInButton = findViewById(R.id.welcomeLogInButton);

        mGetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WelcomePage.this, RoleSelectActivity.class);
                startActivity(intent);
            }
        });

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token, "abcd", "aaaaaaaa", getApplicationContext(), WelcomePage.this);
                notificationsSender.SendNotifications();
                
                Intent intent = new Intent(WelcomePage.this, SignInActivity.class);

                startActivity(intent);
            }
        });
    }
}