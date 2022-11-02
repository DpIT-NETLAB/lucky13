package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;

import com.example.lucky13.adapter.DiseaseAdapter;
import com.example.lucky13.models.Disease;
import com.example.lucky13.service.DiseaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DiseasesShowActivity extends AppCompatActivity {

    private static final String TAG = "DISEASES LIST:";

    DiseaseService diseaseService = new DiseaseService();

    private RecyclerView recyclerView;
    private DiseaseAdapter adapter;

    private ArrayList<Disease> diseaseArrayList;
    HashMap<Disease, Integer> diseaseMap;


    Button mFindDoctorsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_show);
        mFindDoctorsButton = findViewById(R.id.findDoctorsButton);

        Intent incomingIntent = getIntent();
        ArrayList<String> diseaseUIDs = incomingIntent.getStringArrayListExtra("diseaseUIDs");
        ArrayList<Integer> diseaseFrequency = incomingIntent.getIntegerArrayListExtra("diseaseUIDsFrequency");



        InitializeCard();

        ConvertToHashmap(diseaseUIDs, diseaseFrequency);

        //CreateDataForCards(diseaseMap);


        mFindDoctorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiseasesShowActivity.this, FindDoctorsNearby.class);

                intent.putExtra("number", "1");
                intent.putExtra("field", GetMostCommonField(diseaseArrayList, diseaseFrequency));

                startActivity(intent);
            }
            private String GetMostCommonField(ArrayList<Disease> list, ArrayList<Integer> diseaseFrequency) {
                String[] medicalFields = new String[list.size()];
                int i=0;
                for (Disease disease : list) {
                    medicalFields[i] = disease.getMedicalField();
                    i++;
                }

                HashMap<String, Integer> fieldOccurrences = new HashMap<String, Integer>();

                for (i=0; i<medicalFields.length; i++) {
                    if (fieldOccurrences.containsKey(medicalFields[i])) {
                        fieldOccurrences.put(medicalFields[i], fieldOccurrences.get(medicalFields[i]) + diseaseFrequency.get(i));
                    }
                    else {
                        fieldOccurrences.put(medicalFields[i], diseaseFrequency.get(i));
                    }
                }

                Set<Map.Entry<String, Integer>> set = fieldOccurrences.entrySet();
                String key="";
                int max=0;

                for (Map.Entry<String, Integer> field : set) {
                    if (field.getValue() > max) {
                        max = field.getValue();
                        key = field.getKey();

                    }
                }
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + key);
                return key;
            }

        });
    }

    private HashMap<Disease, Integer> SortDiseases(HashMap<Disease, Integer> diseaseMap) {

        List<Map.Entry<Disease, Integer>> list = new LinkedList<Map.Entry<Disease, Integer>>(diseaseMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Disease, Integer>>() {
            @Override
            public int compare(Map.Entry<Disease, Integer> freq1, Map.Entry<Disease, Integer> freq2) {
                return (freq1.getValue()).compareTo(freq2.getValue());
            }
        });
        HashMap<Disease, Integer> temp = new LinkedHashMap<Disease, Integer>();
        for (Map.Entry<Disease, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private void ConvertToHashmap(ArrayList<String> diseaseUIDs, ArrayList<Integer> diseaseFrequency) {
        diseaseMap = new HashMap<>();

        //ArrayList<Disease> diseaseList = new ArrayList<>();
        diseaseService.getAllDiseases();
        diseaseService.diseasesList.observe(this, diseaseList -> {
            diseaseArrayList.addAll(diseaseList);
            for (int i=0; i<diseaseUIDs.size(); i++) {
                for (Disease disease : diseaseArrayList) {
                    if (Objects.equals(disease.getUID(), diseaseUIDs.get(i))) {
                        Log.d(TAG, " @@@@@@@@@@@@@@@@@@@@@@@@ " + disease.getName());
                        diseaseMap.put(disease, diseaseFrequency.get(i));
                    }
                }
            }
            diseaseArrayList.clear();
            diseaseArrayList.addAll(diseaseMap.keySet());
            adapter.notifyDataSetChanged();
            String field = GetMostCommonField(diseaseArrayList, diseaseFrequency);
            Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + field);

        });
    }

    private void InitializeCard() {
        recyclerView = findViewById(R.id.diseases_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diseaseArrayList = new ArrayList<>();

        adapter = new DiseaseAdapter(this, diseaseArrayList);
        recyclerView.setAdapter(adapter);
    }

    private void CreateDataForCards(HashMap<Disease, Integer> diseaseMap) {
        //HashMap<Disease, Integer> sorted = SortDiseases(diseaseMap);
        diseaseArrayList = new ArrayList<>(diseaseMap.keySet());
        Log.d(TAG, "diseaseMap.");
        adapter.notifyDataSetChanged();
//        String field = GetMostCommonField(diseaseArrayList);
//        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + field);
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
            if (cnt != 0) {
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
        list.clear();
        list.addAll(mostRelevant);
    }

    private String GetMostCommonField(ArrayList<Disease> list, ArrayList<Integer> diseaseFrequency) {
        String[] medicalFields = new String[list.size()];
        int i=0;
        for (Disease disease : list) {
            medicalFields[i] = disease.getMedicalField();
            i++;
        }

        HashMap<String, Integer> fieldOccurrences = new HashMap<String, Integer>();

        for (i=0; i<medicalFields.length; i++) {
            if (fieldOccurrences.containsKey(medicalFields[i])) {
                fieldOccurrences.put(medicalFields[i], fieldOccurrences.get(medicalFields[i]) + diseaseFrequency.get(i));
            }
            else {
                fieldOccurrences.put(medicalFields[i], diseaseFrequency.get(i));
            }
        }

        Set<Map.Entry<String, Integer>> set = fieldOccurrences.entrySet();
        String key="";
        int max=0;

        for (Map.Entry<String, Integer> field : set) {
            if (field.getValue() > max) {
                max = field.getValue();
                key = field.getKey();

            }
        }
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + key);
        return key;
    }

}