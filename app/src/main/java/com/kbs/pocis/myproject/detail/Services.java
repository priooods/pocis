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

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.myprojects.Adapter_Project_Service;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.model.myprojects.Model_Project_Service;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Services extends Fragment {

    Adapter_Project_Service detailService;
    LinearLayout ln1,ln2,ln3,ln4,ln5,ln6,ln7;
    TextView item1,item2,item3,item4,item5,
            title_bot2, item_bot1,item_bot2,item_bot3,item_bot4,item_bot5,item_bot6,item_bot7;

    ConstraintLayout ln_top,ln_bot;
    RecyclerView recyclerView;

    int service_type;
    public Services(int types){
        service_type = types;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_services, container,false);

        ln1 = view.findViewById(R.id.ln_bot1);
        ln2 = view.findViewById(R.id.ln_bot2);
        ln3 = view.findViewById(R.id.ln_bot3);
        ln4 = view.findViewById(R.id.ln_bot4);
        ln5 = view.findViewById(R.id.ln_bot5);
        ln6 = view.findViewById(R.id.ln_bot6);
        ln7 = view.findViewById(R.id.ln_bot7);

        ln_top = view.findViewById(R.id.ln_top);
        ln_bot = view.findViewById(R.id.ln_bot);

        title_bot2 = view.findViewById(R.id.title_bot2);
        item_bot1 = view.findViewById(R.id.item_bot1);
        item_bot2 = view.findViewById(R.id.item_bot2);
        item_bot3 = view.findViewById(R.id.item_bot3);
        item_bot4 = view.findViewById(R.id.item_bot4);
        item_bot5 = view.findViewById(R.id.item_bot5);
        item_bot6 = view.findViewById(R.id.item_bot6);
        item_bot7 = view.findViewById(R.id.item_bot7);

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
                ln_bot.setVisibility(View.GONE);
                break;
        }
    }

    private void GlobalByCode(){
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());
        Model_Project data = Model_Project.mp;
        switch (Model_Project.Code){
            case 0:
                ln_bot.setVisibility(View.GONE);
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                break;
            case 1:
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                break;
            case 2:
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                ln1.setVisibility(View.GONE);
                break;
            case 3:
            case 4:
                ln_top.setVisibility(View.GONE);
                ln1.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = ln2.getLayoutParams();
                params.width = Math.round(width);
                ln2.setLayoutParams(params);

                ln3.setVisibility(View.VISIBLE);
                ln4.setVisibility(View.VISIBLE);
                ln5.setVisibility(View.VISIBLE);
                ln6.setVisibility(View.VISIBLE);
                ln7.setVisibility(View.VISIBLE);
                title_bot2.setTextSize(9);
                item_bot2.setTextSize(11);
                break;
        }
    }

}
