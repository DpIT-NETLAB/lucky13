package com.example.lucky13.activities.doctor_path;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.service.DoctorService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Objects;

public class GeneralScheduleActivity extends AppCompatActivity {

    public static final String TAG = "SCHEDULE: ";

    DoctorService doctorService = new DoctorService();

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    ChipGroup mChipGroup;
    AppCompatButton mDoneButton,
                    mSeeCalendar;

    TimePicker mStartTimePicker,
                mEndTimePicker;

    HashMap<String, String> schedule, scheduleTest;

    int startHour, startMinute, endHour, endMinute;

    String prevDay = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_schedule);

        mChipGroup = findViewById(R.id.daysChipGroup);
        mDoneButton = findViewById(R.id.doneButton);
        mSeeCalendar = findViewById(R.id.seeCalendar);

        mStartTimePicker = findViewById(R.id.startTimePicker);
        mEndTimePicker = findViewById(R.id.endTimePicker);

        String[] weekDays = new DateFormatSymbols().getWeekdays();

        schedule = new HashMap<String, String>();
        String day = addChips(weekDays);


        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule.put(day, startHour + ","+ startMinute + "," + endHour + "," + endMinute);
                addSchedule();
            }
        });

        mSeeCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GeneralScheduleActivity.this, CalendarActivity.class);

                startActivity(intent);
            }
        });
    }

    private void addSchedule() {

       // doctorService.getAllDoctors();
//        doctorService.doctorList.observe(this, doctorList -> {
//            Doctor doctor = doctorList.get(1);
//            doctor.setWorkSchedule(schedule);
//            Log.d(TAG, "!!!!!!!!!!!!!!!!!!!" + doctor.getWorkSchedule());
//        });
        for (String key : schedule.keySet()) {
            Log.d(TAG, key);
        }

        firebaseFirestore.collection("Doctors").document("1RYNbd0MqaZJUciswVHN")
                .update("workSchedule", schedule)
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
    }

    private String addChips(String[] days) {
        for (String day : days) {
            Chip chip = new Chip(this);

            chip.setText(day);
            mChipGroup.addView(chip);

            startHour = 0;
            startMinute = 0;
            endHour = 0;
            endMinute = 0;

            chip.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.darker_blue_gray)));
                    String time = startHour + ","+ startMinute + "," + endHour + "," + endMinute;
                    if (!Objects.equals(prevDay, "0")) {
                        schedule.put(prevDay, time);
                    }
                    Log.d(TAG, day + startHour + ","+ startMinute + "," + endHour + "," + endMinute);

                    startHour = mStartTimePicker.getHour();
                    startMinute = mStartTimePicker.getMinute();
                    endHour = mEndTimePicker.getHour();
                    endMinute = mEndTimePicker.getMinute();
                    prevDay = day;

                }
            });
        }
        return prevDay;
    }
}
