package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Symptom;
import com.example.lucky13.repository.SymptomRepository;
import com.example.lucky13.utils.converters.SymptomConverter;

import java.util.ArrayList;

public class SymptomService {

    private final SymptomRepository repository = new SymptomRepository();
    private final SymptomConverter converter = new SymptomConverter();

    public LiveData<ArrayList<Symptom>> symptomList;

    @NonNull
    public void getAllSymptoms() {

        symptomList = repository.getAllSymptoms();
    }

    public Symptom getSymptom(String UID) {

        return converter.convertFromMapToEntity(repository.getSymptomMap(UID));
    }

}
