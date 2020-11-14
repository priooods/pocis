package com.kbs.pocis.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.api.OnClearExit;
import com.kbs.pocis.service.SessionManager;
import com.kbs.pocis.service.UserData;

public class SplashScreen extends AppCompatActivity {

    UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        startService(new Intent(getBaseContext(), OnClearExit.class));
//        if (SessionManager.getLoggedStatus(SplashScreen.this)){
//            startActivity(new Intent(SplashScreen.this, HomePage.class));
//            SplashScreen.this.finish();
//        }  else {
//            Handlerr();
//        }
//        user = //(UserData) getIntent().getParcelableExtra("user");
        if ( user!=null ? user.CheckTime() : false ){
            // Lanjut ke Home
            startActivity( new Intent(SplashScreen.this, HomePage.class).putExtra("user", user));
            SplashScreen.this.finish();
        }else{
            // Ke Login
            Handlerr();
        }


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