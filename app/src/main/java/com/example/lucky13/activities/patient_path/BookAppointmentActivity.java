package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;
import com.example.lucky13.adapter.SlotAdapter;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.service.DoctorService;

import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class BookAppointmentActivity extends AppCompatActivity {

    private static final String TAG = "AVAILABLE SLOTS: ";

    DoctorService doctorService = new DoctorService();

    SlotAdapter adapter;

    RecyclerView recyclerView;

    HashMap<LocalDateTime, String> bookedDates = new HashMap<>();
    HashMap<String, String> schedule = new HashMap<>();
    HashMap<LocalDate, String> finalDates = new HashMap<>();
    List<HashMap<LocalDate, String>> myList = new ArrayList<HashMap<LocalDate, String>>();

    LocalDate[] allDates;
    String[] allTimes;

    HashMap<String, String> appointments;

    Doctor doc;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent incomingIntent = getIntent();
        String id = incomingIntent.getStringExtra("id");


        doctorService.getAllDoctors();
        doctorService.doctorList.observe(this, doctorsList -> {
            for (Doctor doctor : doctorsList) {
                if (Objects.equals(doctor.getUID(), id)) {
                    InitializeCard(doctor);
                    Log.d(TAG, "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + doctor.getName());
                    GetDates(doctor);
                }
            }
        });
    }

    String weekDay;
    int startHour, endHour;
    int startMinute, endMinute;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GetDates(Doctor doctor) {

        HashMap<String, String> appointments = doctor.getAppointments();
        schedule = doctor.getWorkSchedule();

        for (String appointment : appointments.keySet()) {
            long epoch = Long.parseLong(appointment);
            LocalDateTime dateTime =
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch),
                            TimeZone.getDefault().toZoneId());
            Log.d(TAG, "@@@@@@@@@@@@@@@@@" + dateTime + appointments.get(appointment));
            bookedDates.put(dateTime, appointments.get(appointment));
        }
        LocalDateTime now  = LocalDateTime.now();
        now.get(ChronoField.DAY_OF_WEEK);
        String[] weekDays = new DateFormatSymbols().getWeekdays();

        String day_now = weekDays[now.get(ChronoField.DAY_OF_WEEK) + 1];

        Log.d(TAG, "TODAY: " + day_now);

        int cnt=0, i=0;
        while (cnt<4) {
            i++;
            if (Objects.equals(weekDays[now.get(ChronoField.DAY_OF_WEEK)], "Saturday")) {
                day_now = weekDays[1];
            }
            else day_now = weekDays[now.get(ChronoField.DAY_OF_WEEK) + i];
            for (String weekDay : schedule.keySet()) {
                Log.d(TAG, "WEEKDAY: " + weekDay + day_now);
                if (Objects.equals(day_now, weekDay)) {
                    String[] split = schedule.get(weekDay).split(",");

                    startHour = Integer.parseInt(split[0]);
                    startMinute = Integer.parseInt(split[1]);
                    endHour = Integer.parseInt(split[2]);
                    endMinute = Integer.parseInt(split[3]);

                    Log.d(TAG, "TIME: " + startHour + " " + startMinute + " " + endHour + " " + endMinute);

                    boolean check = false;
                    for (LocalDateTime date : bookedDates.keySet()) {
                        if (date.getYear() == now.getYear() && date.getDayOfYear() == now.getDayOfYear()) {
                            Log.d(TAG, "DATE: " + date);
                            check = true;
                            if (date.getHour() == startHour)  {
                                if (date.getMinute() < startMinute) {
                                    check = false;
                                }
                            }

                            Log.d(TAG, "CHECK: " + check);

                            LocalTime time = LocalTime.of(startHour, startMinute);
                            LocalTime dateTime = LocalTime.of(date.getHour(), date.getMinute());


                            if (date.getHour() > startHour && date.getHour()<endHour || check) {
                                while (dateTime.compareTo(time.plusMinutes(30)) >= 0) {
                                    LocalDate finalDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
                                    String finalInterval = time.getHour() + ":" + time.getMinute() + " - " + time.plusMinutes(30).getHour() + ":" + time.plusMinutes(30).getMinute();
                                    allDates[cnt] = finalDate;
                                    allTimes[cnt] = finalInterval;
                                    for (int j=0; j< allDates.length; j++) {
                                        Log.d(TAG, "DATES0: " + allDates[j] + allTimes[j]);
                                    }
                                    myList.add(finalDates);
                                    Log.d(TAG, "FINAL DATE: " + finalDates.get(finalDate) + finalDate);
                                    time = time.plusMinutes(30);
                                    cnt++;
                                }
                                for (LocalDate date1: allDates) {
                                    Log.d(TAG, "DATES1: " + date1);
                                }
                                LocalTime dateTime1 = time.plusMinutes(30);
                                startHour = dateTime1.getHour();
                                startMinute = dateTime1.getMinute();
                            }
                        }
                    }

                    Log.d(TAG, "CHECK: " + check);
                    if (!check) {
                        LocalTime time = LocalTime.of(startHour, startMinute);
                        LocalTime endTime = LocalTime.of(endHour, endMinute);

                        while (endTime.compareTo(time.plusMinutes(30)) <= 0) {
                            LocalDate finalDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
                            String finalInterval = time.getHour() + ":" + time.getMinute() + " - " + time.plusMinutes(30).getHour() + ":" + time.plusMinutes(30).getMinute();
                            allDates[cnt] = finalDate;
                            allTimes[cnt] = finalInterval;
                            for (int j=0; j< allDates.length; j++) {
                                Log.d(TAG, "DATES000: " + allDates[j] + allTimes[j]);
                            }
                            Log.d(TAG, "FINAL DATE: " + finalDate + finalInterval);
                            time = time.plusMinutes(30);
                            cnt++;
                        }
                    }


                }
            }
        }
        CreateDataForCards();


    }



    private void InitializeCard(Doctor doctor) {
        recyclerView = findViewById(R.id.slots_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allDates = new LocalDate[50];
        allTimes = new String[50];

        adapter = new SlotAdapter(this, allTimes, allDates, doctor);
        recyclerView.setAdapter(adapter);
    }

    private void CreateDataForCards() {

//        for (LocalDate date: finalDates.keySet()) {
//            Log.d(TAG, "DATES: " + date + finalDates.get(date));
//        }

        adapter.notifyDataSetChanged();
    }

}
