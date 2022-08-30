package com.example.lucky13.activities.doctor_path;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.lucky13.R;
import com.example.lucky13.models.Clinic;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.service.DoctorService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Contract;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


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

    HashMap<String, String> bookedTimes;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    DoctorService doctorService = new DoctorService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_event_form);

        bookedTimes = new HashMap<String, String>();

        Intent incomingIntent = getIntent();
        Integer day = incomingIntent.getIntExtra("day", 1);
        Integer month = incomingIntent.getIntExtra("month", 1);
        Integer year = incomingIntent.getIntExtra("year", 2022);

        initialiseWidgets();

        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        Log.d(TAG, "*************************************" + beginTime.get(Calendar.DAY_OF_MONTH) + ", " + beginTime.get(Calendar.MONTH) + ", "+beginTime.get(Calendar.YEAR)+", " +beginTime.get(Calendar.HOUR_OF_DAY)+", "+beginTime.get(Calendar.MINUTE)+", "+endTime.get(Calendar.HOUR_OF_DAY)+", "+endTime.get(Calendar.MINUTE));

        doctorService.doctorList.observe(this, doctorList -> {
            for (Doctor doctor : doctorList) {
                if (Objects.equals(doctor.getUID(), "1RYNbd0MqaZJUciswVHN")) {
                    bookedTimes.putAll(doctor.getAppointments());
                }
            }
        });


        int day = beginTime.get(Calendar.DAY_OF_MONTH),
                month = beginTime.get(Calendar.MONTH),
                year = beginTime.get(Calendar.YEAR),
                hour = beginTime.get(Calendar.HOUR_OF_DAY),
                minute = beginTime.get(Calendar.MINUTE),
                endHour = endTime.get(Calendar.HOUR_OF_DAY),
                endMinute = endTime.get(Calendar.MINUTE);

        Date start = new Date(2000, 10, 1, hour, minute);
        Date end = new Date(2000, 10, 1, endHour, endMinute);

        long diff = end.getTime() - start.getTime();

        long diff1 = diff / (60 * 60 * 1000) % 24;
        long diff2 = diff / (60 * 1000) % 60;
        String value = String.valueOf(diff1) + "," + String.valueOf(diff2);


        //long date = LocalDate.of(year, month, day).atTime(hour, minute).getLong(ChronoField.EPOCH_DAY);


        LocalDateTime localDateTime = LocalDateTime.of(year, month+1, day, hour, minute);
        ZoneId zoneId = ZoneId.of("Europe/Oslo");
        long date = localDateTime.atZone(zoneId).toEpochSecond();

        //long date1 = LocalDate.now().atTime(4,5).getLong(ChronoField.EPOCH_DAY);
        //String datestr = Long.toString(date
        //bookedTimes.put(date, diff);
        Log.d(TAG, "!!!!!!"+date);

        
        bookedTimes.put(Long.toString(date), value);

        firebaseFirestore.collection("Doctors").document("1RYNbd0MqaZJUciswVHN")
                .update("appointments", bookedTimes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

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