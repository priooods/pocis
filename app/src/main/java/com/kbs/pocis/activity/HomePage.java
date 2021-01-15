package com.kbs.pocis.activity;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.kbs.pocis.HomeMenu;
import com.kbs.pocis.R;
import com.kbs.pocis.service.UserData;
import com.valdesekamdem.library.mdtoast.MDToast;

public class HomePage extends AppCompatActivity {

    FragmentContainerView framehomepage;
    AppUpdateManager appUpdateManager;
    public static int REQUEST = 10;
    public static int PRIVATE_CODE = 1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        sharedPreferences = getSharedPreferences("sesi", Context.MODE_PRIVATE);
        if (UserData.isExists()) {
            Log.i("home", "onCreate: " + "Ada");
            UserData.i.setToken(sharedPreferences.getString("token", null));
            UserData.i.setCustId(sharedPreferences.getString("cust", null));
        } else {
            UserData data = new UserData(sharedPreferences.getString("token", null),sharedPreferences.getString("cust", null)
                ,sharedPreferences.getString("name", null));
            UserData.i = data;
            Log.i("home", "token: " + data.getToken());
            Log.i("home", "cust_id: " + data.getCustID());
            Log.i("home", "name: " + data.username);
        }
//        AutomatisUpdateApp();
        framehomepage = findViewById(R.id.framehomepage);
        Permission();
        FragmentList(new HomeMenu());
    }

    //Ini bisa dikatifin. double click baru Close APP. kalau click sekali muncul notif ( backToast )
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


    public void AutomatisUpdateApp(){
        appUpdateManager = AppUpdateManagerFactory.create(HomePage.this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
            if ((result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                try {
                    appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, HomePage.this, REQUEST);
                }catch (IntentSender.SendIntentException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.framehomepage, fragment,"framentTujuan")
                .commit(); // setTrans itu untuk efeek transisi Fade
    }


    //Minta Permission Storage di app access File Manager
    public void Permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.i("home", "Permission Storage: " + true);
            } else {
                IjinStorage();
            }
        }
    }

    public void IjinStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission Required")
                    .setMessage("You must give document opening permission to upload files")
                    .setPositiveButton("Yes", (dialog, which) -> ActivityCompat.requestPermissions(HomePage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE))
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PRIVATE_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                MDToast.makeText(this, "Permission Granted", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
            }
            else {
                MDToast.makeText(this, "Permission Not Given", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AutomatisUpdateApp();
    }

}