package com.bibitproject.aplikasidatachart.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bibitproject.aplikasidatachart.BuildConfig;


public class SessionManager {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void login(String userid, String access) {
        editor.putString("uid", userid);
        editor.putString("access", access);
        editor.putBoolean("logged", true);
        editor.commit();
    }

    public void logout() {
        editor.putString("uid", null);
        editor.putString("access", null);
        editor.putBoolean("logged", false);
        editor.commit();
    }

    public boolean isLoggedin() {
        return preferences.getBoolean("logged", false);
    }

    public String getUserid() {
        return preferences.getString("uid", "");
    }

    public String getUserAccess() {
        return preferences.getString("access", "");
    }
}
