package com.kbs.pocis.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kbs.pocis.R;

public class HomePage extends Fragment {

    ImageView menu_create_boking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        menu_create_boking = view.findViewById(R.id.menu_create_booking);
        menu_create_boking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homepage = new OnlineBooking();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, homepage).addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }
}