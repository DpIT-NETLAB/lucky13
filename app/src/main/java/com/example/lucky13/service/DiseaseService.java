package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Disease;
import com.example.lucky13.repository.DiseaseRepository;
import com.example.lucky13.utils.converters.DiseaseConverter;

import java.util.ArrayList;

public class DiseaseService {

    private final DiseaseRepository repository = new DiseaseRepository();
    private final DiseaseConverter converter = new DiseaseConverter();

    public LiveData<ArrayList<Disease>> diseasesList;

    @NonNull
    public void getAllDiseases() {

        diseasesList = repository.getAllDiseases();
    }

    public Disease getDisease(String UID) {

        return converter.convertFromMapToEntity(repository.getDiseaseMap(UID));
    }

}
