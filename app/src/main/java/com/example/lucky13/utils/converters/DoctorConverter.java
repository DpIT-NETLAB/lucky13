package com.example.lucky13.utils.converters;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Doctor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorConverter {

    public static Map<String, Object> convertFromEntityToMap(@NonNull Doctor doctor) {

        Map<String, Object> doctorMap = new HashMap<>();

        doctorMap.put("id", doctor.getUID());
        doctorMap.put("passcode", doctor.getPasscode());
        doctorMap.put("email", doctor.getEmail());
        doctorMap.put("gender",  doctor.getGender());
        doctorMap.put("phone",  doctor.getPhone());
        doctorMap.put("review", doctor.getReview());
        doctorMap.put("workSchedule", doctor.getWorkSchedule());
        doctorMap.put("appointments", doctor.getAppointments());

        return doctorMap;
    }

    public Doctor convertFromMapToEntity(@NonNull Map<String, Object> doctorMap) {

        String UID = "",
                name = "",
                email = "",
                passcode = "",
                gender = "",
                phone = "",
                token= "";
        double review = 0.00;

        HashMap<String, String> workSchedule = new HashMap<>();
        HashMap<String, String> appointments = new HashMap<>();

        for (Map.Entry<String, Object> entry: doctorMap.entrySet()) {

            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("email"))
                email = (String) entry.getValue();
            else if (entry.getKey().equals("name"))
                name = (String) entry.getValue();
            else if (entry.getKey().equals("gender"))
                gender = (String) entry.getValue();
            else if (entry.getKey().equals("phone"))
               phone = (String) entry.getValue();
            else if (entry.getKey().equals("review"))
                review = (double) entry.getValue();
            else if (entry.getKey().equals("appointments"))
                appointments = (HashMap<String, String>) entry.getValue();
            else if (entry.getKey().equals("workSchedule"))
                workSchedule = (HashMap<String, String>) entry.getValue();
            else if (entry.getKey().equals("token"))
                token = (String) entry.getValue();
        }

        return new Doctor(
                UID,
                name,
                email,
                passcode,
                "",
                "",
                new ArrayList<>(),
                phone,
                review,
                gender,
                appointments,
                workSchedule,
                token
        );
    }
}
