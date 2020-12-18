package com.kbs.pocis.monitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Monitoring;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.model.Model_Monitoring;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;

public class Unloading_List extends Fragment {

    int status;
    View view;
    public Unloading_List(int stat){
        status = stat;
    }
    RecyclerView recyclerView;
    TextView title;
    ArrayList<Model_Monitoring> model_monitorings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unloading_list,container,false);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.listing_monitoring);

        checkStatus();
        return view;
    }

    public void checkStatus(){
        switch (status){
            case 0:
                listBerthing();
                break;
            case 1:
                listPlanned();
                break;
            case 2:
                listDeparture();
                break;
        }
    }

    void listBerthing(){
        title.setText("Showing all berthing list. Tap to see details.");
        model_monitorings = new ArrayList<>();
        model_monitorings.add(new Model_Monitoring("NOT PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","CS. PACIFIC GUARDIAN","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("NOT PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","CS. PACIFIC GUARDIAN","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("NOT PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","CS. PACIFIC GUARDIAN","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("NOT PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","CS. PACIFIC GUARDIAN","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));

        Adapter_Monitoring adapter_project_list = new Adapter_Monitoring(getContext(), model_monitorings,0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
    }

    void listPlanned(){
        title.setText("Showing all planned list. Tap to see details.");
        model_monitorings = new ArrayList<>();
        model_monitorings.add(new Model_Monitoring("PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));

        Adapter_Monitoring adapter_project_list = new Adapter_Monitoring(getContext(), model_monitorings,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
    }

    void listDeparture(){
        title.setText("Showing all departure list. Tap to see details.");
        model_monitorings = new ArrayList<>();
        model_monitorings.add(new Model_Monitoring("PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("NOT PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("NOT PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("PLANNED","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","MV. UNION EXPLORER","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));

        Adapter_Monitoring adapter_project_list = new Adapter_Monitoring(getContext(), model_monitorings,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
    }

}
