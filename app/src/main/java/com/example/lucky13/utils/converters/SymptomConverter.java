package com.example.lucky13.utils.converters;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Symptom;

import java.util.HashMap;
import java.util.Map;

public class SymptomConverter {

    public static Map<String, Object> convertFromEntityToMap(Symptom symptom) {

        Map<String, Object> symptomMap = new HashMap<>();

        symptomMap.put("id", symptom.getUID());
        symptomMap.put("name", symptom.getName());
        symptomMap.put("relatedQuestion", symptom.getRelatedQuestion());

        return symptomMap;
    }

    public Symptom convertFromMapToEntity(@NonNull Map<String, Object> symptomMap) {
        String UID = "",
                name = "",
                relatedQuestion = "";

        for (Map.Entry<String, Object> entry: symptomMap.entrySet()) {
            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("name"))
                name = (String) entry.getValue();
            else if (entry.getKey().equals("relatedQuestion"))
                relatedQuestion = (String) entry.getValue();
        }
        return new Symptom(
                UID,
                name,
                relatedQuestion
        );
    }

}
