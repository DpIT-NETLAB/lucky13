package com.example.lucky13.utils.converters;

import com.example.lucky13.models.Response;

import java.util.ArrayList;
import java.util.Map;

public class ResponseConverter {

    public Response convertFormMapToEntity(Map<String, Object> responseMap) {

        String UID = "",
                text = "";
        ArrayList<String> diseaseUIDs = new ArrayList<>();

        for (Map.Entry<String, Object> entry: responseMap.entrySet()) {

            if (entry.getKey().equals("id"))
                UID = (String) entry.getValue();
            else if (entry.getKey().equals("text"))
                text = (String) entry.getValue();
            else if (entry.getKey().equals("diseaseUIDs"))
                diseaseUIDs = (ArrayList<String>) entry.getValue();
        }

        return new Response(
                UID,
                text,
                diseaseUIDs
        );
    }
}
