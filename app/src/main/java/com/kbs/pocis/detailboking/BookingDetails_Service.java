package com.kbs.pocis.detailboking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.detailboking.Adapter_DetailsService;
import com.kbs.pocis.model.Model_DetailsService;

import java.util.ArrayList;
import java.util.List;

public class BookingDetails_Service extends Fragment {

    Adapter_DetailsService detailsService;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_service,container,false);

        recyclerView = view.findViewById(R.id.recycle_service);
        List<Model_DetailsService> model_detailsServices = new ArrayList<>();
        model_detailsServices.add(new Model_DetailsService("A107 - PORT FACILITY SERVICE (SCRAP) (T2)","12.000"));
        model_detailsServices.add(new Model_DetailsService("A015 - PORT FACILITY SERVICE LOADING PLATE","15.000"));
        model_detailsServices.add(new Model_DetailsService("B075 - SHIP UNLOADER","21.000"));

        detailsService = new Adapter_DetailsService(getContext(), model_detailsServices);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailsService);


        return view;
    }
}
