package com.kbs.pocis.myproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
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
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Projects_Approved extends FilterFragment {

    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    DialogFragment fragment;
    ProgressBar progressBar;
    RelativeLayout layout_kosong;
    NestedScrollView nested;
    TextView kanan, kiri, kanan_banget, kiri_banget,title_progress,
            index_list_invoice, all_index_invoice, titles, title_nodata;
    ImageView search_icon;
    int total_item = 0;
    String title = "Showing all Approval list. Tap to see details.";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        model_project_s = new ArrayList<>();
        nested = view.findViewById(R.id.nested);
        titles = view.findViewById(R.id.q);
        title_nodata = view.findViewById(R.id.title_nodata);
        titles.setText(title);
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

        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, Projects_Approved.this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        filter = null;
        max_list = 5;
        GenerateLists();
        Ganti();

        return view;
    }


    public void Ganti() {
        kanan.setOnClickListener(view -> ChangePage(page_current + 1));
        kanan_banget.setOnClickListener(view -> ChangePage(page_last));
        kiri_banget.setOnClickListener(view -> ChangePage(1));
        kiri.setOnClickListener(view -> ChangePage(page_current - 1));
    }

    void ChangePage(int target_page) {
        if (Ready) {
            page_current = target_page;
            GenerateLists();
            Ready = false;
        } else {
            Log.w("approved", "Aggresive Touch/Command!");
        }
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

    void scrollNested(){
        nested.fullScroll(View.FOCUS_UP);
        nested.smoothScrollTo(0,0);
    }


    @Override
    protected void ShowAdapter() {
        if (model_project_s != null && model_project_s.size() > 0) {
            scrollNested();
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);

            String of = page_current + "  Of  " + page_last;
            String show = "Showing " + (model_project_s.size()) + " of " + total_item + " results";
            index_list_invoice.setText(of);
            all_index_invoice.setText(show);

            Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 0);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_list);
        } else {
            nested.setVisibility(View.GONE);
            title_nodata.setText(R.string.not_found);
            layout_kosong.setVisibility(View.VISIBLE);
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
        layout_kosong.setVisibility(View.GONE);
        Log.i("frag_aproved", "Call Approved page = " + page);
        if (UserData.isExists()) {
            Call<PublicList> call = UserData.i.getService().getListApproved(UserData.i.getToken(), String.valueOf(page));
            call.enqueue(new Callback<PublicList>() {
                @Override
                public void onResponse(@NotNull Call<PublicList> call, @NotNull Response<PublicList> response) {
                    PublicList respone = response.body();
                    if (Calling.TreatResponse(getContext(), "approved", respone)) {
                        if (!filtering) {
                            assert respone != null;
                            model_project_s = respone.data.model;
                            page_current = respone.data.current_page;
                            page_last = respone.data.last_page;
                            total_item = respone.data.total;
                            FinishFilter();
                        } else {
                            layout_kosong.setVisibility(View.GONE);
                            if (pmanager.loaded) {
                                assert respone != null;
                                List<Model_Project> data = respone.data.model;
                                for (int i = list; i < data.size(); i++) {
                                    if (filter.checkFilter(data.get(i))) {
                                        if (model_project_s.size() < pmanager.page_capacity) {
                                            model_project_s.add(data.get(i));
                                            Log.i("approved", data.get(i).vessel_name);
                                        } else {
                                            page_last = pmanager.page_last;
                                            total_item = pmanager.total;
                                            load = false;
                                            FinishFilter();
                                            return;
                                        }
                                    }
                                }
                                if (page < respone.data.last_page && pmanager.getLastPage()) {
                                    GenerateFilter(pmanager.getNextPage(), 0);
                                } else {
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    FinishFilter();
                                }
                            } else {
                                int i = 0;
                                assert respone != null;
                                for (Model_Project data : respone.data.model) {
                                    if (filter.checkFilter(data)) {
                                        if (pmanager.addPack(page, i) && load) {
                                            model_project_s.add(data);
                                            Log.i("approved", data.vessel_name);
                                        } else {
                                            load = false;
                                        }
                                    }
                                    i++;
                                }
                                if (page == respone.data.last_page) {
                                    pmanager.finishLoad();
                                    page_last = pmanager.page_last;
                                    total_item = pmanager.total;
                                    load = false;
                                    nested.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    title_progress.setVisibility(View.GONE);
                                    FinishFilter();
                                } else {
                                    GenerateFilter(page + 1, 0);
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
                public void onFailure(@NotNull Call<PublicList> call, @NotNull Throwable t) {
                    if (!(t instanceof SocketTimeoutException)) {
                        Ready = true;
                        Log.e("frag_approved", "on Failure called!" + t);
                    }
                    Toasty.error(requireContext(),"Connection Server Error, Please Try Again", Toasty.LENGTH_SHORT, true).show();

                }
            });
            try {
                Thread.sleep(100, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        filtering = false;
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop: ");
//        filtering = false;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy: ");
//        filtering = false;
//    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    }
}