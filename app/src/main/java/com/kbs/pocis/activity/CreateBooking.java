package com.kbs.pocis.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.createboking.CustomerAddForm;
import com.kbs.pocis.R;
import com.kbs.pocis.item.Dialog_Cancel;
import com.valdesekamdem.library.mdtoast.MDToast;

public class CreateBooking extends AppCompatActivity {

    ImageView icon_back;
    FragmentContainerView frameCreate;
    public static int REQUEST_SETTING = 168;
    public static int PRIVATE_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_booking);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        frameCreate = findViewById(R.id.frameCreate);

        //Kembali ke Layout Sebelumnya
        icon_back = findViewById(R.id.btn_back_create_booking);
        icon_back.setOnClickListener(v -> {
            DialogFragment fragment = new Dialog_Cancel();
            fragment.show(getSupportFragmentManager(),"dialog");
        });
        Permission();

        FragmentList(new CustomerAddForm());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PRIVATE_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                MDToast.makeText(this, "Permission Granted", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
            }
            else {
                onBackPressed();
                finish();}
        }
    }

    public void openSetting(){
        Intent setting = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
        setting.addCategory(Intent.CATEGORY_DEFAULT);
        setting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(setting,REQUEST_SETTING);
    }

    public void Permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.i("home", "Permission Storage: " + true);
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                MDToast.makeText(this, "You Must Have Enable Permission", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                openSetting();
            }
            else {
                IjinStorage();
            }
        }
    }

    public void IjinStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission Required")
                    .setMessage("You must give document opening permission to upload files")
                    .setPositiveButton("Yes", (dialog, which) -> ActivityCompat.requestPermissions(CreateBooking.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE))
                    .setNegativeButton("No", (dialog, which) ->{
                        dialog.dismiss();
                        onBackPressed();
                        finish();
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
        }
    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frameCreate, fragment,"framentTujuan").commit();
    }
}
