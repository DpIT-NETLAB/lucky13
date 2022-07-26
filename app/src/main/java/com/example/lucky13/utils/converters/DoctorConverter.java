package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Doctor;

import java.util.HashMap;
import java.util.Map;

public class DoctorConverter {

    public static Map<String, Object> convertFromEntityToMap(Doctor doctor) {

        Map<String, Object> doctorMap = new HashMap<>();

        doctorMap.put("id", doctor.getUID());
        doctorMap.put("email", doctor.getEmail());
        doctorMap.put("passcode", doctor.getPasscode());

        return doctorMap;
    }
}
