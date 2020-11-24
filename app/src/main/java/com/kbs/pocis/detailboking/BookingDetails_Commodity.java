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
import com.kbs.pocis.adapter.detailboking.Adapter_DetailCommodity;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.Model_DetailsCommodity;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;

import java.util.ArrayList;
import java.util.List;

public class BookingDetails_Commodity extends Fragment {

    RecyclerView recyclerView;
    Adapter_DetailCommodity adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_commodity,container,false);

        recyclerView = view.findViewById(R.id.recycle_commodity);
        List<Model_DetailsCommodity> model_detailsCommodities = new ArrayList<>();

        //Log.i("Tag","DetailCommodity = id = "+ BookingData.i.vessel.vessel_name);
        if (BookingDetailData.i!=null) {
            if (BookingDetailData.i.commodity!=null) {
                for (Model_DetailsCommodity mod : BookingDetailData.i.commodity) {
                    model_detailsCommodities.add(mod);
                }
            }
        }else {
            for (Model_Commodity mod : BookingData.i.commodity) {
                model_detailsCommodities.add(new Model_DetailsCommodity(mod.commodity, mod.consigne, mod.packages, mod.weight));
            }
        }
        //model_detailsCommodities.add(new Model_DetailsCommodity("iron ore","non grain bulk", 2,175000000));

        adapter = new Adapter_DetailCommodity(getContext(), model_detailsCommodities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }
}
