package com.kbs.pocis.detailboking;

import android.os.Bundle;
import android.util.Log;
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
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.Model_DetailsCommodity;
import com.kbs.pocis.model.Model_DetailsService;
import com.kbs.pocis.service.BookingData;

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
        Log.i("Tag","BookingData.i = "+ (BookingData.i!=null));
        Log.i("Tag","DetailService = id = "+ BookingData.i.vessel.vessel_name);
        for(BookingData.BookTemplate mod : BookingData.i.template) {
            for (BookingData.BookTemplate.BookTempList anak : mod.listCheck) {
                model_detailsServices.add(new Model_DetailsService(anak.code + " - " + anak.name, "10000"));
            }
        }
        detailsService = new Adapter_DetailsService(getContext(), model_detailsServices);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailsService);


        return view;
    }
}
