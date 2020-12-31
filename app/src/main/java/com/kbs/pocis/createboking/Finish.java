package com.kbs.pocis.createboking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.HomePage;

public class Finish extends Fragment {

    Button backhome;
    TextView bookagain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        backhome = view.findViewById(R.id.finish_backHome);
        bookagain = view.findViewById(R.id.finish_bookegain);

        ButtonFunction();


        return view;
    }

    public void ButtonFunction(){
        backhome.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HomePage.class);
            startActivity(intent);
            requireActivity().finish();
        });

        bookagain.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateBooking.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }


    //Di hidden back default biar pilih dua buttin diatas aja dan ga akan bisa double booking untuk users
    //jangan diganti
    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            // handle back button's click listener
            return event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK;
        });
    }

}