package com.example.lucky13.activities.patient_path;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.WelcomePage;
import com.example.lucky13.models.Symptom;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class GeneralSymptomSelect extends AppCompatActivity {

    AppCompatButton mDoneButton;
    ChipGroup mChipGroup;

    ArrayList<Symptom> symptoms = new ArrayList<>();
    ArrayList<Boolean> checkedChip = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_symptom_select);

        //TODO: aici ar veni ceva tip repository.get general symptoms
        symptoms.add(new Symptom("123", "raceala", "Ai raceala?"));
        symptoms.add(new Symptom("234", "febra", "Ai febra?"));
        symptoms.add(new Symptom("345", "icter", "Ai ochii galbeni?"));
        symptoms.add(new Symptom("456", "stafilococ", "Ai raceala?"));
        symptoms.add(new Symptom("567", "infarct", "Ai raceala?"));
        symptoms.add(new Symptom("678", "olog", "Ai raceala?"));
        symptoms.add(new Symptom("789", "handicap", "Ai raceala?"));

        mChipGroup = findViewById(R.id.generalSymptomSelectChipGroup);
        mDoneButton = findViewById(R.id.generalSymptomSelectButton);

        addSymptomChips(this.symptoms);

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> toSendSymptoms = getAllCheckedSymptoms(symptoms, checkedChip);

                // TODO: trebuie sa modificam din "WelcomePage.class" in altceva
                Intent intent = new Intent(GeneralSymptomSelect.this, WelcomePage.class);
                intent.putStringArrayListExtra("symptoms", toSendSymptoms);

                startActivity(intent);
            }
        });
    }

    public void addSymptomChips(@NonNull ArrayList<Symptom> symptoms) {

        ChipGroup chipGroup = findViewById(R.id.generalSymptomSelectChipGroup);

        for (Symptom symptom: symptoms) {

            Chip chip = new Chip(this);

            chip.setText(symptom.getName());
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.darker_blue_gray)));
            chipGroup.addView(chip);
            checkedChip.add(false);

            chip.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {

                    if (checkedChip.get(chip.getId() - 1)) {

                        checkedChip.set(chip.getId() - 1, false);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.darker_blue_gray)));
                    } else {

                        checkedChip.set(chip.getId() - 1, true);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.topic_social_pressed)));
                    }
                }
            });
        }
    }

    public ArrayList<String> getAllCheckedSymptoms(@NonNull ArrayList<Symptom> symptoms, @NonNull ArrayList<Boolean> checkedChip) {

        ArrayList<String> checkedSymptoms = new ArrayList<>();

        for (int i = 0; i < checkedChip.size(); i++) {

            if (checkedChip.get(i)) {

                checkedSymptoms.add(symptoms.get(i).getUID());
            }
        }

        return checkedSymptoms;
    }
}