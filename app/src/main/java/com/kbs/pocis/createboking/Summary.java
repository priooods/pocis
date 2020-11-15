package com.kbs.pocis.createboking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        next = view.findViewById(R.id.summary_nextBtn);
        prev = view.findViewById(R.id.summary_prevBtn);

        ButtonFunction();
        return view;
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


        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_close.setText("No");
        btn_close.setAllCaps(false);
        Button btn_cancelBoking = view.findViewById(R.id.btn_cancelbookinggo);
        btn_cancelBoking.setText("Yes");
        btn_cancelBoking.setBackground(getResources().getDrawable(R.drawable.btn_green));
        btn_cancelBoking.setAllCaps(false);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_cancelBoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
                Intent intent = new Intent(context, Finish.class).putExtra("user", user);
                startActivity(intent);
                getActivity().finish();
            }
        });
        dialogFragment.show();
    }
}