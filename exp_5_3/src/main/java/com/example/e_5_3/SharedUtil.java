package com.example.e_5_3;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

    private static final String PREF_NAME = "shopping_pref";

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }
}
