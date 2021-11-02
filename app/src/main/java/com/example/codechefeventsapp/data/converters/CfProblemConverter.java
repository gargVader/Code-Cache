package com.example.codechefeventsapp.data.converters;

import androidx.room.TypeConverter;

import com.example.codechefeventsapp.data.models.cf.CfProblem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CfProblemConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static CfProblem stringToObject(String data) {
        if (data == null) return null;

        Type setType = new TypeToken<CfProblem>() {
        }.getType();
        return gson.fromJson(data, setType);
    }

    @TypeConverter
    public static String ObjectToString(CfProblem cfProblem) {
        return gson.toJson(cfProblem);
    }

}
