package com.kbs.pocis.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;

public class OnlineBooking extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView icon_back, icon_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_booking, container, false);

        icon_back = view.findViewById(R.id.btn_back_online_booking);
        icon_search = view.findViewById(R.id.btn_search_online_booking);
        tabLayout = view.findViewById(R.id.tabLayout_onlineBook);
        viewPager = view.findViewById(R.id.viewpager_onlineBooking);


        //Ini adalah adapter Viewpager Default ( Bisa di scroll Horizontal )
        ViewpagerDefault adapter = new ViewpagerDefault(getActivity().getSupportFragmentManager());
        //Taruh fragment ke view pager lalu pasang ke tablayout
        adapter.Addfragment(new AllBookings(), "All Bookings");
        adapter.Addfragment(new CancelBooking(), "Cancel Booking");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}