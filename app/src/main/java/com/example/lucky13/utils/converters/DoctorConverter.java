package com.example.lucky13.utils.converters;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorConverter {

    public static Map<String, Object> convertFromEntityToMap(@NonNull Doctor doctor) {

        Map<String, Object> doctorMap = new HashMap<>();

        doctorMap.put("id", doctor.getUID());
        doctorMap.put("passcode", doctor.getPasscode());
        doctorMap.put("email", doctor.getEmail());
        doctorMap.put("gender",  doctor.getGender());
        doctorMap.put("review", doctor.getReview());

        return doctorMap;
    }

    public Doctor convertFromMapToEntity(@NonNull Map<String, Object> doctorMap) {

        String UID = "",
                name = "",
                email = "",
                passcode = "",
                gender = "";
        double review = 0.00;

        for (Map.Entry<String, Object> entry: doctorMap.entrySet()) {

            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("email"))
                email = (String) entry.getValue();
            else if (entry.getKey().equals("name"))
                name = (String) entry.getValue();
            else if (entry.getKey().equals("gender"))
                gender = (String) entry.getValue();
            else if (entry.getKey().equals("review"))
                review = (double) entry.getValue();
        }

        return new Doctor(
                UID,
                name,
                email,
                passcode,
                "",
                "",
                new ArrayList<>(),
                "",
                review,
                gender
        );
    }
}
