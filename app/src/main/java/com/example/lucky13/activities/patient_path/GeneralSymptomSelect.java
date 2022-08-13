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
import com.example.lucky13.models.Question;
import com.example.lucky13.models.Response;
import com.example.lucky13.models.Symptom;
import com.example.lucky13.service.QuestionService;
import com.example.lucky13.service.ResponseService;
import com.example.lucky13.service.SymptomService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class GeneralSymptomSelect extends AppCompatActivity {

    SymptomService symptomService = new SymptomService();
    QuestionService questionService = new QuestionService();
    ResponseService responseService = new ResponseService();

    AppCompatButton mDoneButton;
    ChipGroup mChipGroup;

    ArrayList<Symptom> symptoms = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Response> responses = new ArrayList<>();
    ArrayList<Boolean> checkedChip = new ArrayList<>();

    Integer servicesFinished = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_symptom_select);

        checkedChip = new ArrayList<>();
        symptomService.getAllSymptoms();
        symptomService.symptomList.observe(this, symptomList -> {
            symptoms.addAll(symptomList);
            servicesFinished++;

            addSymptomChips(this.symptoms);
        });
        questionService.getAllQuestions();
        questionService.questionList.observe(this, questionList -> {
            questions.addAll(questionList);
            servicesFinished++;
        });
        responseService.getAllResponses();
        responseService.responseList.observe(this, responseList -> {
            responses.addAll(responseList);
            servicesFinished++;
        });

        mChipGroup = findViewById(R.id.generalSymptomSelectChipGroup);
        mDoneButton = findViewById(R.id.generalSymptomSelectButton);

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (servicesFinished.equals(3)) {

                    ArrayList<String> toSendSymptoms = getAllCheckedSymptoms(symptoms, checkedChip);

                    Intent intent = new Intent(GeneralSymptomSelect.this, PatientQuestionActivity.class);

                    intent.putExtra("symptoms", symptoms);
                    intent.putExtra("questions", questions);
                    intent.putExtra("responses", responses);
                    intent.putStringArrayListExtra("checkedSymptoms", toSendSymptoms);

                    startActivity(intent);
                }
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

                checkedSymptoms.add(symptoms.get(i).getId());
            }
        }

        return checkedSymptoms;
    }
}