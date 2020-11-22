package com.kbs.pocis.onlineboking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.filter.FIlter_Service;
import com.kbs.pocis.filter.Filter_OnlineBooking;

public class OnlineBooking extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    private static int REQUEST_CODE = 123;
    ImageView icon_back, icon_search;
    ViewpagerDefault adapter;
    DialogFragment fragment;

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

        //ViewpagerDefault adapter = new ViewpagerDefault(getChildFragmentManager());
        //Taruh fragment ke view pager lalu pasang ke tablayout
//        adapter.Addfragment(new AllBookings(), "All Bookings");
//        adapter.Addfragment(new CancelBooking(), "Cancel Booking");
        adapter = new ViewpagerDefault(getChildFragmentManager());
        adapter.Addfragment(new AllBookings(),"All Bookings");
        adapter.Addfragment(new CancelBooking(),"Cancel Booking");
        Log.i("TAG", "onCreateView: "+ adapter);

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
//                fragment = new Filter_OnlineBooking();
//                fragment.setTargetFragment(getTargetFragment(),REQUEST_CODE);
//                fragment.show(getChildFragmentManager(), "filter_online");
            }
        });

        return view;
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_CODE){
//            String datas = data.getStringExtra("inputvesel");
//            Log.e("TAG", "onActivityResult: " +  datas);
//        }
//    }
}