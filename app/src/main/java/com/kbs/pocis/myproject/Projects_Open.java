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
import com.kbs.pocis.adapter.myprojects.Adapter_Project_List;
import com.kbs.pocis.filter.Filter_MyProject;
import com.kbs.pocis.model.myprojects.Model_Project_Open;

import java.util.ArrayList;

public class Projects_Open extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Model_Project_Open> model_project_opens;
    ImageView search_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects_open, container, false);

        search_icon = view.findViewById(R.id.btn_search_project_list);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFilter(getContext());
            }
        });
        recyclerView = view.findViewById(R.id.list_project_open);
        ListingProject();
        return view;
    }

    void ListingProject(){
        model_project_opens = new ArrayList<>();
        model_project_opens.add(new Model_Project_Open("P0019-063","PREPARED","PPJ/BEN03-2020-00121/01","SEAMS001","PT Krakatau Steel","16 September 2020 13:02","PPJ-2020/08880","5 September 2020 08:00","39,977.600","0216055983811","8923716055987308","MV. Sea Master"));
        model_project_opens.add(new Model_Project_Open("P0019-069","OPEN","PPJ/BEN03-2021-00121/09","SEAMS001","PT Krakatau Steel","20 September 2020 13:02","-","5 Januari 2020 08:00","39,977.600","0216055983811","8923716055987308","MV. Sea Master"));
        model_project_opens.add(new Model_Project_Open("P0019-063","PREPARED","PPJ/BEN03-2020-00121/01","SEAMS001","PT Krakatau Steel","16 September 2020 13:02","PPJ-2020/08880","5 September 2020 08:00","39,977.600","0216055983811","8923716055987308","MV. Sea Master"));

        Adapter_Project_List adapter_project_list = new Adapter_Project_List(getContext(), model_project_opens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);

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