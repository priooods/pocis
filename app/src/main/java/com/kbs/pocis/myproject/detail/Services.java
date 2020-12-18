package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.myprojects.Adapter_Project_Service;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.model.myprojects.Model_Project_Service;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Services extends Fragment {


    Adapter_Project_Service detailService;
    TextView item1,item2,item3,item4,item5, item_list1,item_list2, item_bpaj1,
            invoice1,invoice2,invoice3,invoice4,invoice5,invoice6, performa1,performa2;

    ConstraintLayout ln_list,ln_bpaj,ln_performa;
    View bottom;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;

    int service_type;
    public Services(int types){
        service_type = types;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_services, container,false);

        item_list1 = view.findViewById(R.id.item_list1);
        item_list2 = view.findViewById(R.id.item_list2);
        item_bpaj1 = view.findViewById(R.id.item_bpaj1);
        bottom = view.findViewById(R.id.bottom_shet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        bottomSheetBehavior.setHideable(false);

        //ini untuk hide layout
        ln_list = view.findViewById(R.id.ln_list);
        ln_bpaj = view.findViewById(R.id.ln_bpaj);
        ln_performa = view.findViewById(R.id.ln_performa);

        invoice1 = view.findViewById(R.id.item_invoice1);
        invoice2 = view.findViewById(R.id.item_invoice2);
        invoice3 = view.findViewById(R.id.item_invoice3);
        invoice4 = view.findViewById(R.id.item_invoice4);
        invoice5 = view.findViewById(R.id.item_invoice5);
        invoice6 = view.findViewById(R.id.item_invoice6);

        performa1 = view.findViewById(R.id.item_perf1);
        performa2 = view.findViewById(R.id.item_perf2);


        //Ini untuk table atas
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item4 = view.findViewById(R.id.item4);
        item5 = view.findViewById(R.id.item5);
        recyclerView = view.findViewById(R.id.recycle_service_project);

        PrivateByType();


        List<Model_Project_Service> model_project_services = new ArrayList<>();
        model_project_services.add(new Model_Project_Service("B020 - Jasa Ritasi (Port Facility Service)","USD 0.001","39,977.600 T","-","IDR 567,682","IDR 567,682"));
        model_project_services.add(new Model_Project_Service("B020 - Jasa Ritasi (Port Facility Service)","USD 0.001","39,977.600 T","-","IDR 567,682","IDR 567,682"));
        model_project_services.add(new Model_Project_Service("B020 - Jasa Ritasi (Port Facility Service)","USD 0.001","39,977.600 T","-","IDR 567,682","IDR 567,682"));
        detailService = new Adapter_Project_Service(getContext(), model_project_services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailService);


        return view;
    }

    private void PrivateByType(){
        Model_Project data = Model_Project.mp;
        switch (service_type){
            case 0:
                Log.i(TAG, "PrivateByType: " + service_type);
                GlobalByCode();
                break;
            case 1:
                Log.i(TAG, "PrivateByType: " + service_type);
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                break;
        }
    }

    private void GlobalByCode(){
        Model_Project data = Model_Project.mp;
        switch (Model_Project.Code){
            case 0:
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                break;
            case 1:
                ln_list.setVisibility(View.VISIBLE);
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
//                item_list1.setText();
//                item_list2.setText();
                break;
            case 2:
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                ln_bpaj.setVisibility(View.VISIBLE);
//                item_bpaj1.setText();
                break;
            case 3:
                bottom.setVisibility(View.VISIBLE);
//                invoice1.setText();
//                invoice2.setText();
//                invoice3.setText();
//                invoice4.setText();
//                invoice5.setText();
//                invoice6.setText();
                break;
            case 4:
                ln_performa.setVisibility(View.VISIBLE);
//                performa1.setText();
//                performa1.setText();
                break;
        }
    }

}
