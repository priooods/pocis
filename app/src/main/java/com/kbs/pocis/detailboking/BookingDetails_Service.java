package com.kbs.pocis.detailboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.detailboking.Adapter_DetailsService;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;

public class BookingDetails_Service extends Fragment {

    Adapter_DetailsService detailsService;
    RecyclerView recyclerView;
    ConstraintLayout lay_ada;
    RelativeLayout lay_kosong;
    List<Model_Project> model_detailsServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_service, container, false);

        recyclerView = view.findViewById(R.id.recycle_service);
        lay_ada = view.findViewById(R.id.detail_ser_ada);
        lay_kosong = view.findViewById(R.id.detail_ser_kosong);
        if (Model_Project.Service.size() > 0) {
            model_detailsServices = new ArrayList<>(Model_Project.Service);
            detailsService = new Adapter_DetailsService(getContext(), model_detailsServices);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(detailsService);
        } else {
            lay_ada.setVisibility(View.GONE);
            lay_kosong.setVisibility(View.VISIBLE);
            Log.i("Tag", "list kosong: " + "List anda kosong");
        }


        return view;
    }
}
