package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;

import java.util.Objects;

public class PatientInfoActivity extends AppCompatActivity {

    EditText mFirstName,
            mLastName;
    ImageView mGenderImage;
    AppCompatButton mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        Intent incomingIntent = getIntent();
        String incomingGenderString = incomingIntent.getStringExtra("gender");

        mFirstName = findViewById(R.id.patientinfoFirstNameTextEdit);
        mLastName = findViewById(R.id.patientinfoLastNameTextEdit);
        mGenderImage = findViewById(R.id.patientInfoGenderImage);

        mNextButton = findViewById(R.id.patientInfoNextButton);

        if (Objects.equals(incomingGenderString, "male"))
            mGenderImage.setImageResource(R.drawable.manimage);
        else mGenderImage.setImageResource(R.drawable.womanimage);

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
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);

                startActivity(intent);
            }
        });
    }
}