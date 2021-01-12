package com.kbs.pocis.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class SessionManager extends Service {

    CountDownTimer countDownTimer;
    public static boolean check;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (check) {
            countDownTimer = new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i(TAG, "onTick: " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i(TAG, "onFinish: " + "Selesai");
                    stopSelf();
                }
            };
            countDownTimer.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Session Manager: " + "Service Destroy");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

}

