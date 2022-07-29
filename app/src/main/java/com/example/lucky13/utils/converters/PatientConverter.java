package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientConverter {

    public static Map<String, Object> convertFromEntityToMap(Patient patient) {

        Map<String, Object> patientMap = new HashMap<>();

        patientMap.put("id", patient.getUID());
        patientMap.put("email", patient.getEmail());
        patientMap.put("firstName", patient.getFirstName());
        patientMap.put("lastName", patient.getLastName());
        patientMap.put("gender", patient.getGender());

        return patientMap;
    }
}
