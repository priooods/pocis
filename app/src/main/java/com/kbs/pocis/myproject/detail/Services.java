package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Services extends Fragment {

    TextView title1,title2,title3,title4,title5;
    TextView item1,item2,item3,item4,item5, item_list1,item_list2, item_bpaj1,
            invoice1,invoice2,invoice3,invoice4,invoice5,invoice6, performa1,performa2, server_error, title_pembuka;

    ConstraintLayout ln_list,ln_bpaj,ln_performa, ln_top, ln_list_area,ln_report;
    ConstraintLayout bottom;
    LinearLayout ln1, progress;
    List<Model_Project> model_project_services;
    RecyclerView recyclerView, list_schedule_report,list_piloting;
    ProgressBar progressBar;

    int service_type;
    public Services(int types){
        service_type = types;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_services, container,false);
        ln_report = view.findViewById(R.id.ln_report);
        list_schedule_report = view.findViewById(R.id.list_shedule_report);
        list_piloting = view.findViewById(R.id.list_piloting);
        progress = view.findViewById(R.id.progress);
        progressBar = view.findViewById(R.id.progress2);
        ln1 = view.findViewById(R.id.ln1);
        title_pembuka = view.findViewById(R.id.a);
        server_error = view.findViewById(R.id.title_error);
        ln_top = view.findViewById(R.id.ln_top);
        ln_list_area = view.findViewById(R.id.ln_list_area);
        item_list1 = view.findViewById(R.id.item_list1);
        item_list2 = view.findViewById(R.id.item_list2);
        item_bpaj1 = view.findViewById(R.id.item_bpaj1);
        bottom = view.findViewById(R.id.bottom_shet);
        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottom);
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
        PrivateByType();

        return view;
    }

    public void PrivateByType(){
        switch (service_type){
            case 0:
                Log.i(TAG, "PrivateByType: " + service_type);
                GlobalByCode();
                break;
            case 1: // for Bapj Tab Vessel Report
                Log.i(TAG, "PrivateByType: " + service_type);
                ln_top.setVisibility(View.GONE);
                title_pembuka.setVisibility(View.GONE);
                ln_list.setVisibility(View.GONE);
                ln_report.setVisibility(View.VISIBLE);
                CallVesselReportBAPJ();
                CallPilotingBAPJ();
                break;
        }
    }

    public void GlobalByCode(){
        switch (Model_Project.Code){
            case 0: //for aprove
                item1.setText(Model_Project.Information.get(0).exchange_rate);
                item2.setText(Model_Project.Information.get(0).start_date);
                item3.setText(Model_Project.Information.get(0).bi_date);
                item4.setText(Model_Project.Information.get(0).end_date);
                CallingServiceApproval();
                customMargin(ln_list_area, 0,20,0,20);
                break;
            case 1: //for list
                ln_list.setVisibility(View.VISIBLE);
                item2.setText(Model_Project.Information.get(0).start_date);
                item3.setText(Model_Project.Information.get(0).exchange_rate);
                item4.setText(Model_Project.Information.get(0).end_date);
                item5.setText(Model_Project.Information.get(0).bi_date);
                customMargin(ln_list_area, 0,20,0,20);
                Log.i(TAG, "GlobalByCode: " + Model_Project.Information.get(0).total);
                if (Model_Project.Information.get(0).total != null){
                    setFormatIDR(Model_Project.Information.get(0).total_dp, item_list1);
                }
                if (Model_Project.Information.get(0).total_dp != null){
                    setFormatIDR(Model_Project.Information.get(0).total, item_list2);
                }
                CallingServiceList();
                break;
            case 2: //for bapj
                item2.setText(Model_Project.Information.get(0).start_date);
                item3.setText(Model_Project.Information.get(0).exchange_rate);
                item4.setText(Model_Project.Information.get(0).end_date);
                item5.setText(Model_Project.Information.get(0).bi_date);
                customMargin(ln_list_area, 0,20,0,10);
                CallServiceBAPJ();
                break;
            case 3: // for invoice
                ln_top.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                if (Model_Project.InformationAndDocument.get(0).total_net_amount_in_idr != null) {
                    setFormatIDR(Model_Project.InformationAndDocument.get(0).total_net_amount_in_idr, invoice1);
                }
                if (Model_Project.InformationAndDocument.get(0).pph_in_idr != null) {
                    setFormatIDR(Model_Project.InformationAndDocument.get(0).pph_in_idr, invoice3);
                }
                if (Model_Project.InformationAndDocument.get(0).ppn_in_idr != null) {
                    setFormatIDR(Model_Project.InformationAndDocument.get(0).ppn_in_idr, invoice4);
                }
                invoice2.setText(Model_Project.InformationAndDocument.get(0).free_ppn);
                invoice5.setText(Model_Project.InformationAndDocument.get(0).amount_paid_by_dp);
                invoice6.setText(Model_Project.InformationAndDocument.get(0).amount_paid_by_invoice);
                CallingServiceInvoice();
                break;
            case 4: //for performa
                ln_performa.setVisibility(View.VISIBLE);
                ln_top.setVisibility(View.GONE);
                if (Model_Project.InformationAndDocument.get(0).total != null) {
                    setFormatIDR(Model_Project.InformationAndDocument.get(0).total, performa1);
                }
                if (Model_Project.InformationAndDocument.get(0).total_dp != null) {
                    setFormatIDR(Model_Project.InformationAndDocument.get(0).total_dp, performa2);
                }
                CallingServiceProforma();
                customMargin(ln_list_area, 0,20,0,20);
                break;
        }
    }

    public void CallPilotingBAPJ(){
        if (Model_Project.Piloting != null) {
            model_project_services = new ArrayList<>();
            for (int i = 0; i < Model_Project.Piloting.size(); i++){
                model_project_services.add(Model_Project.Piloting.get(i).get(i));
            }
            Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 6);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            list_piloting.setLayoutManager(layoutManager);
            list_piloting.setAdapter(adapter_project_service);
        } else {
            ln_list_area.setVisibility(View.GONE);
            server_error.setVisibility(View.VISIBLE);
        }
    }

    public void CallVesselReportBAPJ(){
        Log.i(TAG, "CallingServiceBAPJ: => " + Model_Project.VesselReport);
        if (Model_Project.VesselReport != null) {
            model_project_services.addAll(Model_Project.VesselReport);
            Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 5);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            list_schedule_report.setLayoutManager(layoutManager);
            list_schedule_report.setAdapter(adapter_project_service);
        } else {
            ln_list_area.setVisibility(View.GONE);
            server_error.setVisibility(View.VISIBLE);
        }
    }

    public void CallServiceBAPJ(){
        Log.i(TAG, "CallingServiceBAPJ: => " + Model_Project.Service);
        if (Model_Project.Service != null) {
            model_project_services.addAll(Model_Project.Service);
            Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 2);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_service);
        } else {
            ln_list_area.setVisibility(View.GONE);
            server_error.setVisibility(View.VISIBLE);
        }
    }

    public void CallingServiceApproval(){
        Log.i(TAG, "CallingServiceApproval: => " + Model_Project.Service);
        if (Model_Project.Service != null) {
            model_project_services.addAll(Model_Project.Service);
            Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 0);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_service);
        } else {
            ln_list_area.setVisibility(View.GONE);
            server_error.setVisibility(View.VISIBLE);
        }
    }

    public void CallingServiceInvoice(){
            Log.i(TAG, "CallingServiceInvoice: => " + Model_Project.Service);
            if (Model_Project.Service != null) {
                model_project_services.addAll(Model_Project.Service);
                Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 3);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter_project_service);
            } else {
                ln_list_area.setVisibility(View.GONE);
                server_error.setVisibility(View.VISIBLE);
            }
    }

    public void CallingServiceProforma(){
        Log.i(TAG, "CallingServiceProforma: => " + Model_Project.Service);
        if (Model_Project.Service != null && Model_Project.Service.size() != 0) {
            model_project_services.addAll(Model_Project.Service);
            Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 4);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_service);
        } else {
            ln_list_area.setVisibility(View.GONE);
            server_error.setVisibility(View.VISIBLE);
        }
    }

    public void CallingServiceList(){
        Log.i(TAG, "CallingServiceList: => " + Model_Project.Service);
        if (Model_Project.Service != null) {
            model_project_services.addAll(Model_Project.Service);
            Adapter_Project_Service adapter_project_service = new Adapter_Project_Service(getContext(), model_project_services, 0);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_service);
        } else {
            ln_list_area.setVisibility(View.GONE);
            server_error.setVisibility(View.VISIBLE);
        }
    }

    public void setFormatIDR(String value, TextView textView){
        DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("IDR ");
        formatSymbols.setMonetaryDecimalSeparator(',');
        formatSymbols.setGroupingSeparator('.');
        kursIndo.setDecimalFormatSymbols(formatSymbols);
        textView.setText(kursIndo.format(Integer.parseInt(value)));
    }

    public void customMargin(View view, int left, int top, int right, int bottom){
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left,top,right,bottom);
            view.requestLayout();
        }
    }


}
