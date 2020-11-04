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
    boolean kai, card, train, fee;
    CheckBox checkFee, checkCard, checkKai, checkTain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_template, container, false);

        checkFee = view.findViewById(R.id.check_template_kendaraan);
        checkCard = view.findViewById(R.id.check_template_card);
        checkKai = view.findViewById(R.id.check_template_keretaApi);
        checkTain = view.findViewById(R.id.check_template_train);


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
                    fee = true;
                } else {
                    fee = false;
                }
            }
        });

        checkCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    card = true;
                } else {
                    card = false;
                }
            }
        });

        checkKai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    kai = true;
                } else {
                    kai = false;
                }
            }
        });

        checkTain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    train = true;
                } else {
                    train = false;
                }
            }
        });
    }

    public void GoNext(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SelectTemplate();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}