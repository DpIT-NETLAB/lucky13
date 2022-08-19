package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.RoleSelectActivity;
import com.example.lucky13.activities.common_activities.WelcomePage;

public class PatientChoicesActivity extends AppCompatActivity {
    AppCompatButton mTakeQuizButton,
            mFindDoctorsNearbyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_choices);

        mTakeQuizButton = findViewById(R.id.takeQuizButton);
        mFindDoctorsNearbyButton = findViewById(R.id.findDoctorsNearbyButton);

        mTakeQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientChoicesActivity.this, GeneralSymptomSelect.class));
            }
        });

        mFindDoctorsNearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientChoicesActivity.this, FindDoctorsNearby.class);
                intent.putExtra("number", "2");
                startActivity(intent);
            }
        });
    }
}