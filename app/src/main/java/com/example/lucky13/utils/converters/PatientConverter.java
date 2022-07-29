package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Triple;

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

    public Patient convertFromMapToEntity(Map<String, Object> patientMap) {

        String UID = "",
                email = "",
                firstName = "",
                lastName = "",
                gender = "";

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
        }

        return new Patient(
                UID,
                firstName,
                lastName,
                new Triple(0, 0, 0),
                email,
                0,
                0,
                gender,
                new ArrayList<>()
        );
    }
}
