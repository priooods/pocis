package com.kbs.pocis.onlineboking;

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
import com.kbs.pocis.filter.Dialog_Filter;

public class OnlineBooking extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
//    private static int REQUEST_CODE = 123;
    ImageView icon_back, icon_search;
    ViewpagerDefault adapter;
    DialogFragment fragment;
    AllBookings[] allbooking;
    AllBookings select_booking;

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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i("ViewPager", "Scrolled | position = "+position+" , positionOffset = "+positionOffset+", pos.of.pix = "+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("ViewPager", "Select | position = "+position);
                select_booking = allbooking[position];
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                    //Log.i("ViewPager", "Changed | state = "+state);
            }
        });
        allbooking = new AllBookings[2];
        adapter = new ViewpagerDefault(getChildFragmentManager());
        adapter.Addfragment(select_booking = allbooking[0] = new AllBookings(0),"All Bookings");
        adapter.Addfragment(allbooking[1] = new AllBookings(1),"Cancel Bookings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        //Icon Arrow Back Click
        icon_back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
            requireActivity().finish();
        });

        //Icon Search Pada Online Booking CLick
        icon_search.setOnClickListener(v -> {
            fragment = new Dialog_Filter(false,select_booking);
//                fragment.setTargetFragment(getTargetFragment(),REQUEST_CODE);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        return view;
    }
}