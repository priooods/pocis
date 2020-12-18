package com.kbs.pocis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.activity.Invoice;
import com.kbs.pocis.activity.Monitoring;
import com.kbs.pocis.activity.MyProject_Dasar;
import com.kbs.pocis.activity.OnlineBook;
import com.kbs.pocis.item.Popup_Profile;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.welcome.Contact_Us;

public class HomeMenu extends Fragment {

    ImageView menu_online_booking, menu_create_booking, iconprofile, menu_myproject, menu_invoice,
        menu_monitoring;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        iconprofile = view.findViewById(R.id.iconprofile);

        menu_online_booking = view.findViewById(R.id.menu_online_booking);
        menu_online_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OnlineBook.class));
            }
        });

        menu_monitoring = view.findViewById(R.id.menu_operation_monitor);
        menu_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Monitoring.class));
            }
        });

        menu_create_booking = view.findViewById(R.id.menu_create_booking);
        menu_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingData.i = null;
                Intent intent = new Intent(getContext(), CreateBooking.class);
                startActivity(intent);
            }
        });

        menu_myproject = view.findViewById(R.id.menu_my_project);
        menu_myproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyProject_Dasar.class));
            }
        });

        menu_invoice = view.findViewById(R.id.menu_invoice_perfom);
        menu_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Invoice.class));
            }
        });

        iconprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new Popup_Profile();
                fragment.show(getChildFragmentManager(),"PopUp");
            }
        });

        floatingActionButton = view.findViewById(R.id.floating_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new Contact_Us();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }



}