package com.kbs.pocis.createboking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.service.UserData;

public class Summary extends Fragment {

    Button next, prev;
    TextView customer_type, related, contract, veselname, discharge,
            port, arival, departure, loading;

    String cus, rel, con, ves, des, por, ari, dep, load;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        Bundle bundle = new Bundle();
        cus = getArguments().getString("customertype");
        rel = getArguments().getString("contract");
        con = getArguments().getString("relatedvessel");
        ves = getArguments().getString("vesel");
        des = getArguments().getString("discharge");
        por = getArguments().getString("port");
        ari = getArguments().getString("estimate");
        dep = getArguments().getString("departure");


        next = view.findViewById(R.id.summary_nextBtn);
        prev = view.findViewById(R.id.summary_prevBtn);

        customer_type = view.findViewById(R.id.veselinfo_customer_type);
        related = view.findViewById(R.id.veselinfo_related_vesel);
        contract = view.findViewById(R.id.veselinfo_contract);
        veselname = view.findViewById(R.id.veselinfo_veselname);
        discharge = view.findViewById(R.id.veselinfo_loading_discharge);
        port = view.findViewById(R.id.veselinfo_port);
        arival = view.findViewById(R.id.veselinfo_arrival);
        departure = view.findViewById(R.id.veselinfo_departure);
        loading = view.findViewById(R.id.veselinfo_loading);

        setStringToText();

        ButtonFunction();
        return view;
    }

    void setStringToText(){
        customer_type.setText(cus);
        related.setText(rel);
        contract.setText(con);
    }

    public void ButtonFunction(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogCancell(getContext());
            }
        });
    }

    public void ShowDialogCancell (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_cancelled, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputLayout input_alasan = view.findViewById(R.id.onp);
        input_alasan.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.tl);
        ImageView bg = view.findViewById(R.id.bc);
        bg.setImageResource(R.drawable.crb);
        title.setText("Are you sure want to create booking based on your provided information?");
        title.setGravity(Gravity.CENTER);

        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_close.setText("No");
        btn_close.setAllCaps(false);
        Button btn_go = view.findViewById(R.id.btn_cancelbookinggo);
        btn_go.setText("Yes");
        btn_go.setBackground(getResources().getDrawable(R.drawable.btn_green));
        btn_go.setAllCaps(false);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.dismiss();
                Fragment fragment = new Finish();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment);
                fragmentTransaction.commit();
            }
        });
        dialogFragment.show();
    }
}