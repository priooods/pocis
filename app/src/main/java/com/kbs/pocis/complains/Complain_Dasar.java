package com.kbs.pocis.complains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.R;

public class Complain_Dasar extends Fragment {

    ImageView icon_back;
    View view;
    ImageView new_complain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.complain_dasar,container,false);

        new_complain = view.findViewById(R.id.new_complain);
        icon_back = view.findViewById(R.id.btn_back_complain);
        icon_back.setOnClickListener(v -> requireActivity().onBackPressed());

        new_complain.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new New_Complain();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        FragmentList(new Complain());

        return view;
    }

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.framecomplain, fragment,"framentcomplain").commit();
    }
}
