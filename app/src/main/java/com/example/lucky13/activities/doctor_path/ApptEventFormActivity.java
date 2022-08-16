package com.example.lucky13.activities.doctor_path;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucky13.R;


public class ApptEventFormActivity extends AppCompatActivity {

    public static final String TAG = "Add-Appt.-ACTIVITY";

    EditText mTitle,
            mDescription;
    Button mAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_event_form);

        Intent incomingIntent = getIntent();
        String patientEmail = incomingIntent.getStringExtra("patientEmail");

//        try {
//            patientEmail = incomingIntent.getStringExtra("patientEmail");
//        } catch (NullPointerException e) {
//            Log.d(TAG, "Error while receiving email. Error message: " + e.getMessage());
//        }

        mTitle = (EditText) findViewById(R.id.apptEventTitleText);
        mDescription = (EditText) findViewById(R.id.apptEventDescriptionText);
        mAddEvent = (AppCompatButton) findViewById(R.id.apptEventAddEventButton);

        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {

                if (mTitle.getText().toString().equals("")) {

                    mTitle.setError("Field empty!");
                    return;
                }
                if (mDescription.getText().toString().equals("")) {

                    mDescription.setError("Field empty!");
                    return;
                }

                String title = mTitle.getText().toString().trim();
                String description = mDescription.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, title);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "acasă");
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
                intent.putExtra(CalendarContract.Events.ALL_DAY, false);// an appointment clearly takes only an hour or so
                intent.putExtra(Intent.EXTRA_EMAIL, patientEmail);

                startActivity(intent);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(ApptEventFormActivity.this, "No app can support this action", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}