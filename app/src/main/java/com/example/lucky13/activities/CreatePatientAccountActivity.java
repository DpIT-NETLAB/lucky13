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
import com.example.lucky13.models.Patient;
import com.example.lucky13.utils.EmailVerificationSender;
import com.example.lucky13.utils.converters.PatientConverter;
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

import kotlin.Triple;

public class CreatePatientAccountActivity extends AppCompatActivity {

    private final String TAG = "Addition of user";

    EditText mUsernameEditText,
            mEmailAddressEditText,
            mPasswordEditText,
            mConfPasswordEditText;

    AppCompatButton mSignUpButton,
                    mSignUpGoogleButton;

    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account_create);

        Intent incomingIntent = getIntent();
        String incomingFirstName = incomingIntent.getStringExtra("firstName");
        String incomingLastName = incomingIntent.getStringExtra("lastName");
        String incomingGender = incomingIntent.getStringExtra("gender");

        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mUsernameEditText = findViewById(R.id.patientcreateUsernameTextEdit);
        mEmailAddressEditText = findViewById(R.id.patientCreateEmailTextEdit);
        mPasswordEditText = findViewById(R.id.patientCreatePasswordTextEdit);
        mConfPasswordEditText = findViewById(R.id.patientcreatePasswordConfirmTextEdit);

        mSignUpButton = findViewById(R.id.patientCreateSignInButton);
        mSignUpGoogleButton = findViewById(R.id.patientCreateSignInWithGoogleButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsernameEditText.getText().toString().trim();
                String email = mEmailAddressEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String conf_password = mConfPasswordEditText.getText().toString().trim();

                if (!password.equals(conf_password)) {

                    mConfPasswordEditText.setError("Password confirmation does not match!");
                    return;
                }

                if (TextUtils.isEmpty(email)) {

                    mEmailAddressEditText.setError("Email is required");
                    return;
                }
                else if (TextUtils.isEmpty(password)) {

                    mPasswordEditText.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {

                    mPasswordEditText.setError("Password must be of at least 6 characters");
                    return;
                }
                if (username.length() < 6) {

                    mUsernameEditText.setError("Username must be of at least 6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            EmailVerificationSender.sendVerificationMail(fAuth);

                            Toast.makeText(CreatePatientAccountActivity.this, TAG + ": succeeded", Toast.LENGTH_SHORT).show();

                            String uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            Patient patient = new Patient(
                                    uid,
                                    incomingFirstName,
                                    incomingLastName,
                                    new Triple(0, 0, 0),
                                    email,
                                    0,
                                    0,
                                    incomingGender,
                                    new ArrayList<String>()
                            );

                            Map<String, Object> patientMap = PatientConverter.convertFromEntityToMap(patient);

                            firebaseFirestore.collection("Patients").document(uid).set(patientMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Log.d(TAG, "PATIENT ADDED TO THE FIRESTORE DATABASE");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Log.d(TAG, "PATIENT CANNOT BE ADDED TO THE FIRESTORE DATABASE");
                                        }
                                    });
                        } else {

                            Toast.makeText(CreatePatientAccountActivity.this, TAG + ": failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}