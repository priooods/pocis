package com.kbs.pocis.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.service.SessionManager;
import com.kbs.pocis.service.UserData;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Handler handler;
    SessionManager SessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sharedPreferences = getSharedPreferences("sesi", Context.MODE_PRIVATE);
        //Splash Screen di set 2 Second
        //logo masukin sini dulu bi . upload nya mana di fime manager lu
        Handlerr();

    }

    public void Handlerr(){
        int TIMING = 2000;
        handler = new Handler();
        handler.postDelayed(() -> {
            if (sharedPreferences.getString("token", null) == null) {
                startActivity(new Intent(SplashScreen.this, Welcome_Screen.class));
            } else {
                startActivity(new Intent(SplashScreen.this, HomePage.class));
            }
//            startActivity(new Intent(SplashScreen.this, Welcome_Screen.class));
            SplashScreen.this.finish();
        }, TIMING);
    }

}