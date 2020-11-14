package com.kbs.pocis.api;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityClass extends AppCompatActivity {
    static public boolean noAFK = true;
    @Override
    protected void onPause() {
        super.onPause();
        if (noAFK) {
            Log.i("home", "onPause: Closing App Home Click ");
        }
        //finish();
    }
}
