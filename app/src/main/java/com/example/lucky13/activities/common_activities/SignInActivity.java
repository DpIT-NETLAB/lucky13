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
import com.example.lucky13.activities.patient_path.DiseasesShowActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    final  String TAG = "Sign in user";

    EditText mEmailEditText,
             mPasswordEditText;
    AppCompatButton mSignInButton,
                    mSignInGoogleButton;

    FirebaseAuth firebaseAuth;

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

                            Intent intent = new Intent(SignInActivity.this, DiseasesShowActivity.class);
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