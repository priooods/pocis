package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;

public class Project_Information extends Fragment {

    String consigne, vesel, tonag;
    TextView vessel_name, consigne_name, tonage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myproject_information, container, false);

        consigne = getActivity().getIntent().getStringExtra("name_consigne");
        vesel = getActivity().getIntent().getStringExtra("name_vesel");
        tonag = getActivity().getIntent().getStringExtra("tonage");

        consigne_name = view.findViewById(R.id.detail_project_info_consigne);
        vessel_name = view.findViewById(R.id.detail_project_info_vesselName);
        tonage = view.findViewById(R.id.detail_project_info_tonage);

        tonage.setText(tonag);
        consigne_name.setText(consigne);
        vessel_name.setText(vesel);


        return view;
    }
}
