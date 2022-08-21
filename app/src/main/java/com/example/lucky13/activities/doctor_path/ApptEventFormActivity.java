package com.example.lucky13.activities.doctor_path;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lucky13.R;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class ApptEventFormActivity extends AppCompatActivity {

    public static final String TAG = "Add-Appt.-ACTIVITY";

    EditText mTitle,
            mDescription;
    TimePicker mStartTimePicker,
                mEndTimePicker;
    RadioButton mOneDayRadioBttn,
                mEveryDayRadioBttn,
                mEveryWeekRadioBttn;
    Button mAddEvent;

    String customEventMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_event_form);

        Intent incomingIntent = getIntent();
        Integer day = incomingIntent.getIntExtra("day", 1);
        Integer month = incomingIntent.getIntExtra("month", 1);
        Integer year = incomingIntent.getIntExtra("year", 2022);

        initialiseWidgets();

        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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
                int     startHour = mStartTimePicker.getHour(),
                        startMinute = mStartTimePicker.getMinute(),
                        endHour = mEndTimePicker.getHour(),
                        endMinute = mEndTimePicker.getMinute();

                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                beginTime.set(actualYear(year, month), previousMonth(month), day, startHour, startMinute);
                endTime.set(actualYear(year, month), previousMonth(month), day, endHour, endMinute);

                startActivity(getIntentByEventDetails(title, description, beginTime, endTime, "La clinică"));
            }
        });

        mOneDayRadioBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mEveryDayRadioBttn.setChecked(false);
                mEveryWeekRadioBttn.setChecked(false);

                customEventMessage = mOneDayRadioBttn.getText().toString();
            }
        });
        mEveryDayRadioBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOneDayRadioBttn.setChecked(false);
                mEveryWeekRadioBttn.setChecked(false);

                customEventMessage = mEveryDayRadioBttn.getText().toString();
            }
        });
        mEveryWeekRadioBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mEveryDayRadioBttn.setChecked(false);
                mOneDayRadioBttn.setChecked(false);

                customEventMessage = mEveryWeekRadioBttn.getText().toString();
            }
        });
    }

    private void initialiseWidgets() {

        mTitle = (EditText) findViewById(R.id.apptEventTitleText);
        mDescription = (EditText) findViewById(R.id.apptEventDescriptionText);
        mStartTimePicker = (TimePicker) findViewById(R.id.apptEventStartTimePicker);
        mEndTimePicker = (TimePicker) findViewById(R.id.apptEventEndTimePicker);
        mOneDayRadioBttn = (RadioButton) findViewById(R.id.apptEventRadioBttnOneDay);
        mEveryDayRadioBttn = (RadioButton) findViewById(R.id.apptEventRadioBttnEveryDay);
        mEveryWeekRadioBttn = (RadioButton) findViewById(R.id.apptEventRadioBttnEveryWeek);

        mAddEvent = (AppCompatButton) findViewById(R.id.apptEventAddEventButton);
    }

    @NonNull
    private Intent getIntentByEventDetails(String title, String description, @NonNull Calendar beginTime, @NonNull Calendar endTime, String location) {

        Intent intent = new Intent(Intent.ACTION_INSERT);

        intent.setData(CalendarContract.Events.CONTENT_URI);
        //intent.putExtra(CalendarContract.Events.ALL_DAY, false);// an appointment clearly takes only an hour or so
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location); // TODO: clinic's actual location

        return intent;
    }

    @NonNull
    @Contract(pure = true)
    private Integer previousMonth(Integer monthIndex) {

        if (monthIndex == 1)
            return 12;
        return monthIndex - 1;
    }

    private Integer actualYear(Integer yearIndex, Integer monthIndex) {

        if (monthIndex == 1)
            return yearIndex - 1;
        return yearIndex;
    }
}