package com.kbs.pocis.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.service.SessionManager;

public class SplashScreen extends AppCompatActivity {

    String tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Intent intent = getIntent();
        tokens = intent.getStringExtra("tokens");

//        if (SessionManager.getLoggedStatus(SplashScreen.this)){
//            startActivity(new Intent(SplashScreen.this, HomePage.class));
//            SplashScreen.this.finish();
//        }  else {
//            Handlerr();
//        }
        Handlerr();

    }


    public void Handlerr(){
        int TIMING = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Welcome_Screen.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }
        }, TIMING);
    }

}