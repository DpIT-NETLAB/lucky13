package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Symptom;

import java.util.HashMap;
import java.util.Map;

public class SymptomConverter {

    public static Map<String, Object> convertFromEntityToMap(Symptom symptom) {

        Map<String, Object> diseaseMap = new HashMap<>();

        diseaseMap.put("id", symptom.getUID());
        diseaseMap.put("name", symptom.getName());
        diseaseMap.put("relatedQuestion", symptom.getRelatedQuestion());

        return diseaseMap;
    }
}
