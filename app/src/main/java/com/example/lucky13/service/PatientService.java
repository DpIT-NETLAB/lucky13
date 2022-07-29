package com.example.lucky13.service;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Patient;
import com.example.lucky13.repository.PatientRepository;
import com.example.lucky13.utils.converters.PatientConverter;

import java.util.ArrayList;
import java.util.Map;

import kotlin.Triple;

public class PatientService {

    private final PatientRepository repository = new PatientRepository();
    private final PatientConverter converter = new PatientConverter();

    @NonNull
    public ArrayList<Patient> getAllPatients() {

        ArrayList<Patient> patients = new ArrayList<>();

        ArrayList<Map<String, Object>> patientsMap = repository.getAllPatients();

        for (Map<String, Object> patientMap: patientsMap) {

            patients.add(converter.convertFromMapToEntity(patientMap));
        }

        return patients;
    }

    public Patient getPatient(String UID) {

        return converter.convertFromMapToEntity(repository.getPatient(UID));
    }

    public void addPatient(String uid, String firstName, String lastName, Triple<Integer, Integer, Integer> dateOfBirth,
                           String email, double height, double weight, String gender, ArrayList<String> medicalRecord) {

        Patient patient = new Patient(
                uid,
                firstName,
                lastName,
                dateOfBirth,
                email,
                height,
                weight,
                gender,
                medicalRecord
        );

        repository.addPatient(patient, PatientConverter.convertFromEntityToMap(patient));
    }
}
