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
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.Model_DetailsCommodity;
import com.kbs.pocis.model.Model_DetailsService;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BookingDetails_Service extends Fragment {

    Adapter_DetailsService detailsService;
    RecyclerView recyclerView;
    ConstraintLayout lay_ada;
    RelativeLayout lay_kosong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_service, container, false);

        recyclerView = view.findViewById(R.id.recycle_service);
        lay_ada = view.findViewById(R.id.detail_ser_ada);
        lay_kosong = view.findViewById(R.id.detail_ser_kosong);
        List<Model_DetailsService> model_detailsServices = new ArrayList<>();
        Log.i("Tag", "BookingData.i = " + (BookingDetailData.i != null));
        //Log.i("Tag","DetailService = id = "+ BookingData.i.vessel.vessel_name);
        if (BookingDetailData.i != null) {
            if (BookingDetailData.i.template != null) {
                for (Model_DetailsService mod : BookingDetailData.i.template) {
                    model_detailsServices.add(mod);
                }
            }
        } else if (BookingData.i != null) {
            for (BookingData.BookTemplate mod : BookingData.i.template) {
                for (BookingData.BookTemplate.BookTempList anak : mod.listCheck) {
                    model_detailsServices.add(new Model_DetailsService(anak.code, anak.name, "10000"));
                }
            }
        }
        if (model_detailsServices.size()>0){
            detailsService = new Adapter_DetailsService(getContext(), model_detailsServices);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(detailsService);
        } else {
            //TODO LIST KOOSNG
            lay_ada.setVisibility(View.GONE);
            lay_kosong.setVisibility(View.VISIBLE);
            Log.i("Tag", "list kosong: " + "List anda kosong");
        }


        return view;
    }
}
