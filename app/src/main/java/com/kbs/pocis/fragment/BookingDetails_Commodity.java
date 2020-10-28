package com.kbs.pocis.fragment;

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
import com.kbs.pocis.adapter.Adapter_DetailCommodity;
import com.kbs.pocis.adapter.Adapter_DetailsService;
import com.kbs.pocis.model.Model_DetailsCommodity;

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
        model_detailsCommodities.add(new Model_DetailsCommodity("coal","non grain bulk", 1,37340000));
        model_detailsCommodities.add(new Model_DetailsCommodity("iron ore","non grain bulk", 2,175000000));

        adapter = new Adapter_DetailCommodity(getContext(), model_detailsCommodities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }
}
