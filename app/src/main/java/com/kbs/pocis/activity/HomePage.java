package com.kbs.pocis.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kbs.pocis.HomeMenu;
import com.kbs.pocis.R;
import com.kbs.pocis.api.ActivityClass;

public class HomePage extends ActivityClass {

    private long doubleBackTime;
    private Toast backToast;
    Service service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityClass.noAFK = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        FragmentList(new HomeMenu());
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackTime + 2000 > System.currentTimeMillis()){
//            backToast.cancel();
//            super.onBackPressed();
//        }
//        else{
//            backToast = Toast.makeText(getBaseContext(), "Press once again to exit",
//                    Toast.LENGTH_SHORT);
//            backToast.show();
//            doubleBackTime = System.currentTimeMillis();
//        }
//    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framehomepage, fragment,"framentTujuan")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}