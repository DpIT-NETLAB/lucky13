package com.example.lucky13.activities.doctor_path;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneNumberActivity extends AppCompatActivity {

    EditText mPhoneNumber;

    AppCompatButton mGetCodeButton;

    String password, passcode, email, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_phone_number);

        mPhoneNumber = findViewById(R.id.doctorCreatePhoneNumberTextEdit);
        mGetCodeButton = findViewById(R.id.doctorCreateGetCodeButton);


        password = getIntent().getStringExtra("password");
        passcode = getIntent().getStringExtra("passCode");
        email = getIntent().getStringExtra("email");
        gender = getIntent().getStringExtra("gender");

        mGetCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhoneNumber.getText().toString().trim().isEmpty()) {
                    mGetCodeButton.setEnabled(false);
                    return;
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+40" + mPhoneNumber.getText().toString(),
                        20,
                        TimeUnit.SECONDS,
                        PhoneNumberActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(PhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                mGetCodeButton.setEnabled(true);
                                Intent intent = new Intent(getApplicationContext(), PhoneVerifyActivity.class);
                                intent.putExtra("phone number", "+40" + mPhoneNumber.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                intent.putExtra("password", password);
                                intent.putExtra("passCode", passcode);
                                intent.putExtra("email", email);
                                intent.putExtra("gender", gender);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }
}
