package com.example.codechefeventsapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static Activity mActivity;
    private static SharedPref instance = null;
    private static SharedPreferences sharedPreferences;

    private SharedPref(Activity activity) {
        mActivity = activity;
        sharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
    }

    public static SharedPref getInstance(Activity activity) {
        if (instance == null) {
            instance = new SharedPref(activity);
        }
        return instance;
    }

    public boolean getBoolean(String key, boolean def) {
        return sharedPreferences.getBoolean(key, def);
    }

    public String getString(String key, String def) {
        return sharedPreferences.getString(key, def);
    }

    public void putBoolean(String key, boolean value){
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    public void putString(String key, String value){
        sharedPreferences.edit().putString(key, value).apply();
    }


}
