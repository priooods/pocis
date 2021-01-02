package com.kbs.pocis.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.welcome.Login;
import com.kbs.pocis.welcome.Welcome_Screen;

import java.util.HashMap;

public class SessionManager {


    //TODO Class Session Update By Token
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String token = "keytoken";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createSession(String token){
        editor.putBoolean(is_login, true);
        editor.putString(token, token);
        editor.commit();
    }

    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, HomePage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(token, pref.getString(token, null));
        return user;
    }
}
