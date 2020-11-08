package com.kbs.pocis.createboking;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        backhome = view.findViewById(R.id.finish_backHome);
        bookagain = view.findViewById(R.id.finish_bookegain);

        ButtonFunction();
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        return view;
    }

    public void ButtonFunction(){
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomePage.class);
                startActivity(intent);
            }
        });


        bookagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateBooking.class);
                startActivity(intent);
            }
        });
    }

    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Intent intent = new Intent(getContext(), HomePage.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

}