package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;

public class PatientInfoActivity extends AppCompatActivity {

    EditText mFirstName,
            mLastName;
    AppCompatButton mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        Intent incomingIntent = getIntent();
        String incomingGenderString = incomingIntent.getStringExtra("gender");
        double incomingHeightDouble = incomingIntent.getDoubleExtra("height", 0);
        double incomingWeightDouble = incomingIntent.getDoubleExtra("weight", 0);
        String incomingBMIString = incomingIntent.getStringExtra("BMI");

        mFirstName = findViewById(R.id.patientinfoFirstNameTextEdit);
        mLastName = findViewById(R.id.patientinfoLastNameTextEdit);

        mNextButton = findViewById(R.id.patientInfoNextButton);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {

                    mFirstName.setError("First name is required!");
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {

                    mLastName.setError("Last name is required!");
                    return;
                }

                Intent intent = new Intent(PatientInfoActivity.this, CreatePatientAccountActivity.class);

                intent.putExtra("gender", incomingGenderString);
                intent.putExtra("height", incomingHeightDouble);
                intent.putExtra("weight", incomingWeightDouble);
                intent.putExtra("BMI", incomingBMIString);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);

                startActivity(intent);
            }
        });
    }
}