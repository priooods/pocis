package com.kbs.pocis.onlineboking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.onlineboking.Adapter_TarifApproved;
import com.kbs.pocis.model.Model_Bookings;

import java.util.ArrayList;
import java.util.List;

public class TarifApprove extends Fragment {

    RecyclerView recyclerView;
    Adapter_TarifApproved adapter;
    ImageView btn_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tarif_approve, container,false);

        btn_back = view.findViewById(R.id.btn_back_tarif_approve);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        List<Model_Bookings> model_bookings = new ArrayList<>();
        recyclerView = view.findViewById(R.id.tarif_approve_recycleview);
        adapter = new Adapter_TarifApproved(getContext(), model_bookings);

        model_bookings.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00067", "verified",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
        model_bookings.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00068", "verified",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
        model_bookings.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00069", "verified",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));

        adapter = new Adapter_TarifApproved(getContext(), model_bookings);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }



}
