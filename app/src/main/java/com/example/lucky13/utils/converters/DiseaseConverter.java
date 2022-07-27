package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Disease;

import java.util.HashMap;
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
}
