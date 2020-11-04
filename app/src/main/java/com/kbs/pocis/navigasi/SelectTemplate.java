package com.kbs.pocis.navigasi;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

public class SelectTemplate extends Fragment {

    boolean g004, f003, j043, t008 = true;
    CardView card_f003, card_g004;
    CheckBox checkAll, check_f1, check_f2, check_f3, check_f4,
            check_f5, check_g1;
    Button prev, next;
    TextView notif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);

        g004 = getArguments().getBoolean("g004");
        f003 = getArguments().getBoolean("f003");

        //View card Template
        card_f003 = view.findViewById(R.id.card_f003);
        card_g004 = view.findViewById(R.id.card_g004);

        //CheckButton
        checkAll = view.findViewById(R.id.select_template_checkAll);
        check_f1 = view.findViewById(R.id.check_f003_1);
        check_f2 = view.findViewById(R.id.check_f003_2);
        check_f3 = view.findViewById(R.id.check_f003_3);
        check_f4 = view.findViewById(R.id.check_f003_4);
        check_f5 = view.findViewById(R.id.check_f003_5);
        check_g1 = view.findViewById(R.id.check_g004_1);

        //Button go & back
        prev = view.findViewById(R.id.select_template_prevBtn);
        next = view.findViewById(R.id.select_template_nextBtn);

        SettTemplateVisibity();
        SelectedBox();
        FunctionButton();

        return view;
    }

    public void SettTemplateVisibity(){
        if ( f003 ){
            card_f003.setVisibility(View.VISIBLE);
        }
        if ( g004 ){
            card_g004.setVisibility(View.VISIBLE);
        }
    }

    public void SelectedBox(){
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    check_f1.setChecked(true);
                    check_f2.setChecked(true);
                    check_f3.setChecked(true);
                    check_f4.setChecked(true);
                    check_f5.setChecked(true);
                    check_g1.setChecked(true);
                } else {
                    check_f1.setChecked(false);
                    check_f2.setChecked(false);
                    check_f3.setChecked(false);
                    check_f4.setChecked(false);
                    check_f5.setChecked(false);
                    check_g1.setChecked(false);
                }
            }
        });
    }

    public void FunctionButton(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UploadDocument();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}