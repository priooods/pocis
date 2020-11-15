package com.kbs.pocis.onlineboking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;

public class OnlineBooking extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView icon_back, icon_search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_booking, container, false);

        icon_back = view.findViewById(R.id.btn_back_online_booking);
        icon_search = view.findViewById(R.id.btn_search_online_booking);
        tabLayout = view.findViewById(R.id.tabLayout_onlineBook);
        viewPager = view.findViewById(R.id.viewpager_onlineBooking);

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
                getActivity().finish();
            }
        });

        //Icon Search Pada Online Booking CLick
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(getContext());
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