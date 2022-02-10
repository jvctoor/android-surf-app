package com.example.waves013;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DatabaseHelper {

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public DatabaseHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("databasePicos", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveData(ArrayList<Pico> picosList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("picos", gson.toJson(picosList));
        editor.apply();
    }

    public ArrayList<Pico> getData() {
        String praiasString = sharedPreferences.getString("picos", null);
        Type type = new TypeToken<ArrayList<Pico>>() {}.getType();
        ArrayList<Pico> picosList = gson.fromJson(praiasString, type);
        if(picosList != null) {
            return picosList;
        } else {
            return new ArrayList<>();
        }

    }

}
