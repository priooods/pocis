package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.util.Log;
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
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Services extends Fragment {

    TextView title1,title2,title3,title4,title5;
    TextView item1,item2,item3,item4,item5, item_list1,item_list2, item_bpaj1,
            invoice1,invoice2,invoice3,invoice4,invoice5,invoice6, performa1,performa2;

    ConstraintLayout ln_list,ln_bpaj,ln_performa, ln_top, ln_list_area;
    View bottom;
    LinearLayout ln1;
    BottomSheetBehavior bottomSheetBehavior;
    List<Model_Project> model_project_services;
    RecyclerView recyclerView;

    int service_type;
    public Services(int types){
        service_type = types;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_services, container,false);

        ln1 = view.findViewById(R.id.ln1);
        ln_top = view.findViewById(R.id.ln_top);
        ln_list_area = view.findViewById(R.id.ln_list_area);
        item_list1 = view.findViewById(R.id.item_list1);
        item_list2 = view.findViewById(R.id.item_list2);
        item_bpaj1 = view.findViewById(R.id.item_bpaj1);
        bottom = view.findViewById(R.id.bottom_shet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        bottomSheetBehavior.setHideable(false);

        model_project_services = new ArrayList<>();
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

        title1 = view.findViewById(R.id.title1);
        title2 = view.findViewById(R.id.title2);
        title3 = view.findViewById(R.id.title3);
        title4 = view.findViewById(R.id.title4);
        title5 = view.findViewById(R.id.title5);

        recyclerView = view.findViewById(R.id.recycle_service_project);
        model_project_services = new ArrayList<>();
        if (Model_Project.isExist()) {
            PrivateByType();
        }

        return view;
    }

    public void PrivateByType(){
//        Model_Project data = Model_Project.mp;
        switch (service_type){
            case 0:
                Log.i(TAG, "PrivateByType: " + service_type);
                GlobalByCode();
                break;
            case 1:
                Log.i(TAG, "PrivateByType: " + service_type);
                break;
        }
    }

    public void GlobalByCode(){
        Model_Project data = Model_Project.mp;
        switch (Model_Project.Code){
            case 0: //for aprove
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                break;
            case 1: //for list
                ln_list.setVisibility(View.VISIBLE);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
//                item_list1.setText();
//                item_list2.setText();
                break;
            case 2: //for bapj
                ln1.setVisibility(View.VISIBLE);
                item1.setText(data.location);
                item2.setText(data.start_date);
                item3.setText(data.exhange_rate);
                item4.setText(data.end_date);
                item5.setText(data.bi_date);
                ln_bpaj.setVisibility(View.VISIBLE);
//                item_bpaj1.setText();
                break;
            case 3: // for invoice
                ln_top.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                invoice1.setText(data.total_net_amount_in_idr);
                setFormatIDR(data.total_net_amount_in_idr, invoice1);
                invoice2.setText(data.free_ppn);
                invoice3.setText(data.pph_in_idr);
                setFormatIDR(data.pph_in_idr, invoice3);
                invoice4.setText(data.ppn_in_idr);
                setFormatIDR(data.ppn_in_idr, invoice4);
                invoice5.setText(data.amount_paid_by_dp);
                invoice6.setText(data.amount_paid_by_invoice);
                CallingServiceInvoice();
                break;
            case 4: //for performa
                ln_performa.setVisibility(View.VISIBLE);
                ln_top.setVisibility(View.GONE);
                performa1.setText(data.total);
                performa2.setText(data.total_dp);
                setFormatIDR(data.total, performa1);
                setFormatIDR(data.total_dp, performa2);
                CallingServiceProforma();
                customMargin(ln_list_area, 0,0,0,15);
                break;
        }
    }

    public void CallingServiceInvoice(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().getDetailInvoice(UserData.i.getToken(), data.t_billing_invoice_id, data.flag_compound);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(getContext(), "service", detail)) {
                    assert detail != null;
                    Log.i("service", "onResponse: => " + detail.data.Service);
                    model_project_services.addAll(detail.data.Service);
                    Log.i("service", "onResponse: => " + model_project_services.size());
                    Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services,3);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter_project_service);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("frag_service", "on Failure called!" + t);
            }
        });
    }

    public void CallingServiceProforma(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().getDetailProforma(UserData.i.getToken(), data.t_project_header_id, data.flag_compound);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(getContext(), "service", detail)) {
                    assert detail != null;
                    Log.i("service", "onResponse: => " + detail.data.Service);
                    model_project_services.addAll(detail.data.Service);
                    Log.i("service", "onResponse: => " + model_project_services.size());
                    Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services,4);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter_project_service);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("frag_service", "on Failure called!" + t);
            }
        });
    }

    public void setFormatIDR(String value, TextView textView){
        DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("IDR ");
        formatSymbols.setMonetaryDecimalSeparator(',');
        formatSymbols.setGroupingSeparator('.');
        kursIndo.setDecimalFormatSymbols(formatSymbols);
        textView.setText(kursIndo.format(Integer.valueOf(value)));
    }

    public void customMargin(View view, int left, int top, int right, int bottom){
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left,top,right,bottom);
            view.requestLayout();
        }
    }


}