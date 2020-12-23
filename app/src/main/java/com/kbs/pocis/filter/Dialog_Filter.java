package com.kbs.pocis.filter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.onlineboking.AllBookings;
import com.kbs.pocis.service.UserData;

public class Dialog_Filter extends DialogFragment {

    TextInputEditText input_vesel, input_nomerBook, nomer_project;
    TextView title;
    Button button_back, button_next;
    public LinearLayout line_project;
    FilterFragment filterFragment;
    boolean is_three;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    public Dialog_Filter(boolean three, FilterFragment online_booking){
        this.filterFragment = online_booking;
        is_three = three;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = getLayoutInflater().inflate(R.layout.dialog_filters, container, false);

        line_project = view.findViewById(R.id.line_projectno);
        title = view.findViewById(R.id.title_filter);
        input_nomerBook = view.findViewById(R.id.filter_nomerbooking);
        nomer_project = view.findViewById(R.id.filter_nomerproject);
        input_vesel = view.findViewById(R.id.filter_veselname);
        button_back = view.findViewById(R.id.btn_cancelbooking);
        button_next = view.findViewById(R.id.btn_filterbooking);

        if (is_three){
            line_project.setVisibility(View.VISIBLE);
            title.setText("Filter Projects");
        }

        if (UserData.isExists()) {
            if (UserData.i.filter!=null) {
                input_nomerBook.setText(UserData.i.filter.nomor);
                input_vesel.setText(UserData.i.filter.vessel);
            }
        }
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserData.isExists()){
                    UserData.i.updateFilter(nomer_project.getText().toString(),input_nomerBook.getText().toString().toUpperCase(),input_vesel.getText().toString().toUpperCase());
                }
                if (filterFragment != null) {
                    filterFragment.filter = UserData.i.filter;
                    filterFragment.pmanager = null;
                    filterFragment.GenerateLists();
                }
                dismiss();
            }
        });

        return view;

    }
}
