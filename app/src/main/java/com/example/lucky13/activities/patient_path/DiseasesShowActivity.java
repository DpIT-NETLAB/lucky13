package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;

import com.example.lucky13.adapter.DiseaseAdapter;
import com.example.lucky13.models.Disease;
import com.example.lucky13.service.DiseaseService;

import java.util.ArrayList;
import java.util.List;

public class DiseasesShowActivity extends AppCompatActivity {

    private static final String TAG = "DISEASES LIST:";

    DiseaseService diseaseService = new DiseaseService();

    private RecyclerView recyclerView;
    private DiseaseAdapter adapter;

    private ArrayList<Disease> diseaseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_show);

        Intent incomingIntent = getIntent();
        ArrayList<String> symptoms = incomingIntent.getStringArrayListExtra("symptoms");

        InitializeCard();

        CreateDataForCards(symptoms);
    }

    private void InitializeCard() {
        recyclerView = findViewById(R.id.diseases_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diseaseArrayList = new ArrayList<>();

        adapter = new DiseaseAdapter(this, diseaseArrayList);
        recyclerView.setAdapter(adapter);
    }

    private void CreateDataForCards(ArrayList<String> symptoms) {

        diseaseService.getAllDiseases();
        diseaseService.diseasesList.observe(this, diseaseList -> {
            diseaseArrayList.addAll(diseaseList);
            CheckDisease(diseaseArrayList, symptoms);
            adapter.notifyDataSetChanged();
        });
    }

    private void CheckDisease(ArrayList<Disease> list, ArrayList<String> symptoms) {
        ArrayList<Disease> mostRelevant = new ArrayList<>();
        int[] counters = new int[list.size()];
        int i=0, max=0;
        for (Disease disease : list) {
            int cnt = 0;
            List<String> uids = disease.getSymptomUIDs();
            for (String uid : uids) {
                if (symptoms.contains(uid)) cnt++;
            }
            if (cnt == 0) list.remove(disease);
            else {
                counters[i] = cnt;
                if (counters[i] > max) max = counters[i];
                i++;
            }
            Log.d(TAG, "COUNTER " + cnt);
        }
        int length = counters.length;
        for (i=0; i<length; i++) {
            if (counters[i] == max-1) {
                mostRelevant.add(list.get(i));
            }
            if (counters[i] == max) {
                mostRelevant.add(0, list.get(i));
            }
            Log.d(TAG, "COUNTERS " + counters[i]);
        }
        for (Disease disease : mostRelevant) {
            Log.d(TAG, "#####################################################" + disease.getName());
        }
        list.clear();
        list.addAll(mostRelevant);
    }

}
