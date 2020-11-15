package com.kbs.pocis.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import com.google.android.material.textfield.TextInputLayout;
import com.kbs.pocis.createboking.CustomerAddForm;
import com.kbs.pocis.R;
import com.kbs.pocis.service.UserData;

public class CreateBooking extends AppCompatActivity {

    ImageView icon_back;
    FragmentContainerView frameCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        ActivityClass.noAFK = true;
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
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogCancell(CreateBooking.this);
            }
        });

        FragmentList(new CustomerAddForm());

    }

    //Dialog form ketika cancelbutton click
    public void ShowDialogCancell (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_cancelled, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputLayout input_alasan = view.findViewById(R.id.onp);
        input_alasan.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.tl);
        ImageView bg = view.findViewById(R.id.bc);
        bg.setImageResource(R.drawable.show_cancel);
        title.setText("Are you sure want to quit create booking?");


        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_close.setText("Close");
        btn_close.setAllCaps(false);
        Button btn_cancelBoking = view.findViewById(R.id.btn_cancelbookinggo);
        btn_cancelBoking.setText("Yes");
        btn_cancelBoking.setAllCaps(false);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_cancelBoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData user = (UserData) getIntent().getParcelableExtra("user");
                Intent intent = new Intent(context, HomePage.class).putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        dialogFragment.show();
    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameCreate, fragment,"framentTujuan")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}
