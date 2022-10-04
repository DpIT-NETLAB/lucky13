package com.example.lucky13.activities.common_activities;

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
import com.example.lucky13.service.DoctorService;
import com.example.lucky13.activities.patient_path.PatientHomeScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    final  String TAG = "Sign in user";

    EditText mEmailEditText,
             mPasswordEditText;
    AppCompatButton mSignInButton,
                    mSignInGoogleButton;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    String token;

    DoctorService doctorService = new DoctorService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailEditText = findViewById(R.id.patientCreateEmailTextEdit);
        mPasswordEditText = findViewById(R.id.patientCreatePasswordTextEdit);

        mSignInButton = findViewById(R.id.patientCreateSignInButton);
        mSignInGoogleButton = findViewById(R.id.patientCreateSignInWithGoogleButton);
        firebaseAuth = FirebaseAuth.getInstance();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    mEmailEditText.setError("Email is required");
                    return;

                }
                else if (TextUtils.isEmpty(password)) {

                    mPasswordEditText.setError("Password is required");
                    return;

                }
                if (password.length() < 6) {

                    mPasswordEditText.setError("Password must be of at least 6 characters");
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            if (!Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {

                                Toast.makeText(SignInActivity.this, "Please verify email!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(SignInActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "User successfully logged in!");

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

                            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                            doctorService.getAllDoctors();
                            doctorService.doctorList.observe(SignInActivity.this, doctorsList -> {
                                for (Doctor doctor : doctorsList) {
                                    if (Objects.equals(doctor.getEmail(), email)) {
                                        firebaseFirestore.collection("Doctors").document(doctor.getUID())
                                            .update("token", token)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("SLOTS: ", "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("SLOTS: ", "Error writing document", e);
                                                }
                                            });
                                    }
                                }
                            });
                            
                            Intent intent = new Intent(SignInActivity.this, PatientHomeScreenActivity.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "User cannot log in!");
                        }
                    }
                });
            }
        });
    }
}