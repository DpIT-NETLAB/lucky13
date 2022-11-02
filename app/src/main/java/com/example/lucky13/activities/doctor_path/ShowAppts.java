package com.example.lucky13.activities.doctor_path;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.lucky13.R;
import com.example.lucky13.notification.FcmNotificationsSender;
import com.example.lucky13.service.DoctorService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class ShowAppts extends AppCompatActivity {

    TextView mApptTime;

    CardView mApptCard;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_appts);

        mApptCard  = findViewById(R.id.appt_card);
        mApptCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAppts.this, PatientPresentationActivity.class);
                startActivity(intent);
            }
        });

    }
}