package com.kbs.pocis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.MyProject_Dasar;
import com.kbs.pocis.activity.OnlineBook;
import com.kbs.pocis.item.Popup_Profile;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;

public class HomeMenu extends Fragment {

    ImageView menu_online_booking, menu_create_booking, iconprofile, menu_myproject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        iconprofile = view.findViewById(R.id.iconprofile);

        menu_online_booking = view.findViewById(R.id.menu_online_booking);
        menu_online_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
                startActivity(new Intent(getActivity(), OnlineBook.class));
            }
        });

        menu_create_booking = view.findViewById(R.id.menu_create_booking);
        menu_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
                BookingData.i = null;
                Intent intent = new Intent(getContext(), CreateBooking.class);
                startActivity(intent);
            }
        });

        menu_myproject = view.findViewById(R.id.menu_my_project);
        menu_myproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
                startActivity(new Intent(getActivity(), MyProject_Dasar.class));
            }
        });

        iconprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new Popup_Profile();
                fragment.show(getChildFragmentManager(),"PopUp");
            }
        });

        return view;
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackTime + 2000 > System.currentTimeMillis()){
//            backToast.cancel();
//            super.onBackPressed();
//        }
//        else{
//            backToast = Toast.makeText(getBaseContext(), "Press once again to exit",
//                    Toast.LENGTH_SHORT);
//            backToast.show();
//            doubleBackTime = System.currentTimeMillis();
//        }
//    }
}