package com.example.lucky13.activities.patient_path;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.lucky13.service.QuestionService;
import com.example.lucky13.service.ResponseService;
import com.example.lucky13.service.SymptomService;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Triple;

public class PatientQuestionActivity extends AppCompatActivity {

    SymptomService symptomService = new SymptomService();
    QuestionService questionService = new QuestionService();
    ResponseService responseService = new ResponseService();

    Triple<String, String, String> relevantQuestions = new Triple<>("", "", "");
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<String> passedDiseaseUIDs = new ArrayList<>();
    ArrayList<Symptom> symptoms = new ArrayList<>();
    ArrayList<Response> responses = new ArrayList<>();

    Integer flag = 0;

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

        questionTextView = (TextView) findViewById(R.id.patientQuestionTextView);
        answearRadioButton1 = (RadioButton) findViewById(R.id.patientQuestionRadioButton1);
        answearRadioButton2 = (RadioButton) findViewById(R.id.patientQuestionRadioButton2);
        answearRadioButton3 = (RadioButton) findViewById(R.id.patientQuestionRadioButton3);

        Intent incomingIntent = getIntent();
        String pageOrder = incomingIntent.getStringExtra("pageOrder");
        ArrayList<String> givenSymptoms = incomingIntent.getStringArrayListExtra("symptoms");

        symptomService.getAllSymptoms();
        symptomService.symptomList.observe(this, symptomsList -> {

            symptoms.clear();
            symptoms.addAll(symptomsList);

            flag++;

            System.out.println(pageOrder);

            if (flag.equals(3)) {
                relevantQuestions = getMostRelevantQuestions(symptoms, givenSymptoms);

                switch (pageOrder) {
                    case "1":

                        passedDiseaseUIDs.clear();

                        Question firstQuestion = new Question();
                        for (Question question : questions) {

                            if (question.getId().equals(relevantQuestions.getFirst()))
                                firstQuestion = question;
                        }
                        ArrayList<String> firstQuestionResponses = firstQuestion.getResponses();


                        questionTextView.setText(firstQuestion.getText());
                        answearRadioButton1.setText(getQuestionByUID(this.questions, firstQuestionResponses.get(0)).getText());
                        answearRadioButton2.setText(getQuestionByUID(this.questions, firstQuestionResponses.get(1)).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);


                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(firstQuestionResponses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(firstQuestionResponses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });

                    case "2":

                        Question secondQuestion = getQuestionByUID(questions, relevantQuestions.getSecond());

                        ArrayList<String> question_Responses = secondQuestion.getResponses();
                        ArrayList<Response> secondQuestionResponses = new ArrayList<>();

                        for (String responseUID: question_Responses) {

                            secondQuestionResponses.add(getResponseByUID(this.responses, responseUID));
                        }

                        questionTextView.setText(secondQuestion.getText());
                        answearRadioButton1.setText(secondQuestionResponses.get(0).getText());
                        answearRadioButton2.setText(secondQuestionResponses.get(1).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);

                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(question_Responses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(question_Responses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });

                    case "3":

                        Question thirdQuestion = getQuestionByUID(questions, relevantQuestions.getThird());
                        ArrayList<String> thirdQuestionResponsesUIDs = thirdQuestion.getResponses();
                        ArrayList<Response> thirdQuestionResponses = new ArrayList<>();

                        for (String responseUID: thirdQuestionResponsesUIDs) {

                            thirdQuestionResponses.add(getResponseByUID(this.responses, responseUID));
                        }

                        questionTextView.setText(thirdQuestion.getText());
                        answearRadioButton1.setText(thirdQuestionResponses.get(0).getText());
                        answearRadioButton2.setText(thirdQuestionResponses.get(1).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);

                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(thirdQuestionResponsesUIDs.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(thirdQuestionResponsesUIDs.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                }
            }
        });

        questionService.getAllQuestions();
        questionService.questionList.observe(this, questionsList -> {

            questions.clear();
            questions.addAll(questionsList);

            flag++;

            if (flag.equals(3)) {
                relevantQuestions = getMostRelevantQuestions(symptoms, givenSymptoms);

                switch (pageOrder) {
                    case "1":

                        passedDiseaseUIDs.clear();

                        Question firstQuestion = new Question();
                        for (Question question : questions) {

                            if (question.getId().equals(relevantQuestions.getFirst()))
                                firstQuestion = question;
                        }
                        ArrayList<String> questionResponses = firstQuestion.getResponses();


                        questionTextView.setText(firstQuestion.getText());
                        answearRadioButton1.setText(getQuestionByUID(this.questions, questionResponses.get(0)).getText());
                        answearRadioButton2.setText(getQuestionByUID(this.questions, questionResponses.get(1)).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);


                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(questionResponses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(questionResponses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });

                    case "2":

                        Question secondQuestion = getQuestionByUID(questions, relevantQuestions.getSecond());

                        ArrayList<String> question_Responses = secondQuestion.getResponses();
                        ArrayList<Response> responses = new ArrayList<>();

                        for (String responseUID: question_Responses) {

                            responses.add(getResponseByUID(this.responses, responseUID));
                        }

                        questionTextView.setText(secondQuestion.getText());
                        answearRadioButton1.setText(responses.get(0).getText());
                        answearRadioButton2.setText(responses.get(1).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);

                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(question_Responses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(question_Responses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });

                    case "3":

                        Question thirdQuestion = getQuestionByUID(questions, relevantQuestions.getThird());
                        ArrayList<String> thirdQuestionResponsesUIDs = thirdQuestion.getResponses();
                        ArrayList<Response> thirdQuestionResponses = new ArrayList<>();

                        for (String responseUID: thirdQuestionResponsesUIDs) {

                            thirdQuestionResponses.add(getResponseByUID(this.responses, responseUID));
                        }

                        questionTextView.setText(thirdQuestion.getText());
                        answearRadioButton1.setText(thirdQuestionResponses.get(0).getText());
                        answearRadioButton2.setText(thirdQuestionResponses.get(1).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);

                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(thirdQuestionResponsesUIDs.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(thirdQuestionResponsesUIDs.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                }
            }
        });

        responseService.getAllResponses();
        responseService.responseList.observe(this, responseList -> {

            responses.clear();
            responses.addAll(responseList);

            flag++;

            if (flag.equals(3)) {
                relevantQuestions = getMostRelevantQuestions(symptoms, givenSymptoms);

                switch (pageOrder) {
                    case "1":

                        passedDiseaseUIDs.clear();

                        Question firstQuestion = new Question();
                        for (Question question : questions) {

                            if (question.getId().equals(relevantQuestions.getFirst()))
                                firstQuestion = question;
                        }
                        ArrayList<String> questionResponses = firstQuestion.getResponses();

                        questionTextView.setText(firstQuestion.getText());
                        answearRadioButton1.setText(getResponseByUID(this.responses, questionResponses.get(0)).getText());
                        answearRadioButton2.setText(getResponseByUID(this.responses, questionResponses.get(1)).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);


                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(questionResponses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(questionResponses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "2");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });

                    case "2":

                        Question secondQuestion = getQuestionByUID(questions, relevantQuestions.getSecond());

                        ArrayList<String> question_Responses = secondQuestion.getResponses();
                        ArrayList<Response> responses = new ArrayList<>();

                        for (String responseUID: question_Responses) {

                            responses.add(getResponseByUID(this.responses, responseUID));
                        }

                        questionTextView.setText(secondQuestion.getText());
                        answearRadioButton1.setText(responses.get(0).getText());
                        answearRadioButton2.setText(responses.get(1).getText());

                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(question_Responses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(question_Responses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putExtra("pageOrder", "3");
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });

                    case "3":

                        Question thirdQuestion = getQuestionByUID(questions, relevantQuestions.getThird());
                        ArrayList<String> thirdQuestionResponses = thirdQuestion.getResponses();

                        questionTextView.setText(thirdQuestion.getText());
                        answearRadioButton1.setText(getResponseByUID(this.responses, thirdQuestionResponses.get(0)).getText());
                        answearRadioButton2.setText(getResponseByUID(this.responses, thirdQuestionResponses.get(1)).getText());
                        answearRadioButton3.setText(R.string.thirdQuestionResponse);

                        answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(thirdQuestionResponses.get(0));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Response response = responseService.getResponse(thirdQuestionResponses.get(1));
                                passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                        answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                                intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                                startActivity(intent);
                            }
                        });
                }
            }
        });
    }

    @NonNull
    private Triple<String, String, String> getMostRelevantQuestions(ArrayList<Symptom> symptoms, @NonNull ArrayList<String> givenSymptoms) {

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

        return new Triple<> (
                relevantQuestion1,
                relevantQuestion2,
                relevantQuestion3
        );
    }
}