package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Doctor;
import com.example.lucky13.repository.DoctorRepository;
import com.example.lucky13.utils.converters.DoctorConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorService {

    private final DoctorRepository repository = new DoctorRepository();
    private final DoctorConverter converter = new DoctorConverter();

    public LiveData<ArrayList<Doctor>> doctorList;

    @NonNull
    public void getAllDoctors() {

        doctorList = repository.getAllDoctors();
    }

    public Doctor getDoctor(String UID) {

        return converter.convertFromMapToEntity(repository.getDoctor(UID));
    }

    public void addDoctor(String UID, String name, String email, String passcode, String clinicId, String medicalField,
                          ArrayList<String> languages, String phone, double review, String gender, HashMap<String, String> workSchedule, String token) {

        Doctor doctor = new Doctor(
                UID,
                name,
                email,
                passcode,
                clinicId,
                medicalField,
                languages,
                phone,
                review,
                gender,
                new HashMap<String, String>(),
                workSchedule,
                token
        );

        repository.addDoctor(doctor, DoctorConverter.convertFromEntityToMap(doctor));
    }
}
