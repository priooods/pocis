package com.kbs.pocis.myproject;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.CallProjectList;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Projects_List extends FilterFragment {

    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    ProgressBar progressBar;
    RelativeLayout layout_kosong;
    NestedScrollView nested;
    TextView kanan, kiri, kanan_banget, kiri_banget,title_progress,
            index_list_invoice, all_index_invoice;
    ImageView search_icon;
    int total_item = 0;

    TextView title_text;
    ConstraintLayout tp;
    String title_close = "Showing all list of closed projects. Tap to see details.";
    String title_open = "Showing all list of opened projects. Tap to see details.";
    Call<CallProjectList> call;
    int list_status;
    public Projects_List(int type) {
        list_status = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        model_project_s = new ArrayList<>();
        tp = view.findViewById(R.id.a);
        tp.setVisibility(View.GONE);
        title_text = view.findViewById(R.id.q);

        switch (list_status){
            case 0:
                title_text.setText(title_open);
                break;
            case 1:
                title_text.setText(title_close);
                break;
            case 2:
                title_text.setVisibility(View.GONE);
                break;
        }

        nested = view.findViewById(R.id.nested);
        title_progress = view.findViewById(R.id.title_progress);
        progressBar = view.findViewById(R.id.progress);
        search_icon = view.findViewById(R.id.btn_search_invoice);
        layout_kosong = view.findViewById(R.id.lay_invoice_kosong);
        kanan = view.findViewById(R.id.kanan);
        progressBar = view.findViewById(R.id.progress);
        kiri = view.findViewById(R.id.kiri);
        kanan_banget = view.findViewById(R.id.kanan_banget);
        kiri_banget = view.findViewById(R.id.kiri_banget);
        index_list_invoice = view.findViewById(R.id.index_list_invoice);
        all_index_invoice = view.findViewById(R.id.all_index_invoice);
        recyclerView = view.findViewById(R.id.list_invoice);

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
            title_progress.setVisibility(View.VISIBLE);
            nested.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            title_progress.setVisibility(View.GONE);
            nested.setVisibility(View.VISIBLE);
        }
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
        Log.i("frag_list", "Call list page = " + page);
        if (UserData.isExists()) {
            call = UserData.i.getService().getListProject(UserData.i.getToken(), String.valueOf(page));
            CallingData(page, list);
            try {
                Thread.sleep(100, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void ShowAdapter() {
        if (model_project_s != null && model_project_s.size() > 0) {
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);

            String of = page_current + "  Of  " + page_last;
            String show = "Showing " + (model_project_s.size()) + " of " + total_item + " results";
            index_list_invoice.setText(of);
            all_index_invoice.setText(show);
            Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_list);
        } else {
            nested.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
        }
    }

    private void CallingData(int page, int list){
        call.enqueue(new Callback<CallProjectList>() {
            @Override
            public void onResponse(@NotNull Call<CallProjectList> call, @NotNull Response<CallProjectList> response) {
                CallProjectList respone = response.body();
                if (Calling.TreatResponse(getContext(), "list", respone)) {
                    //for status open
                    if (list_status == 0){
                        if (!filtering) {
                            assert respone != null;
                            model_project_s = respone.data.Open.model;
                            page_current = respone.data.Open.current_page;
                            page_last = respone.data.Open.last_page;
                            total_item = respone.data.Open.total;
                            FinishFilter();
                        } else {
                            if (pmanager.loaded) {
                                assert respone != null;
                                List<Model_Project> data = respone.data.Open.model;
                                for (int i = list; i < data.size(); i++) {
                                    if (filter.checkFilter(data.get(i))) {
                                        if (model_project_s.size() < pmanager.page_capacity) {
                                            model_project_s.add(data.get(i));
                                            Log.i("frag_list", data.get(i).vessel_name);
                                        } else {
                                            page_last = pmanager.page_last;
                                            total_item = pmanager.total;
                                            load = false;
                                            FinishFilter();
                                            return;
                                        }
                                    }
                                }
                                if (page < respone.data.Open.last_page) {
                                    GenerateFilter(page + 1, 0);
                                } else {
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    FinishFilter();
                                }
                            } else {
                                int i = 0;
                                assert respone != null;
                                for (Model_Project data : respone.data.Open.model) {
                                    if (filter.checkFilter(data)) {
                                        if (pmanager.addPack(page, i) && load) {
                                            model_project_s.add(data);
                                            Log.i("frag_list", data.vessel_name);
                                        } else {
                                            load = false;
                                        }
                                    }
                                    i++;
                                }
                                if (page == respone.data.Open.last_page) {
                                    if (pmanager.pack > 0) {
                                        pmanager.finalPack(page, i - 1);
                                    }
                                    pmanager.finishLoad();
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    nested.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    title_progress.setVisibility(View.GONE);
                                    ShowAdapter();
                                } else {
                                    GenerateFilter(page + 1, 0);
                                }
                            }
                        }
                    }
                    //for status close
                    else if (list_status == 1){
                        if (!filtering) {
                            assert respone != null;
                            model_project_s = respone.data.Close.model;
                            page_current = respone.data.Close.current_page;
                            page_last = respone.data.Close.last_page;
                            total_item = respone.data.Close.total;
                            FinishFilter();
                        } else {
                            if (pmanager.loaded) {
                                assert respone != null;
                                List<Model_Project> data = respone.data.Close.model;
                                for (int i = list; i < data.size(); i++) {
                                    if (filter.checkFilter(data.get(i))) {
                                        if (model_project_s.size() < pmanager.page_capacity) {
                                            model_project_s.add(data.get(i));
                                            Log.i("frag_list", data.get(i).vessel_name);
                                        } else {
                                            page_last = pmanager.page_last;
                                            total_item = pmanager.total;
                                            load = false;
                                            FinishFilter();
                                            return;
                                        }
                                    }
                                }
                                if (page < respone.data.Close.last_page) {
                                    GenerateFilter(page + 1, 0);
                                } else {
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    FinishFilter();
                                }
                            } else {
                                int i = 0;
                                assert respone != null;
                                for (Model_Project data : respone.data.Close.model) {
                                    if (filter.checkFilter(data)) {
                                        if (pmanager.addPack(page, i) && load) {
                                            model_project_s.add(data);
                                            Log.i("frag_list", data.vessel_name);
                                        } else {
                                            load = false;
                                        }
                                    }
                                    i++;
                                }
                                if (page == respone.data.Close.last_page) {
                                    if (pmanager.pack > 0) {
                                        pmanager.finalPack(page, i - 1);
                                    }
                                    pmanager.finishLoad();
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    nested.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    title_progress.setVisibility(View.GONE);
                                    ShowAdapter();
                                } else {
                                    GenerateFilter(page + 1, 0);
                                }
                            }
                        }
                    } else { // for status ALL
                        if (!filtering) {
                            assert respone != null;
                            model_project_s = respone.data.All.model;
                            page_current = respone.data.All.current_page;
                            page_last = respone.data.All.last_page;
                            total_item = respone.data.All.total;
                            FinishFilter();
                        } else {
                            if (pmanager.loaded) {
                                assert respone != null;
                                List<Model_Project> data = respone.data.All.model;
                                for (int i = list; i < data.size(); i++) {
                                    if (filter.checkFilter(data.get(i))) {
                                        if (model_project_s.size() < pmanager.page_capacity) {
                                            model_project_s.add(data.get(i));
                                            Log.i("frag_list", data.get(i).vessel_name);
                                        } else {
                                            page_last = pmanager.page_last;
                                            total_item = pmanager.total;
                                            load = false;
                                            FinishFilter();
                                            return;
                                        }
                                    }
                                }
                                if (page < respone.data.All.last_page) {
                                    GenerateFilter(page + 1, 0);
                                } else {
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    FinishFilter();
                                }
                            } else {
                                int i = 0;
                                assert respone != null;
                                for (Model_Project data : respone.data.All.model) {
                                    if (filter.checkFilter(data)) {
                                        if (pmanager.addPack(page, i) && load) {
                                            model_project_s.add(data);
                                            Log.i("frag_list", data.vessel_name);
                                        } else {
                                            load = false;
                                        }
                                    }
                                    i++;
                                }
                                if (page == respone.data.All.last_page) {
                                    if (pmanager.pack > 0) {
                                        pmanager.finalPack(page, i - 1);
                                    }
                                    pmanager.finishLoad();
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    nested.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    title_progress.setVisibility(View.GONE);
                                    ShowAdapter();
                                } else {
                                    GenerateFilter(page + 1, 0);
                                }
                            }
                        }
                    }
                } else {
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
            public void onFailure(@NotNull Call<CallProjectList> call, @NotNull Throwable t) {
                if (!(t instanceof SocketTimeoutException)) {
                    Ready = true;
                    Log.e("frag_list", "on Failure called!" + t);
                }
                Toasty.error(requireContext(),"Connection Server Error, Your token delete ! Please Login Again", Toasty.LENGTH_SHORT, true).show();

            }
        });
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    }
}