package com.example.democontenta.utils;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private String readJsonFromAssets(Context context,String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> parseJson(String json) {
        List<String> serviceDefinitions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("serviceDefinitions");
            for (int i = 0; i < jsonArray.length(); i++) {
                String definition = jsonArray.getString(i);
                serviceDefinitions.add(definition);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serviceDefinitions;
    }
}

