package com.kbs.pocis.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.R;
import com.kbs.pocis.onlineboking.AllBookings;
import com.kbs.pocis.onlineboking.OnlineBooking;
import com.kbs.pocis.onlineboking.TarifApprove;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.UserService;

public class OnlineBook extends AppCompatActivity {

    LinearLayout tarif_approve, online_booking_onlinebooking;
    TextView id_booking, id_tarif;
    ImageView icon_booking, icon_tarif,create_booking;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_book_dasar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        tarif_approve = findViewById(R.id.online_booking_tarif_aprove);
        online_booking_onlinebooking = findViewById(R.id.online_booking_onlinebooking);
        id_booking = findViewById(R.id.id_icon_onlineboking);
        id_tarif = findViewById(R.id.id_icon_tarifapprove);
        icon_booking = findViewById(R.id.icon_onlineboking);
        icon_tarif = findViewById(R.id.icon_tarifapprove);
        create_booking = findViewById(R.id.online_booking_create_booking);

        tarif_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_booking.setTextColor(getResources().getColor(R.color.colorGrey));
                icon_booking.setImageResource(R.drawable.booking_icon_grey);
                id_tarif.setTextColor(getResources().getColor(R.color.colorWhite));
                icon_tarif.setImageResource(R.drawable.icon_dollar);
                Fragment fragment = new TarifApprove();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameOnline, fragment);
                fragmentTransaction.commit();
            }
        });

        online_booking_onlinebooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_booking.setTextColor(getResources().getColor(R.color.colorWhite));
                icon_booking.setImageResource(R.drawable.booking_icon_white);
                id_tarif.setTextColor(getResources().getColor(R.color.colorGrey));
                icon_tarif.setImageResource(R.drawable.dollar_icon_grey);

                Fragment fragment = new OnlineBooking();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameOnline, fragment);
                fragmentTransaction.commit();
            }
        });

        FragmentList(new OnlineBooking());

        create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineBook.this, CreateBooking.class);
                startActivity(intent);
            }
        });
    }

    public void FragmentList(Fragment fragment){
        UserData user = (UserData) getIntent().getParcelableExtra("user");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameOnline, fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }



}
