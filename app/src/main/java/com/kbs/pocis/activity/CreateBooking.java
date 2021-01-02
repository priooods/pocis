package com.kbs.pocis.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.createboking.CustomerAddForm;
import com.kbs.pocis.R;
import com.kbs.pocis.item.Dialog_Cancel;

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
        icon_back.setOnClickListener(v -> {
            DialogFragment fragment = new Dialog_Cancel();
            fragment.show(getSupportFragmentManager(),"dialog");
        });

        FragmentList(new CustomerAddForm());

    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frameCreate, fragment,"framentTujuan").commit();
    }
}
