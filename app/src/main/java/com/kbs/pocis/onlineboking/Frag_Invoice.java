package com.kbs.pocis.onlineboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.onlinebooking.CallingData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_Invoice extends FilterFragment {

    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    DialogFragment fragment;
    ImageView search_icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        model_project_s = new ArrayList<>();
        search_icon = view.findViewById(R.id.btn_search_invoice);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true,Frag_Invoice.this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        filter = null;
        max_list = 10;
        GenerateLists();
        recyclerView = view.findViewById(R.id.list_invoice);
//
//        model_project_s.add(new Model_Project("P0019-2020-00021","UNPAID","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
//                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
//                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
//                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
//                "YES","2020-11-27","Quay/Dermaga"));
//        model_project_s.add(new Model_Project("P0019-2020-00022","PAID","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
//                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
//                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
//                "02518/BAPJ-JPL/III/2020",2,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
//                "YES","2020-11-27","Quay/Dermaga"));
//        model_project_s.add(new Model_Project("P0019-2020-00023","UNPAID","PPJ/P0019-288/03","H9JY01002","PPJ-2020/15550",
//                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
//                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
//                "02518/BAPJ-JPL/III/2020",3,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
//                "YES","2020-11-27","Quay/Dermaga"));
//        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 3);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter_project_list);

        return view;
    }

    @Override
    protected void ShowAdapter() {
        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
    }

    @Override
    protected void Model_CheckorClear() {
        if (model_project_s == null)
            model_project_s = new ArrayList<>();
        else
            model_project_s.clear();
    }

    @Override
    protected void GenerateFilter(int page, int list){
        Call<PublicList> call = UserData.i.getService().getListInvoice(UserData.i.getToken());
        call.enqueue(new Callback<PublicList>() {
            @Override
            public void onResponse(Call<PublicList> call, Response<PublicList> response) {
//                if (data.TreatResponse(getContext(),"invoice", data)){
//                    for (Model_Project project : data.data.model){
//                        model_project_s.add(project);
//                    }
//                    Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 3);
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setAdapter(adapter_project_list);
//                }


                PublicList respone = response.body();
                if (Calling.TreatResponse(getContext(), "invoice", respone)) {
                    if (!filtering) {
                        for (Model_Project project : respone.data.model){
                            model_project_s.add(project);
                        }
                        page_current = respone.data.current_page;
                        page_last = respone.data.last_page;
                        FinishFilter();
                    }else {
                        if (pmanager.loaded) {
                            Log.i("booking_load", "pmanager.load"+pmanager.loaded+" page = "+page);
                            Model_Project[] data = respone.data.model;
                            for (int i = list; i < data.length; i++) {
                                if (filter.checkFilter(data[i])) {
                                    if (model_project_s.size() < pmanager.page_capacity) {
                                        model_project_s.add(data[i]);
                                        Log.i("booking_load",data[i].status_payment);
                                    }else{
                                        page_last = pmanager.page_last;
                                        load = false;
                                        FinishFilter();
                                        return;
                                    }
                                }
                            }
                            if (page<respone.data.last_page){
                                GenerateFilter(page +1, 0);
                            }else {
                                page_last = pmanager.page_last;
                                load = false;
                                FinishFilter();
                                return;
                            }
                        } else {
                            Log.i("booking_load", "pmanager.load"+pmanager.loaded+" page = "+page+" load = "+load);
                            int i = 0;
                            for (Model_Project data : respone.data.model) {
                                if (filter.checkFilter(data)) {
                                    if (pmanager.addPack(page, i) && load) {
                                        model_project_s.add(data);
                                        Log.i("booking_load",data.status_payment);
                                    } else {
                                        load = false;
                                    }
                                }
                                i++;
                            }
                            //TODO semisal kagak ada next-back page
                            if (page >= respone.data.last_page || !load) {
                                if (pmanager.pack > 0) {
                                    pmanager.finalPack(page, i - 1);
                                }
                                pmanager.finishLoad();
                                page_last = pmanager.page_last;
                                load = false;
                                ShowAdapter();
                            } else {
                                GenerateFilter(page + 1, 0);
                            }
                        }
                    }
                }else{
                    try {
                        Thread.sleep(4500,0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        GenerateFilter(page, 0);
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<PublicList> call, Throwable t) {
                Ready = true;
                Log.e("frag_invoice", "on Failure called!" + t);
            }
        });
        try {
            Thread.sleep(100,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
