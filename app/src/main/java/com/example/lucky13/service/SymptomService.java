package com.example.lucky13.service;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Symptom;
import com.example.lucky13.repository.SymptomRepository;
import com.example.lucky13.utils.converters.SymptomConverter;

import java.util.ArrayList;
import java.util.Map;

public class SymptomService {

    private final SymptomRepository repository = new SymptomRepository();
    private final SymptomConverter converter = new SymptomConverter();

    @NonNull
    public ArrayList<Symptom> getAllSymptoms() {

        ArrayList<Symptom> symptoms = new ArrayList<>();

        ArrayList<Map<String, Object>> symptomsMap = repository.getAllSymptoms();

        for (Map<String, Object> symptomMap: symptomsMap) {

            symptoms.add(converter.convertFromMapToEntity(symptomMap));
        }

        return symptoms;
    }

    public Symptom getSymptom(String UID) {

        return converter.convertFromMapToEntity(repository.getSymptomMap(UID));
    }

}
