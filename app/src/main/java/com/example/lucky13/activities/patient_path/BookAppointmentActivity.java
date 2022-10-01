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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.TreeMap;

public class BookAppointmentActivity extends AppCompatActivity {

    private static final String TAG = "AVAILABLE SLOTS: ";

    DoctorService doctorService = new DoctorService();

    SlotAdapter adapter;

    RecyclerView recyclerView;

    HashMap<LocalDateTime, String> bookedDates = new HashMap<>();
    HashMap<String, String> schedule = new HashMap<>();
    HashMap<LocalDate, String> finalDates = new HashMap<>();
    List<HashMap<LocalDate, String>> myList = new ArrayList<HashMap<LocalDate, String>>();

    LocalDate[] allDates = new LocalDate[50];
    String[] allTimes = new String[50];

    LocalDate[] dates;
    String[] times;

    Map<LocalDateTime, String> appts = new TreeMap<>();



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
                    //InitializeCard(doctor);
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

        for (String day: schedule.keySet()) {
            Log.d(TAG, day);
        }

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


        int cnt=0;
        while (cnt<4) {
            day_now = weekDays[now.get(ChronoField.DAY_OF_WEEK) + 1];
            for (String weekDay : schedule.keySet()) {
                Log.d(TAG, "WEEKDAY: " + weekDay + day_now);
                if (Objects.equals(day_now, weekDay)) {
                    String[] split = schedule.get(weekDay).split(",");

                    startHour = Integer.parseInt(split[0]);
                    startMinute = Integer.parseInt(split[1]);
                    endHour = Integer.parseInt(split[2]);
                    endMinute = Integer.parseInt(split[3]);

                    Log.d(TAG, "TIME: " + startHour + " " + startMinute + " " + endHour + " " + endMinute);

                    LocalTime startTime = LocalTime.of(startHour, startMinute);
                    LocalTime endTime = LocalTime.of(endHour, endMinute);

                    boolean check = false;
                    for (LocalDateTime date : bookedDates.keySet()) {
                        LocalTime dateTime = LocalTime.of(date.getHour(), date.getMinute());
                        Log.d(TAG, "ALL TIMES: " + startTime + "   " + endTime + "   " + dateTime);
                        if (date.getYear() == now.getYear() && date.getDayOfYear() == now.getDayOfYear() && dateTime.compareTo(startTime) >= 0) {
                            Log.d(TAG, "DATE: " + date);
                            appts.put(date, bookedDates.get(date));
                            check = true;
                        }

                        Log.d(TAG, "CHECK: " + check);
                    }
                    for (LocalDateTime date1: appts.keySet()) {
                        Log.d(TAG, "APPOINTMENTS: " + date1 + "     " + appts.get(date1));
                    }
                    if (check) {
                        for (LocalDateTime dateTime : appts.keySet()) {
                            LocalTime tempET = dateTime.toLocalTime();
                            Log.d(TAG, "END TIMEEEEEEE:" + tempET);
                            while (startTime.compareTo(tempET) <0) {
                                Log.d(TAG, "COMPARE: " + startTime.plusMinutes(30).compareTo(tempET));
                                Log.d(TAG, "START TIMEEEEEEE:" + startTime);
                                LocalDate finalDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
                                String finalInterval = startTime.getHour() + ":" + startTime.getMinute() + " - " + startTime.plusMinutes(30).getHour() + ":" + startTime.plusMinutes(30).getMinute();
                                Log.d(TAG, "START TIME: " + finalInterval);
                                allDates[cnt] = finalDate;
                                allTimes[cnt] = finalInterval;
                                cnt++;
                                startTime = startTime.plusMinutes(30);
                            }
                            startTime = tempET.plusMinutes(30);

                        }
                        while (startTime.plusMinutes(30).compareTo(endTime) <=0) {
                            LocalDate finalDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
                            String finalInterval = startTime.getHour() + ":" + startTime.getMinute() + " - " + startTime.plusMinutes(30).getHour() + ":" + startTime.plusMinutes(30).getMinute();
                            Log.d(TAG, "START TIME: " + finalInterval);
                            allDates[cnt] = finalDate;
                            allTimes[cnt] = finalInterval;
                            startTime = startTime.plusMinutes(30);
                            cnt++;
                        }
                    }


//
//
//                            if (date.getHour() > startHour && date.getHour() < endHour || check) {
//                                while (dateTime.compareTo(time.plusMinutes(30)) >= 0) {
//                                    LocalDate finalDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
//                                    String finalInterval = time.getHour() + ":" + time.getMinute() + " - " + time.plusMinutes(30).getHour() + ":" + time.plusMinutes(30).getMinute();
//                                    allDates[cnt] = finalDa\te;
//                                    allTimes[cnt] = finalInterval;
//                                    for (int j = 0; j < allDates.length; j++) {
//                                        Log.d(TAG, "DATES0: " + allDates[j] + allTimes[j]);
//                                    }
//                                    myList.add(finalDates);
//                                    Log.d(TAG, "FINAL DATE: " + finalDates.get(finalDate) + finalDate);
//                                    time = time.plusMinutes(30);
//                                    cnt++;
//                                }
//                                for (LocalDate date1 : allDates) {
//                                    Log.d(TAG, "DATES1: " + date1);
//                                }
//                                LocalTime dateTime1 = time.plusMinutes(30);
//                                startHour = dateTime1.getHour();
//                                startMinute = dateTime1.getMinute();
//                            }
                    if (!check) {
                        LocalTime time = LocalTime.of(startHour, startMinute);

                        Log.d(TAG, "START TIME: " + time);
                        Log.d(TAG, "END TIME: " + endTime);

                        while (endTime.compareTo(time.plusMinutes(30)) >= 0) {
                            LocalDate finalDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
                            String finalInterval = time.getHour() + ":" + time.getMinute() + " - " + time.plusMinutes(30).getHour() + ":" + time.plusMinutes(30).getMinute();
                            Log.d(TAG, "START TIME: " + finalInterval);
                            allDates[cnt] = finalDate;
                            allTimes[cnt] = finalInterval;
                            for (int j = 0; j < allDates.length; j++) {
                                Log.d(TAG, "DATES000: " + allDates[j] + allTimes[j]);
                            }
                            Log.d(TAG, "FINAL DATE: " + finalDate + finalInterval);
                            time = time.plusMinutes(30);
                            Log.d(TAG, "START TIME: " + time);
                            cnt++;
                        }
                    }


                }
                now = now.plusDays(1);
            }
        }
        dates = Arrays.copyOf(allDates, cnt);
        times = Arrays.copyOf(allTimes, cnt);

        allDates = Arrays.copyOf(dates, cnt);
        allTimes = Arrays.copyOf(times, cnt);
        CreateDataForCards(doctor);


    }



    private void InitializeCard() {
    }

    private void CreateDataForCards(Doctor doctor) {

//        for (LocalDate date: finalDates.keySet()) {
//            Log.d(TAG, "DATES: " + date + finalDates.get(date));
//        }
        recyclerView = findViewById(R.id.slots_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new SlotAdapter(this, allTimes, allDates, doctor);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}
