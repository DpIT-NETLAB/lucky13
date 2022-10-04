package com.example.lucky13.activities.common_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.notification.FcmNotificationsSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token, "TopDoc - Welcome", "Bine ai venit in aplicatie! \uD83D\uDE00", getApplicationContext(), WelcomePage.this);
                notificationsSender.SendNotifications();
                
                Intent intent = new Intent(WelcomePage.this, SignInActivity.class);

                startActivity(intent);
            }
        });
    }
}