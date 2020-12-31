package com.kbs.pocis.activity;

import android.Manifest;
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
import android.widget.Toast;

import com.kbs.pocis.HomeMenu;
import com.kbs.pocis.R;

public class HomePage extends AppCompatActivity {

    FragmentContainerView framehomepage;

    public static int PRIVATE_CODE = 1;
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

        framehomepage = findViewById(R.id.framehomepage);
        Permision();
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

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.framehomepage, fragment,"framentTujuan")
                .commit(); // setTrans itu untuk efeek transisi Fade
    }


    //Dibawah Ini Untuk Minta Permmisions Storage di Applikasi. Biar bisa access File Manager

    public void Permision(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.i("home", "Permision Storage: " + true);
            } else {
                IjinStorage();
            }
        }

    }

    public void IjinStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Membutuhkan Ijin")
                    .setMessage("Dibutuhkan ijin untuk menemukan File PDF di storage anda")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(HomePage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PRIVATE_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Ijin Berhasil Diberikan", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Ijin Gagal Diberikan", Toast.LENGTH_LONG).show();
            }
        }
    }
}