package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Disease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiseaseConverter {

    public static Map<String, Object> convertFromEntityToMap(Disease disease) {

        Map<String, Object> diseaseMap = new HashMap<>();

        diseaseMap.put("id", disease.getUID());
        diseaseMap.put("name", disease.getName());
        diseaseMap.put("medicalField", disease.getMedicalField());
        diseaseMap.put("symptomUIDs", disease.getSymptomUIDs());

        return diseaseMap;
    }


    public Disease convertFromMapToEntity(Map<String, Object> diseaseMap) {

        String UID = "",
                name = "",
                medicalField = "";
        List<String> symptomsUIDs = new ArrayList<>();

        for (Map.Entry<String, Object> entryMap: diseaseMap.entrySet()) {
            if (entryMap.getKey().equals("id"))
                UID = (String) entryMap.getValue();
            else if (entryMap.getKey().equals("name"))
                name = (String) entryMap.getValue();
            else if (entryMap.getKey().equals("medicalField"))
                medicalField = (String) entryMap.getValue();
            else if (entryMap.getKey().equals("symptomUIDs"))
                symptomsUIDs = (List<String>) entryMap.getValue();
        }

        return new Disease (
                UID,
                name,
                medicalField,
                symptomsUIDs
        );
    }
}
