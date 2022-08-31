package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;
import com.example.lucky13.adapter.DiseaseAdapter;
import com.example.lucky13.adapter.DoctorAdapter;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.service.DoctorService;

import java.util.ArrayList;

public class DoctorsShowActivity extends AppCompatActivity {

    private static final String TAG = "DOCTORS LIST";

    DoctorService doctorService = new DoctorService();

    private RecyclerView recyclerView;
    private DoctorAdapter adapter;

    private ArrayList<Doctor> doctorArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_show);

        Intent incomingIntent = getIntent();
        String field = incomingIntent.getStringExtra("field");

        InitializeCard();

        CreateDataForCards(field);
    }

    private void InitializeCard() {
        recyclerView = findViewById(R.id.doctors_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorArrayList = new ArrayList<>();

        adapter = new DoctorAdapter(this, doctorArrayList, null); //TODO: location to be modified
        recyclerView.setAdapter(adapter);
    }

    private void CreateDataForCards(String field) {
        doctorService.getAllDoctors();
        doctorService.doctorList.observe(this, doctorList -> {
            doctorArrayList.addAll(doctorList);
            ModifyList(doctorArrayList, field);
            adapter.notifyDataSetChanged();
        });
    }

    private void ModifyList(ArrayList<Doctor> list, String field) {
        ArrayList<Doctor> doctorsInField = new ArrayList<>();

        for (Doctor doctor : list) {
            if (field.equals(doctor.getMedicalField())) {
                Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + doctor.getName());
                doctorsInField.add(doctor);
            }
        }
        list.clear();
        list.addAll(doctorsInField);
    }

}
