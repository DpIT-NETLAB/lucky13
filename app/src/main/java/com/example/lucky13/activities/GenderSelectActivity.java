package com.example.lucky13.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;

public class GenderSelectActivity extends AppCompatActivity {

    ImageButton mMaleButton,
                mFemaleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_choosing);
        Intent intent = new Intent(GenderSelectActivity.this, PatientInfoActivity.class);

        mMaleButton = findViewById(R.id.choosinggenderMaleButton);
        mFemaleButton = findViewById(R.id.choosinggenderFemaleButton);

        mMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("gender", "male");
                startActivity(intent);
            }
        });
        mFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("gender", "female");
                startActivity(intent);
            }
        });
    }
}