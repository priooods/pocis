package com.kbs.pocis.onlineboking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.service.UserData;

public class OnlineBooking extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView id_booking, id_tarif;
    ImageView icon_back, icon_search, create_booking, icon_booking, icon_tarif;
    LinearLayout tarif_approve, online_booking_onlinebooking;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_booking, container, false);

        icon_back = view.findViewById(R.id.btn_back_online_booking);
        icon_search = view.findViewById(R.id.btn_search_online_booking);
        tabLayout = view.findViewById(R.id.tabLayout_onlineBook);
        viewPager = view.findViewById(R.id.viewpager_onlineBooking);
        create_booking = view.findViewById(R.id.online_booking_create_booking);
        tarif_approve = view.findViewById(R.id.online_booking_tarif_aprove);
        online_booking_onlinebooking = view.findViewById(R.id.online_booking_onlinebooking);

        id_booking = view.findViewById(R.id.id_icon_onlineboking);
        id_tarif = view.findViewById(R.id.id_icon_tarifapprove);
        icon_booking = view.findViewById(R.id.icon_onlineboking);
        icon_tarif = view.findViewById(R.id.icon_tarifapprove);

        //Ini adalah adapter Viewpager Default ( Bisa di scroll Horizontal )
        ViewpagerDefault adapter = new ViewpagerDefault(getChildFragmentManager());
        //Taruh fragment ke view pager lalu pasang ke tablayout
        adapter.Addfragment(new AllBookings(), "All Bookings");
        adapter.Addfragment(new CancelBooking(), "Cancel Booking");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Icon Arrow Back Click
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Icon Search Pada Online Booking CLick
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(getContext());
            }
        });

        create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateBooking.class);
                startActivity(intent);
            }
        });

        tarif_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_booking.setTextColor(getResources().getColor(R.color.colorGrey));
                icon_booking.setImageResource(R.drawable.booking_icon_grey);
                Fragment fragment = new TarifApprove();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        online_booking_onlinebooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_tarif.setTextColor(getResources().getColor(R.color.colorGrey));
                icon_tarif.setImageResource(R.drawable.dollar_icon_grey);
                Fragment fragment = new AllBookings();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    //Ini adalah function untuk Menampilkan Dialog Searching yah
    public void ShowDialog (Context context){
        //setting dialog
        View view  = getLayoutInflater().inflate(R.layout.dialog_filters, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);

        //Setting ukuran dialog
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //widget pada dialog
        TextInputEditText input_nomerBooking = view.findViewById(R.id.filter_nomerbooking);
        TextInputEditText input_veselName = view.findViewById(R.id.filter_veselname);
        Button btn_cancel = view.findViewById(R.id.btn_cancelbooking);
        Button btn_filtering = view.findViewById(R.id.btn_filterbooking);

        //fungsi pada widget
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