package com.kbs.pocis.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

import com.kbs.pocis.api.ActivityClass;
import com.kbs.pocis.createboking.CustomerAddForm;
import com.kbs.pocis.R;

public class CreateBooking extends ActivityClass {

    ImageView icon_back;
    FragmentContainerView frameCreate;
    private static int PRIVATE_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityClass.noAFK = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_booking);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        Permision();
        frameCreate = findViewById(R.id.frameCreate);

        //Kembali ke Layout Sebelumnya
        icon_back = findViewById(R.id.btn_back_create_booking);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateBooking.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        FragmentList(new CustomerAddForm());

    }

    public void Permision(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            Toasty.info(this, "Anda harus memberikan ijin untuk melanjutkan", Toasty.LENGTH_LONG, true).show();
        } else {
            IjinStorage();
        }
    }

    public void IjinStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Membutuhkan Ijin")
                    .setMessage("Ijin ini dibutuhkan untuk melanjutkan Membuat Booking")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CreateBooking.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
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
                Toast.makeText(this, "Ijin Gagal DIberikan", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameCreate, fragment,"framentTujuan")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}
