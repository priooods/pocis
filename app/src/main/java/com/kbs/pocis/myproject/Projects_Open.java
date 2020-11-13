package com.kbs.pocis.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.myprojects.Adapter_Project_Open;
import com.kbs.pocis.adapter.onlineboking.Adapter_CancelBooking;
import com.kbs.pocis.model.myprojects.Model_Project_Open;

import java.util.ArrayList;

public class Projects_Open extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Model_Project_Open> model_project_opens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects_open, container, false);

        recyclerView = view.findViewById(R.id.list_project_open);
        ListingProject();
        return view;
    }

    void ListingProject(){
        model_project_opens = new ArrayList<>();
        model_project_opens.add(new Model_Project_Open("5 September 2020 08:00","PPJ-2020/02669","P0019-063","SEAMS001","39,977.600","16 September 2020 13:02","PT Krakatau Steel","MV. Sea Master"));
        model_project_opens.add(new Model_Project_Open("5 September 2020 08:00","PPJ-2020/02669","P0019-063","SEAMS001","39,977.600","16 September 2020 13:02","PT Krakatau Steel","MV. Sea Master"));
        model_project_opens.add(new Model_Project_Open("5 September 2020 08:00","PPJ-2020/02669","P0019-063","SEAMS001","39,977.600","16 September 2020 13:02","PT Krakatau Steel","MV. Sea Master"));

        Adapter_Project_Open adapter_project_open = new Adapter_Project_Open(getContext(), model_project_opens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_open);

    }
}