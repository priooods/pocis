package com.kbs.pocis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.item.Popup_Profile;
import com.kbs.pocis.onlineboking.OnlineBooking;
import com.kbs.pocis.service.SessionManager;
import com.kbs.pocis.welcome.Login;

public class HomeMenu extends Fragment {

    ImageView menu_online_booking, menu_create_booking, iconprofile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);

        iconprofile = view.findViewById(R.id.iconprofile);

        menu_online_booking = view.findViewById(R.id.menu_online_booking);
        menu_online_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OnlineBooking();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        menu_create_booking = view.findViewById(R.id.menu_create_booking);
        menu_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateBooking.class);
                startActivity(intent);
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
}