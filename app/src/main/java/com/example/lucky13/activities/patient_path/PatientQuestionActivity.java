package com.example.lucky13.activities.patient_path;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.WelcomePage;
import com.example.lucky13.models.Question;
import com.example.lucky13.models.Response;
import com.example.lucky13.models.Symptom;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PatientQuestionActivity extends AppCompatActivity {

    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Response> responses = new ArrayList<>();
    ArrayList<Symptom> symptoms = new ArrayList<>();

    Integer pageOrder = 0;
    ArrayList<String> checkedSymptoms = new ArrayList<>();
    ArrayList<String> passedDiseaseUIDs = new ArrayList<>();
    ArrayList<Integer> passedDiseaseUIDsFreq = new ArrayList<>();
    ArrayList<String> relevantQuestions = new ArrayList<>();
    ArrayList<Response> currentResponses = new ArrayList<>();

    TextView questionTextView;
    RadioButton answearRadioButton1;
    RadioButton answearRadioButton2;
    RadioButton answearRadioButton3;


    @NonNull
    private Question getQuestionByUID(@NonNull ArrayList<Question> questions, String UID) {

        for (Question question: questions) {

            if (question.getId().equals(UID))
                return question;
        }

        return new Question();
    }
    @NonNull
    private Symptom getSymptomByUID(@NonNull ArrayList<Symptom> symptoms, String UID) {

        for (Symptom symptom: symptoms) {

            if (symptom.getId().equals(UID))
                return symptom;
        }

        return new Symptom();
    }
    @NonNull
    private Response getResponseByUID(@NonNull ArrayList<Response> responses, String UID) {

        for (Response response: responses) {

            if (response.getId().equals(UID))
                return response;
        }

        return new Response();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_question);

        //TODO: welcome page to be changed
        Intent nextIntent = new Intent(PatientQuestionActivity.this, DiseasesShowActivity.class);

        questionTextView = (TextView) findViewById(R.id.patientQuestionTextView);
        answearRadioButton1 = (RadioButton) findViewById(R.id.patientQuestionRadioButton1);
        answearRadioButton2 = (RadioButton) findViewById(R.id.patientQuestionRadioButton2);
        answearRadioButton3 = (RadioButton) findViewById(R.id.patientQuestionRadioButton3);

        Intent getIntent = getIntent();
        symptoms = (ArrayList<Symptom>) getIntent.getSerializableExtra("symptoms");
        questions = (ArrayList<Question>) getIntent.getSerializableExtra("questions");
        responses = (ArrayList<Response>) getIntent.getSerializableExtra("responses");
        checkedSymptoms = (ArrayList<String>) getIntent.getSerializableExtra("checkedSymptoms");

        relevantQuestions = getMostRelevantQuestions(symptoms, checkedSymptoms);


        String questionUID = relevantQuestions.get(pageOrder);
        Question question = getQuestionByUID(this.questions, questionUID);

        currentResponses.clear();
        for (String responseUID : question.getResponses())
            currentResponses.add(getResponseByUID(this.responses, responseUID));

        questionTextView.setText(question.getText());

        answearRadioButton1.setText(currentResponses.get(0).getText());
        answearRadioButton2.setText(currentResponses.get(1).getText());
        answearRadioButton3.setText(R.string.thirdQuestionResponse);


        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pageOrder == 2) {

                    addDiseaseUIDs(getDiseaseUIDsForResponse(currentResponses, 0));

                    nextIntent.putStringArrayListExtra("diseaseUIDs", passedDiseaseUIDs);
                    nextIntent.putIntegerArrayListExtra("diseaseUIDsFrequency", passedDiseaseUIDsFreq);

                    for (String diseaseUID: passedDiseaseUIDs)
                        System.out.println("UID: " + diseaseUID);
                    startActivity(nextIntent);
                } else {

                    ArrayList<Response> currentResponses = loadQuestion(++pageOrder);
                    addDiseaseUIDs(getDiseaseUIDsForResponse(currentResponses, 0));
                    clearCheckRadioButtons();
                }
            }
        });
        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pageOrder == 2) {

                    addDiseaseUIDs(getDiseaseUIDsForResponse(currentResponses, 1));

                    nextIntent.putStringArrayListExtra("diseaseUIDs", passedDiseaseUIDs);
                    nextIntent.putIntegerArrayListExtra("diseaseUIDsFrequency", passedDiseaseUIDsFreq);

                    startActivity(nextIntent);
                } else {

                    ArrayList<Response> currentResponses = loadQuestion(++pageOrder);
                    addDiseaseUIDs(getDiseaseUIDsForResponse(currentResponses, 1));
                    clearCheckRadioButtons();
                }
            }
        });
        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pageOrder == 2) {

                    nextIntent.putStringArrayListExtra("diseaseUIDs", passedDiseaseUIDs);
                    nextIntent.putIntegerArrayListExtra("diseaseUIDsFrequency", passedDiseaseUIDsFreq);

                    startActivity(nextIntent);
                } else {

                    loadQuestion(++pageOrder);
                    clearCheckRadioButtons();
                    // response #3 doesn't have any diseaseUIDs attached to it, since its just text: "I don't know"
                    // addDiseaseUIDs(getDiseaseUIDsForResponse(currentResponses, 0));
                }
            }
        });

    }

    private void clearCheckRadioButtons() {

        answearRadioButton1.setChecked(false);
        answearRadioButton2.setChecked(false);
        answearRadioButton3.setChecked(false);
    }

    @NonNull
    private ArrayList<Response> loadQuestion(Integer pageOrder) {

        String questionUID = relevantQuestions.get(pageOrder);
        Question question = getQuestionByUID(this.questions, questionUID);
        ArrayList<Response> responses = new ArrayList<>();

        for (String responseUID: question.getResponses())
            responses.add(getResponseByUID(this.responses, responseUID));

        questionTextView.setText(question.getText());

        answearRadioButton1.setText(responses.get(0).getText());
        answearRadioButton2.setText(responses.get(1).getText());
        answearRadioButton3.setText(R.string.thirdQuestionResponse);

        return responses;
    }

    private ArrayList<String> getDiseaseUIDsForResponse(@NonNull ArrayList<Response> currentResponses, Integer responseOrder) {

        return currentResponses.get(responseOrder).getDiseaseUIDs();
    }

    @Contract(pure = true)
    private void addDiseaseUIDs(@NonNull ArrayList<String> responseDiseaseUIDs) {

        for (String diseaseUID: responseDiseaseUIDs) {

            if (this.passedDiseaseUIDs.contains(diseaseUID)) {
                int position = this.passedDiseaseUIDs.indexOf(diseaseUID);
                passedDiseaseUIDsFreq.set(position, passedDiseaseUIDsFreq.get(position) + 1);
            }
            else {
                passedDiseaseUIDs.add(diseaseUID);
                passedDiseaseUIDsFreq.add(1);
            }
        }
    }

    @NonNull
    private ArrayList<String> getMostRelevantQuestions(ArrayList<Symptom> symptoms, @NonNull ArrayList<String> givenSymptoms) {

        Map<String, Integer> questionsFrequencyMap = new HashMap<>();

        for (String symptomUID: givenSymptoms) {

            Symptom symptom = getSymptomByUID(symptoms, symptomUID);

            for (String relatedQuestionUID: symptom.getRelatedQuestions()) {

                if (!questionsFrequencyMap.containsKey(relatedQuestionUID))
                    questionsFrequencyMap.put(relatedQuestionUID, 1);
                else
                    questionsFrequencyMap.put(relatedQuestionUID, questionsFrequencyMap.get(relatedQuestionUID) + 1);
            }
        }

        Integer maxFreq1 = -1,
                maxFreq2 = -1,
                maxFreq3 = -1;
        String relevantQuestion1 = "",
                relevantQuestion2 = "",
                relevantQuestion3 = "";

        for (Map.Entry<String, Integer> entry: questionsFrequencyMap.entrySet()) {

            System.out.println(entry.getValue());

            if (entry.getValue() > maxFreq3) {

                if (entry.getValue() >= maxFreq2) {

                    if (entry.getValue() >= maxFreq1) {

                        relevantQuestion3 = relevantQuestion2;
                        relevantQuestion2 = relevantQuestion1;
                        relevantQuestion1 = entry.getKey();
                    } else {

                        relevantQuestion3 = relevantQuestion2;
                        relevantQuestion2 = entry.getKey();
                    }
                } else {

                    relevantQuestion3 = entry.getKey();
                }
            }
        }

        return new ArrayList<>(
                Arrays.asList(relevantQuestion1,
                        relevantQuestion2,
                        relevantQuestion3
                ));
    }
}