package com.kbs.pocis.createboking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.kbs.pocis.service.UserData;

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
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
                Intent intent = new Intent(getActivity(), HomePage.class).putExtra("user", user);
                startActivity(intent);
                getActivity().finish();
            }
        });


//        bookagain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserData user = (UserData) getIntent().getParcelableExtra("user");
//                Intent intent = new Intent(Finish.this, CreateBooking.class).putExtra("user", user);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

}