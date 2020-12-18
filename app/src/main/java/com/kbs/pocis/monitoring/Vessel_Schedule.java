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
import com.kbs.pocis.model.Model_Monitoring;

import java.util.ArrayList;

public class Vessel_Schedule extends Fragment {

    RecyclerView recyclerView;
    TextView title;
    View view;
    ArrayList<Model_Monitoring> model_monitorings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vessel_schedule,container,false);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.listing_vessel);

        title.setText("Showing all departure list. Tap to see details.");
        model_monitorings = new ArrayList<>();
        model_monitorings.add(new Model_Monitoring("P0010-2020-00072","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","Tiger Shandong MV.","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("P0010-2020-00072","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","Tiger Shandong MV.","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));
        model_monitorings.add(new Model_Monitoring("P0010-2020-00072","5840D","19 March 2020 00:00","20 March 2020 00:00","19 March 2020 00:00",
                "20 March 2020 00:00","DERMAGA 2","Tiger Shandong MV.","YCDF201002","Inter Island","18 March 2020 22:00","P0010-2020-00072",1));

        Adapter_Monitoring adapter_project_list = new Adapter_Monitoring(getContext(), model_monitorings,3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);

        return view;
    }
}
