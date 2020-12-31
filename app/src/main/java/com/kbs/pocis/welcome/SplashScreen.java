package com.kbs.pocis.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kbs.pocis.R;

public class SplashScreen extends AppCompatActivity {

//    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //Splash Screen di set 2 Second
        Handlerr();

    }


    public void Handlerr(){
        int TIMING = 2000;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, Welcome_Screen.class));
            SplashScreen.this.finish();
        }, TIMING);
    }

}