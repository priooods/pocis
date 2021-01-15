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
import com.kbs.pocis.adapter.detailboking.Adapter_DetailCommodity;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;


public class BookingDetails_Commodity extends Fragment {

    RecyclerView recyclerView;
    Adapter_DetailCommodity adapter;
    ConstraintLayout lay_ada;
    RelativeLayout lay_kosong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_commodity,container,false);

        recyclerView = view.findViewById(R.id.recycle_commodity);
        lay_ada = view.findViewById(R.id.detail_com_ada);
        lay_kosong = view.findViewById(R.id.detail_com_kosong);

        if (Model_Project.Commodity.size()>0) {
            List<Model_Project> model_detailsCommodities = new ArrayList<>(Model_Project.Commodity);
            adapter = new Adapter_DetailCommodity(getContext(), model_detailsCommodities);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            lay_ada.setVisibility(View.GONE);
            lay_kosong.setVisibility(View.VISIBLE);
            Log.i("Tag", "list kosong: " + "List anda kosong");
        }


        return view;
    }
}
