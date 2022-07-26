package com.example.lucky13.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.utils.EmailVerificationSender;
import com.example.lucky13.utils.converters.DoctorConverter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CreateDoctorAccountActivity extends AppCompatActivity {

    private static final String TAG = "Addition of doctor";

    EditText mPassCode,
            mEmailAddress,
            mPasswordText,
            mPasswordConfirmText;
    AppCompatButton mSignUpButton;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);

        Intent incomingIntent = getIntent();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mPassCode = findViewById(R.id.doctorCreatePasscodeTextEdit);
        mEmailAddress = findViewById(R.id.doctorCreateEmailTextEdit);
        mPasswordText = findViewById(R.id.doctorCreatePasswordTextEdit);
        mPasswordConfirmText = findViewById(R.id.doctorCreatePasswordConfirmTextEdit);

        mSignUpButton = findViewById(R.id.doctorCreateSignUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gender = incomingIntent.getStringExtra("gender");

                String passcode = mPassCode.getText().toString().trim();
                String email = mEmailAddress.getText().toString().trim();
                String password = mPasswordText.getText().toString().trim();
                String conf_password = mPasswordConfirmText.getText().toString().trim();

                if (TextUtils.isEmpty(passcode)) {
                    mPassCode.setError("Passcode is required!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmailAddress.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPasswordText.setError("Password is required!");
                    return;
                }
                if (TextUtils.isEmpty(conf_password)) {
                    mPasswordConfirmText.setError("Password confirmation is required!");
                    return;
                }
                if (!password.equals(conf_password)) {
                    mPasswordConfirmText.setError("Password confirmation does not match!");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            EmailVerificationSender.sendVerificationMail(firebaseAuth);

                            Toast.makeText(CreateDoctorAccountActivity.this, TAG + ": succeeded", Toast.LENGTH_SHORT).show();

                            String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                            Doctor doctor = new Doctor(
                                    uid,
                                    "name",
                                    email,
                                    passcode,
                                    "clinicId",
                                    "medicalField",
                                    new ArrayList<String>(),
                                    "0785",
                                    0.00,
                                    gender
                            );

                            Map<String, Object> doctorMap = DoctorConverter.convertFromEntityToMap(doctor);

                            firebaseFirestore.collection("Doctors").document(uid).set(doctorMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Log.d(TAG, "DOCTOR ADDED TO FIRESTORE DATABASE");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Log.d(TAG, "DOCTOR CANNOT BE ADDED TO THE FIRESTORE DATABASE");
                                        }
                                    });
                        } else {

                            Toast.makeText(CreateDoctorAccountActivity.this, TAG + ": failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}