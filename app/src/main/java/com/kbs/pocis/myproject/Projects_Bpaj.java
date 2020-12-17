package com.kbs.pocis.myproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;

public class Projects_Bpaj extends Fragment {

    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    ImageView search_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects_bpaj, container, false);

        search_icon = view.findViewById(R.id.btn_search_bpaj);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFilter(getContext());
            }
        });
        recyclerView = view.findViewById(R.id.list_project_bpaj);
        model_project_s = new ArrayList<>();
        model_project_s.add(new Model_Project("P0019-2020-00021","DRAFT","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00022","DRAFT","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",2,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00023","DRAFT","PPJ/P0019-288/03","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",3,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
        return view;
    }

    public void OpenFilter(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_filters_myproject, null);
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_nomerBook = view.findViewById(R.id.filter_project_nomerbooking);
        TextInputEditText input_vesel = view.findViewById(R.id.filter_project_veselname);
        TextInputEditText input_nomerProject = view.findViewById(R.id.filter_project_nomerproject);
        Button button_back = view.findViewById(R.id.btn_filter_cancel_project);
        Button button_next = view.findViewById(R.id.btn_filter_go_project);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}