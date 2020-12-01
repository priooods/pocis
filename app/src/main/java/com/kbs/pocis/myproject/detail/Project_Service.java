package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.detailboking.Adapter_DetailsService;
import com.kbs.pocis.adapter.myprojects.Adapter_Project_Service;
import com.kbs.pocis.model.Model_DetailsService;
import com.kbs.pocis.model.myprojects.Model_Project_Service;

import java.util.ArrayList;
import java.util.List;

public class Project_Service extends Fragment {

    Adapter_Project_Service detailService;
    ConstraintLayout layout_total_harga;
    RecyclerView recyclerView;
    int codetemplate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myproject_service, container,false);

        codetemplate = getActivity().getIntent().getIntExtra("get", 0);
        layout_total_harga = view.findViewById(R.id.bot);

        recyclerView = view.findViewById(R.id.recycle_service_project);
        List<Model_Project_Service> model_project_services = new ArrayList<>();
        model_project_services.add(new Model_Project_Service("B020 - Jasa Ritasi (Port Facility Service)","USD 0.001","39,977.600 T","-","IDR 567,682","IDR 567,682"));
        model_project_services.add(new Model_Project_Service("B020 - Jasa Ritasi (Port Facility Service)","USD 0.001","39,977.600 T","-","IDR 567,682","IDR 567,682"));
        model_project_services.add(new Model_Project_Service("B020 - Jasa Ritasi (Port Facility Service)","USD 0.001","39,977.600 T","-","IDR 567,682","IDR 567,682"));
        detailService = new Adapter_Project_Service(getContext(), model_project_services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailService);


        /* Kalau mau setting value di text harus di function ini
        * biar rapih dan ga error ketika update value */
        SettingChangeByTemp(codetemplate);

        return view;
    }

    void SettingChangeByTemp(int code){
        if (code == 1){
            layout_total_harga.setVisibility(View.GONE);
        }
    }
}
