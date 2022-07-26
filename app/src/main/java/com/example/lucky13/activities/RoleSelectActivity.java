package com.example.lucky13.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;

public class RoleSelectActivity extends AppCompatActivity {

    ImageButton mPatientRoleButton,
                mDoctorRoleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choosing);

        mPatientRoleButton = findViewById(R.id.choosingRolePatientButton);
        mDoctorRoleButton = findViewById(R.id.choosingRoleDoctorButton);

        mPatientRoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RoleSelectActivity.this, GenderSelectActivity.class));
            }
        });

        mDoctorRoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RoleSelectActivity.this, CreateDoctorAccountActivity.class));
            }
        });
    }
}