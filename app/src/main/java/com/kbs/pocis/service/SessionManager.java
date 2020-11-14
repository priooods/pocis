package com.kbs.pocis.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    public static final String LOGGED_IN_PREF = "logged_in_status";

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, UserData user) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_PREF, user.username);
        editor.putLong(LOGGED_IN_PREF, user.getTime());
        editor.putString(LOGGED_IN_PREF, user.getToken());
        editor.apply();
    }

    public static UserData getLoggedStatus(Context context,UserData userData) {
        String name = getPreferences(context).getString(LOGGED_IN_PREF,null);
        long time = getPreferences(context).getLong(LOGGED_IN_PREF,0);
        String token = getPreferences(context).getString(LOGGED_IN_PREF,null);

        return new UserData(name,token,time);
    }

}
