package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Clinic;

import java.util.HashMap;
import java.util.Map;

public class ClinicConverter {

    public static Map<String, Object> convertFromEntityToMap(Clinic clinic) {

        Map<String, Object> clinicMap = new HashMap<>();

        clinicMap.put("id", clinic.getUID());
        clinicMap.put("name", clinic.getName());
        clinicMap.put("averageReview", clinic.getAverageReview());
        clinicMap.put("location", clinic.getLocation());
        clinicMap.put("doctorUIDs", clinic.getDoctorUIDs());

        return clinicMap;
    }
}
