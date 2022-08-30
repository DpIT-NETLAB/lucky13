package com.example.lucky13.utils.converters;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Clinic;
import com.example.lucky13.repository.ClinicRepository;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
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

    public Clinic convertFromMapToEntity(@NonNull Map<String, Object> clinicMap) {

        String UID = "",
                name = "";
        double averageReview = 0.00;
        ArrayList<String> doctorUIDs = new ArrayList<>();
        GeoPoint location = new GeoPoint(0, 0);

        for (Map.Entry<String, Object> entry: clinicMap.entrySet()) {

            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("name"))
                name = (String) entry.getValue();
            else if (entry.getKey().equals("doctorUIDs"))
                doctorUIDs = (ArrayList<String>) entry.getValue();
            else if (entry.getKey().equals("location"))
                location = (GeoPoint) entry.getValue();
            else if (entry.getKey().equals("averageReview"))
                averageReview = (double) entry.getValue();
        }

        return new Clinic(
                UID,
                name,
                averageReview,
                doctorUIDs,
                location
        );
    }
}
