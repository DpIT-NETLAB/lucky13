package com.example.lucky13.activities.doctor_path;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.service.DoctorService;
import com.example.lucky13.utils.EmailVerificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class PhoneVerifyActivity extends AppCompatActivity {

    private static final String TAG = "Verify phone number";
    private final DoctorService doctorService = new DoctorService();

    EditText mInputCode1,
            mInputCode2,
            mInputCode3,
            mInputCode4,
            mInputCode5,
            mInputCode6;

    AppCompatButton mVerifyPhoneNumberButton;

    String verificationId, password, passcode, email, gender, phoneNumber;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mInputCode1 = findViewById(R.id.inputCode1);
        mInputCode2 = findViewById(R.id.inputCode2);
        mInputCode3 = findViewById(R.id.inputCode3);
        mInputCode4 = findViewById(R.id.inputCode4);
        mInputCode5 = findViewById(R.id.inputCode5);
        mInputCode6 = findViewById(R.id.inputCode6);

        setUpInputs();

        mVerifyPhoneNumberButton = findViewById(R.id.doctorCreateVerifyPhoneNumberButton);

        verificationId = getIntent().getStringExtra("verificationId");
        phoneNumber = getIntent().getStringExtra("phone number");
        password = getIntent().getStringExtra("password");
        passcode = getIntent().getStringExtra("passCode");
        email = getIntent().getStringExtra("email");
        gender = getIntent().getStringExtra("gender");

        mVerifyPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInputCode1.getText().toString().trim().isEmpty()
                        || mInputCode2.getText().toString().trim().isEmpty()
                        || mInputCode3.getText().toString().trim().isEmpty()
                        || mInputCode4.getText().toString().trim().isEmpty()
                        || mInputCode5.getText().toString().trim().isEmpty()
                        || mInputCode6.getText().toString().trim().isEmpty()) {
                    mVerifyPhoneNumberButton.setEnabled(false);
                    return;
                }
                String code = mInputCode1.getText().toString() +
                        mInputCode2.getText().toString() +
                        mInputCode3.getText().toString() +
                        mInputCode4.getText().toString() +
                        mInputCode5.getText().toString() +
                        mInputCode6.getText().toString();

                Log.d(TAG, "verification" + gender + email);

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );

                Log.d(TAG, "verification" + phoneAuthCredential);

                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PhoneVerifyActivity.this, "Phone number verified!", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                                    user.delete();
                                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                EmailVerificationSender.sendVerificationMail(firebaseAuth);

                                                String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                doctorService.addDoctor(uid, "name", email, passcode, "clinicId",
                                                        "medicalField", new ArrayList<String>(), phoneNumber,
                                                        0.00, gender);

                                                Toast.makeText(PhoneVerifyActivity.this, TAG + ": succeeded", Toast.LENGTH_SHORT).show();

                                            } else {

                                                Toast.makeText(PhoneVerifyActivity.this, TAG + ": failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(PhoneVerifyActivity.this, "Phone number NOT verified!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    private void setUpInputs() {
        mInputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mInputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mInputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mInputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mInputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mInputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mInputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mInputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mInputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mInputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
