package com.kbs.pocis.api;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OnClearExit extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("lala", "UNBIND UNQUE MESSAGE I FOUND YOU!");
        return null;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("lala", "UNIQUE MESSAGE I FOUND YOU!");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        Log.e("lala", "ON DESTROY! UNIQUE MESSAGE I FOUND YOU!");
        super.onDestroy();
    }
}
