package com.example.lucky13.utils.converters;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Triple;

public class PatientConverter {

    @NonNull
    public static Map<String, Object> convertFromEntityToMap(@NonNull Patient patient) {

        Map<String, Object> patientMap = new HashMap<>();

        patientMap.put("id", patient.getUID());
        patientMap.put("email", patient.getEmail());
        patientMap.put("firstName", patient.getFirstName());
        patientMap.put("lastName", patient.getLastName());
        patientMap.put("gender", patient.getGender());
        patientMap.put("height", patient.getHeight());
        patientMap.put("weight", patient.getWeight());
        patientMap.put("BMI", patient.getBMI());

        return patientMap;
    }

    public Patient convertFromMapToEntity(@NonNull Map<String, Object> patientMap) {

        String UID = "",
                email = "",
                firstName = "",
                lastName = "",
                gender = "",
                BMI = "";
        double height = 0,
                weight = 0;

        for (Map.Entry<String, Object> entry: patientMap.entrySet()) {

            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("email"))
                email = (String) entry.getValue();
            else if (entry.getKey().equals("firstName"))
                firstName = (String) entry.getValue();
            else if (entry.getKey().equals("lastName"))
                lastName = (String) entry.getValue();
            else if (entry.getKey().equals("gender"))
                gender = (String) entry.getValue();
            else if (entry.getKey().equals("height"))
                height = (double) entry.getValue();
            else if (entry.getKey().equals("weight"))
                weight = (double) entry.getValue();
            else if (entry.getKey().equals("BMI"))
                BMI = (String) entry.getValue();
        }

        return new Patient(
                UID,
                firstName,
                lastName,
                new Triple(0, 0, 0),
                email,
                height,
                weight,
                BMI,
                gender,
                new ArrayList<>()
        );
    }
}
