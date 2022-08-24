package com.example.lucky13.activities.common_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;
import com.example.lucky13.models.Patient;
import com.example.lucky13.service.PatientService;

public class TestCRUD extends AppCompatActivity {

    private PatientService patientService;

    EditText mUIDText;
    TextView mEmailText;

    Button mCheckEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_crud);

        patientService = new PatientService();

        mUIDText = findViewById(R.id.patientUIDText);
        mEmailText = findViewById(R.id.emailDisplayText);
        mCheckEmailButton = findViewById(R.id.checkButton);

        mCheckEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = mUIDText.getText().toString().trim();

                Patient patient = patientService.getPatient(uid);

                mEmailText.setText(patient.getEmail());
            }
        });
    }
}