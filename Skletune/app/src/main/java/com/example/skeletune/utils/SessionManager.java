package com.example.skeletune.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "SkeletuneSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveUser(int id, String name) {
        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }

    public int getUserId() { return pref.getInt(KEY_USER_ID, -1); }
    public void logout() { editor.clear(); editor.apply(); }

    // Agrega esto a tu clase SessionManager
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "Usuario");
    }
}