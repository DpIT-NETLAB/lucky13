package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Clinic;
import com.example.lucky13.repository.ClinicRepository;
import com.example.lucky13.utils.converters.ClinicConverter;

import java.util.ArrayList;

public class ClinicService {
    private final ClinicRepository repository = new ClinicRepository();
    private final ClinicConverter converter = new ClinicConverter();

    public LiveData<ArrayList<Clinic>> clinicList;

    @NonNull
    public void getAllClinics() {

        clinicList = repository.getAllClinics();
    }

    public Clinic getClinic(String UID) {

        return converter.convertFromMapToEntity(repository.getClinic(UID));
    }
}
