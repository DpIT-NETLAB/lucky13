package com.example.lucky13.activities.doctor_path;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class CreateDoctorAccountActivity extends AppCompatActivity {

    private static final String TAG = "Addition of doctor";

    EditText mPassCode,
            mEmailAddress,
            mPasswordText,
            mPasswordConfirmText;

    String token;

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
                            Toast.makeText(CreateDoctorAccountActivity.this, "Token: " + token, Toast.LENGTH_SHORT).show();
                        }
                    });


                Intent intent = new Intent(CreateDoctorAccountActivity.this, GeneralScheduleActivity.class);

                intent.putExtra("passCode", passcode);
                intent.putExtra("email", email);
                intent.putExtra("gender", gender);
                intent.putExtra("password", password);
                intent.putExtra("token", token);

                startActivity(intent);
            }
        });
    }
}