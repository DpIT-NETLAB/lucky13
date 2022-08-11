package com.example.lucky13.activities.doctor_path;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;

import java.util.Objects;

public class PhoneVerifyActivity extends AppCompatActivity {

    EditText mInputCode1,
            mInputCode2,
            mInputCode3,
            mInputCode4;

    AppCompatButton mVerifyPhoneNumberButton;

    String verificationId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);

        mInputCode1 = findViewById(R.id.inputCode1);
        mInputCode2 = findViewById(R.id.inputCode2);
        mInputCode3 = findViewById(R.id.inputCode3);
        mInputCode4 = findViewById(R.id.inputCode4);

        setUpInputs();

        mVerifyPhoneNumberButton = findViewById(R.id.doctorCreateGetCodeButton);

        verificationId = getIntent().getStringExtra("verificationId");

        mVerifyPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInputCode1.getText().toString().trim().isEmpty()
                        || mInputCode2.getText().toString().trim().isEmpty()
                        || mInputCode3.getText().toString().trim().isEmpty()
                        || mInputCode4.getText().toString().trim().isEmpty()) {
                    mVerifyPhoneNumberButton.setEnabled(false);
                    return;
                }
                String code = mInputCode1.getText().toString() +
                        mInputCode2.getText().toString() +
                        mInputCode3.getText().toString() +
                        mInputCode4.getText().toString();

                if (Objects.equals(verificationId, code)) {
                    Toast.makeText(PhoneVerifyActivity.this,"Phone number verified successfully!", Toast.LENGTH_SHORT).show();
                }

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
    }
}
