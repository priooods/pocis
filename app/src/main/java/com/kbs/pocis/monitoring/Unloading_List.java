package com.kbs.pocis.monitoring;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Monitoring;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Monitoring;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.Loading_Unloading;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Unloading_List extends FilterFragment {

    String bert = "Showing all berthing list. Tap to see details.";
    String plain = "Showing all Plained list. Tap to see details.";
    String depart = "Showing all Departure list. Tap to see details.";
    String nodata = "Oops.. Nothing Data";
    int status;
    View view;
    public Unloading_List(int stat){
        status = stat;
    }
    RecyclerView recyclerView;
    TextView title,title_nodata;
    List<Model_Monitoring> model_monitorings;
    RelativeLayout  layout_kosong;
    TextView kanan, kiri, kanan_banget, kiri_banget,title_progress,
            index_list_invoice, all_index_invoice;
    NestedScrollView layout_ada;
    ProgressBar progressBar;
    ConstraintLayout ln_top;
    int total_item = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_invoice,container,false);
        title = view.findViewById(R.id.q);
        title_nodata = view.findViewById(R.id.title_nodata);
        ln_top = view.findViewById(R.id.a);
        ln_top.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.listing_monitoring);
        layout_ada = view.findViewById(R.id.nested);
        title_progress = view.findViewById(R.id.title_progress);
        progressBar = view.findViewById(R.id.progress);
        layout_kosong = view.findViewById(R.id.lay_invoice_kosong);
        kanan = view.findViewById(R.id.kanan);
        kiri = view.findViewById(R.id.kiri);
        kanan_banget = view.findViewById(R.id.kanan_banget);
        kiri_banget = view.findViewById(R.id.kiri_banget);
        index_list_invoice = view.findViewById(R.id.index_list_invoice);
        all_index_invoice = view.findViewById(R.id.all_index_invoice);
        recyclerView = view.findViewById(R.id.list_invoice);


        title_nodata.setText(nodata);
        customMargin(title_nodata, 0,10,0,0);
        filter = null;
        max_list = 5;
        GenerateLists();
        Ganti();
        return view;
    }

    void ChangePage(int target_page) {
        if (Ready) {
            page_current = target_page;
            GenerateLists();
            Ready = false;
        } else {
            Log.w("all_booking", "Aggresive Touch/Command!");
        }
    }

    public void Ganti() {
        kanan.setOnClickListener(view -> ChangePage(page_current + 1));
        kanan_banget.setOnClickListener(view -> ChangePage(page_last));
        kiri_banget.setOnClickListener(view -> ChangePage(1));
        kiri.setOnClickListener(view -> ChangePage(page_current - 1));
    }

    @Override
    protected void LoadingBar(boolean stat){
        if (stat){
            progressBar.setVisibility(View.VISIBLE);
            layout_ada.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            layout_ada.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void Model_CheckorClear() {
        if (model_monitorings == null)
            model_monitorings = new ArrayList<>();
        else
            model_monitorings.clear();
    }

    @Override
    protected void ShowAdapter() {
        if (model_monitorings != null && model_monitorings.size() > 0) {
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);

            String of = page_current + "  Of  " + page_last;
            String show = "Showing " + (model_monitorings.size()) + " of " + total_item + " results";
            index_list_invoice.setText(of);
            all_index_invoice.setText(show);

            Adapter_Monitoring adapter_monitoring = new Adapter_Monitoring(getContext(), model_monitorings,0);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_monitoring);
        } else {
            layout_ada.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void GenerateFilter(int page, int list) {
        Call<Loading_Unloading> call;
        if (status == 0){
            call = UserData.i.getService().getUnloading(UserData.i.getToken(),"Berthing");
            title.setText(bert);
        } else if (status == 1){
            call = UserData.i.getService().getUnloading(UserData.i.getToken(),"Planned");
            title.setText(plain);
        } else {
            call = UserData.i.getService().getUnloading(UserData.i.getToken(),"Departure");
            title.setText(depart);
        }
        call.enqueue(new Callback<Loading_Unloading>() {
            @Override
            public void onResponse(@NotNull Call<Loading_Unloading> call, @NotNull Response<Loading_Unloading> response) {
                Loading_Unloading respone = response.body();
                if (Calling.TreatResponse(getContext(),"unloading", respone)){
                    if (!filtering){
                        assert respone != null;
                        model_monitorings.addAll(respone.data.model);
                        page_current = respone.data.current_page;
                        page_last = respone.data.last_page;
                        total_item = respone.data.total;
                        FinishFilter();
                    } else {
                        if (pmanager.loaded) {
                            Log.i("unloading"," page = " + page);
                            assert respone != null;
                            List<Model_Monitoring> data = respone.data.model;
                            for (int i = list; i < data.size(); i++) {
                                if (filter.checkFilter(data.get(i))) {
                                    if (model_monitorings.size() < pmanager.page_capacity) {
                                        model_monitorings.add(data.get(i));
                                        Log.i("unloading", data.get(i).act_anchorage);
                                    } else {
                                        page_last = pmanager.page_last;
                                        total_item = pmanager.total;
                                        load = false;
                                        FinishFilter();
                                        return;
                                    }
                                }
                            }
                            if (page < respone.data.last_page) {
                                GenerateFilter(page + 1, 0);
                            } else {
                                page_last = pmanager.page_last;
                                total_item = pmanager.total;
                                load = false;
                                FinishFilter();
                            }
                        } else {
                            Log.i("unloading",  " page = " + page + " load = " + load);
                            int i = 0;
                            assert respone != null;
                            for (Model_Monitoring data : respone.data.model) {
                                if (filter.checkFilter(data)) {
                                    if (pmanager.addPack(page, i) && load) {
                                        model_monitorings.add(data);
//                                        Log.i("booking_load", data.readString());
                                    } else {
                                        load = false;
                                    }
                                }
                                i++;
                            }
                            if (page == respone.data.last_page) {
                                if (pmanager.pack > 0) {
                                    pmanager.finalPack(page, i - 1);
                                }
                                pmanager.finishLoad();
                                page_last = pmanager.page_last;
                                total_item = pmanager.total;
                                load = false;
                                layout_ada.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                FinishFilter();
                            } else {
                                GenerateFilter(page + 1, 0);
                            }
                        }
                    }
                }
                else {
                    try {
                        Thread.sleep(4500, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        GenerateFilter(page, 0);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Loading_Unloading> call, @NotNull Throwable t) {
                Ready = true;
                Log.e("unloading", "on Failure called!" + t);
            }
        });
        try {
            Thread.sleep(100, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    }

    public void customMargin(View view, int left, int top, int right, int bottom){
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left,top,right,bottom);
            view.requestLayout();
        }
    }
}
