package com.example.lucky13.service;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Disease;
import com.example.lucky13.repository.DiseaseRepository;
import com.example.lucky13.utils.converters.DiseaseConverter;

import java.util.ArrayList;
import java.util.Map;

public class DiseaseService {

    private final DiseaseRepository repository = new DiseaseRepository();
    private final DiseaseConverter converter = new DiseaseConverter();

    @NonNull
    public ArrayList<Disease> getAllDiseases() {
        ArrayList<Disease> diseases = new ArrayList<>();

        ArrayList<Map<String, Object>> diseasesMap = repository.getAllDiseases();

        for (Map<String, Object> diseaseMap: diseasesMap) {
            diseases.add(converter.convertFromMapToEntity(diseaseMap));
        }
        return diseases;
    }

    public Disease getDisease(String UID) {

        return converter.convertFromMapToEntity(repository.getDiseaseMap(UID));
    }

}
