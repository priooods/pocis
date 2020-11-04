package com.kbs.pocis.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.fragment.AllBookings;
import com.kbs.pocis.fragment.CancelBooking;

public class OnlineBooking extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView icon_back, icon_search, create_booking;
    LinearLayout tarif_approve;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_booking);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        icon_back = findViewById(R.id.btn_back_online_booking);
        icon_search = findViewById(R.id.btn_search_online_booking);
        tabLayout = findViewById(R.id.tabLayout_onlineBook);
        viewPager = findViewById(R.id.viewpager_onlineBooking);
        create_booking = findViewById(R.id.online_booking_create_booking);
        tarif_approve = findViewById(R.id.online_booking_tarif_aprove);


        //Ini adalah adapter Viewpager Default ( Bisa di scroll Horizontal )
        ViewpagerDefault adapter = new ViewpagerDefault(this.getSupportFragmentManager());
        //Taruh fragment ke view pager lalu pasang ke tablayout
        adapter.Addfragment(new AllBookings(), "All Bookings");
        adapter.Addfragment(new CancelBooking(), "Cancel Booking");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Icon Arrow Back Click
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineBooking.super.onBackPressed();
            }
        });

        //Icon Search Pada Online Booking CLick
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(OnlineBooking.this);
            }
        });

        create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineBooking.this, CreateBooking.class);
                startActivity(intent);
            }
        });

        tarif_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineBooking.this, TarifApprove.class);
                startActivity(intent);
            }
        });
    }

    //Ini adalah function untuk Menampilkan Dialog Searching yah
    public void ShowDialog (Context context){
        View view  = getLayoutInflater().inflate(R.layout.dialog_filters, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextInputEditText input_nomerBooking = view.findViewById(R.id.filter_nomerbooking);
        TextInputEditText input_veselName = view.findViewById(R.id.filter_veselname);
        Button btn_cancel = view.findViewById(R.id.btn_cancelbooking);
        Button btn_filtering = view.findViewById(R.id.btn_filterbooking);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_filtering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        dialogFragment.show();
    }

}