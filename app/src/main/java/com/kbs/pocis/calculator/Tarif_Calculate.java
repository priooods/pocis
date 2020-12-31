package com.kbs.pocis.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.R;

public class Tarif_Calculate extends Fragment {

    LinearLayout menu_good, menu_ship;
    ImageView backk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tarif_calculate,container,false);

        menu_good = view.findViewById(R.id.good_menu);
        menu_ship = view.findViewById(R.id.ship_menu);
        backk = view.findViewById(R.id.btn_back_tarif);
        backk.setOnClickListener(v -> requireActivity().onBackPressed());



        menu_good.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Calculates(0);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        menu_ship.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Calculates(1);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });


        return view;
    }
}
