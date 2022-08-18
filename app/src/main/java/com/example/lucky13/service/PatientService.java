package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Patient;
import com.example.lucky13.repository.PatientRepository;
import com.example.lucky13.utils.converters.PatientConverter;

import java.util.ArrayList;

import kotlin.Triple;

public class PatientService {

    private final PatientRepository repository = new PatientRepository();
    private final PatientConverter converter = new PatientConverter();

    public LiveData<ArrayList<Patient>> patientList;

    @NonNull
    public void getAllPatients() {

        patientList = repository.getAllPatients();
    }

    public Patient getPatient(String UID) {

        return converter.convertFromMapToEntity(repository.getPatient(UID));
    }

    public void addPatient(String uid, String firstName, String lastName, Triple<Integer, Integer, Integer> dateOfBirth,
                           String email, double height, double weight, String bmi, String gender, ArrayList<String> medicalRecord) {

        Patient patient = new Patient(
                uid,
                firstName,
                lastName,
                dateOfBirth,
                email,
                height,
                weight,
                bmi,
                gender,
                medicalRecord
        );

        repository.addPatient(patient, PatientConverter.convertFromEntityToMap(patient));
    }
}
