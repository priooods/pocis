package com.kbs.pocis.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.fragment.BookingDetails_Commodity;
import com.kbs.pocis.fragment.BookingDetails_Information;
import com.kbs.pocis.fragment.BookingDetails_Service;

public class BookingDetails extends AppCompatActivity {

    String from, status, nomer;
    TextView topfrom, nomerBooking, statusBooking;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);

        //Ini untuk actionBar jadi white. Karena min sdk 16 jadi ga bisa di sett di style. harus lewat JAVA lngsung
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        //Ini untuk mengambil kembali data yang sudah dikirim dari adapter AllBookings atau Online Booking
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        status = intent.getStringExtra("status");
        nomer = intent.getStringExtra("nomer");

        topfrom = findViewById(R.id.booking_details_Textfrompage);
        nomerBooking = findViewById(R.id.booking_details_bookingNo);
        statusBooking = findViewById(R.id.booking_details_status);
        tabLayout = findViewById(R.id.bookingdetail_tablayout);
        viewPager = findViewById(R.id.bookingdetail_viewpager);


        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
        viewpagerDefault.Addfragment(new BookingDetails_Information(),"Information");
        viewpagerDefault.Addfragment(new BookingDetails_Service(),"Service");
        viewpagerDefault.Addfragment(new BookingDetails_Commodity(),"Commodity");
        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);

        topfrom.setText(from);
        nomerBooking.setText(nomer);
        statusBooking.setText(status);


        //Kondisi untuk mensetting color text pada status yang berbeda
        KondisiStatus(status, statusBooking,this);
    }

    private static void KondisiStatus (String statused, TextView textView, Activity activity){
        if (statused.equals("approved")){
            textView.setTextColor(activity.getResources().getColor(R.color.colorGreen));
        } else if (statused.equals("cancelled")){
            textView.setTextColor(activity.getResources().getColor(R.color.colorRed));
        } else if (statused.equals("booking")){
            textView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        } else {
            textView.setTextColor(activity.getResources().getColor(R.color.colorVerified));
        }
    }
}
