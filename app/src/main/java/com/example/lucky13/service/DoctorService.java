package com.example.lucky13.service;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Doctor;
import com.example.lucky13.repository.DoctorRepository;
import com.example.lucky13.utils.converters.DoctorConverter;

import java.util.ArrayList;
import java.util.Map;

public class DoctorService {

    private final DoctorRepository repository = new DoctorRepository();
    private final DoctorConverter converter = new DoctorConverter();

    @NonNull
    public ArrayList<Doctor> getAllDoctors() {

        ArrayList<Doctor> doctors = new ArrayList<>();

        ArrayList<Map<String, Object>> doctorsMap = repository.getAllDoctors();

        for (Map<String, Object> doctorMap: doctorsMap) {

            doctors.add(converter.convertFromMapToEntity(doctorMap));
        }

        return doctors;
    }

    public Doctor getDoctor(String UID) {

        return converter.convertFromMapToEntity(repository.getDoctor(UID));
    }

    public void addDoctor(String UID, String name, String email, String passcode, String clinicId, String medicalField,
                          ArrayList<String> languages, String phone, double review, String gender) {

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
                gender
        );

        repository.addDoctor(doctor, DoctorConverter.convertFromEntityToMap(doctor));
    }
}
