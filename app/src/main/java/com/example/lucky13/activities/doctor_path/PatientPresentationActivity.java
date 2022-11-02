package com.example.lucky13.activities.doctor_path;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.models.Patient;
import com.example.lucky13.service.DoctorService;
import com.example.lucky13.service.PatientService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class PatientPresentationActivity extends AppCompatActivity {

        TextView mName, mAge, mGender, mWeight, mHeight, mBMI;

        private FirebaseAuth mAuth;

        PatientService patientService = new PatientService();

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient_presentation);
            mName = findViewById(R.id.patientPresentationName);
            mGender = findViewById(R.id.patientPresentationField);
            mAge = findViewById(R.id.patientPresentationAge);
            mHeight = findViewById(R.id.patientPresentationHeight);
            mWeight = findViewById(R.id.patientPresentationWeight);
            mBMI = findViewById(R.id.patientPresentationBMI);

            patientService.getAllPatients();
            patientService.patientList.observe(this, patientList -> {
                for (Patient patient : patientList) {
                    if (Objects.equals(patient.getUID(), "s475bjzeVwViNuh9inkRpZBMm092")) {

                        Log.d("TAG", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + patient.getFirstName());
                        mName.setText(patient.getFirstName() + " " + patient.getLastName());
                        mAge.setText("AGE: " + "18");
                        mGender.setText("GENDER: " + patient.getGender());
                        mHeight.setText("HEIGHT: 190 cm");
                        mWeight.setText("WEIGHT: 70 kg");
                        mBMI.setText("BMI: " + "19.4");
                    }
                }
            });

        }
    }
