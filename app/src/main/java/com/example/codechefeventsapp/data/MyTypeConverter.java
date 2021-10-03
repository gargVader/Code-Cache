package com.example.codechefeventsapp.data;

import androidx.room.TypeConverter;

import com.example.codechefeventsapp.data.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class MyTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static Set<User> stringToSetOfUsers(String data){
        if(data==null) return Collections.emptySet();

        Type setType = new TypeToken<Set<User>>(){}.getType();
        return gson.fromJson(data, setType);
    }

    @TypeConverter
    public static String SetOfUsersToString(Set<User> userSet){
        return gson.toJson(userSet);
    }

}
