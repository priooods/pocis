package com.kbs.pocis.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;

public class Filter_MyProject extends DialogFragment {

    TextInputEditText input_vesel, input_nomerBook, input_nomerProject;
    Button button_back, button_next;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filters_myproject, container,false);

        input_nomerBook = view.findViewById(R.id.filter_project_nomerbooking);
        input_vesel = view.findViewById(R.id.filter_project_veselname);
        input_nomerProject = view.findViewById(R.id.filter_project_nomerproject);
        button_back = view.findViewById(R.id.btn_filter_cancel_project);
        button_next = view.findViewById(R.id.btn_filter_go_project);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
