package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Question;

import java.util.ArrayList;
import java.util.Map;

public class QuestionConverter {

    public Question convertFromMapToEntity(Map<String, Object> questionMap) {

        String UID = "",
                text = "";
        ArrayList<String> responses = new ArrayList<>();

        for (Map.Entry<String, Object> entry: questionMap.entrySet()) {

            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("text"))
                text = (String) entry.getValue();
            else if (entry.getKey().equals("responses"))
                responses = (ArrayList<String>) entry.getValue();
        }

        return new Question(
                UID,
                text,
                responses
        );
    }
}
