package com.kbs.pocis.navigasi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kbs.pocis.R;

public class ShowTemplate extends Fragment {

    Button btnPrev, btnNext;
    boolean g004, f003, j043, t008 = false;
    CheckBox checkFee, checkCard, checkKai, checkTain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_template, container, false);

        checkFee = view.findViewById(R.id.check_template_kendaraan);
        checkCard = view.findViewById(R.id.check_template_card);
        checkKai = view.findViewById(R.id.check_template_keretaApi);
        checkTain = view.findViewById(R.id.check_template_train);
//
//        g004 = view.findViewById(R.id.g004);
//        f003 = view.findViewById(R.id.f003);
//        j043 = view.findViewById(R.id.j043);
//        t008 = view.findViewById(R.id.t008);


        btnPrev = view.findViewById(R.id.cust_add_form_prevBtn);
        btnNext = view.findViewById(R.id.cust_add_form_nextBtn);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        CheckBoxFunction();
        GoNext();

        return view;
    }

    public void CheckBoxFunction (){
        checkFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    f003 = true;
                } else {
                    f003 = false;
                }
            }
        });

        checkCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    g004 = true;
                } else {
                    g004 = false;
                }
            }
        });

        checkKai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    j043 = true;
                } else {
                    j043 = false;
                }
            }
        });

        checkTain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   t008 = true;
                } else {
                    t008 = false;
                }
            }
        });
    }

    public void GoNext(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                Fragment fragment = new SelectTemplate();
                arg.putBoolean("t008", t008);
                arg.putBoolean("f003", f003);
                arg.putBoolean("g004", g004);
                arg.putBoolean("j043", j043);
                fragment.setArguments(arg);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}