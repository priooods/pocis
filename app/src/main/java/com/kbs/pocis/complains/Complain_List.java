package com.kbs.pocis.complains;

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
import com.kbs.pocis.adapter.Adapter_Complain;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Complain_List extends FilterFragment {

    int status;
    public Complain_List(int type){
        status=type;
    }
    String nodata = "Oops.. Nothing Data";

    RecyclerView recyclerView;
    List<Model_Project> model_complains;

    TextView title,title_nodata;
    RelativeLayout  layout_kosong;
    TextView kanan, kiri, kanan_banget, kiri_banget,title_progress,
            index_list_invoice, all_index_invoice;
    NestedScrollView layout_ada;
    ProgressBar progressBar;
    ConstraintLayout ln_top;
    int total_item = 0;
//complaint_list
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice,container,false);

        title = view.findViewById(R.id.q);
        title_nodata = view.findViewById(R.id.title_nodata);
        title.setVisibility(View.GONE);
        ln_top = view.findViewById(R.id.a);
        ln_top.setVisibility(View.GONE);
        model_complains = new ArrayList<>();

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
        customMargin(layout_ada, 0,0,0, 40);
        filter = null;
        max_list = 5;
        GenerateLists();
        Ganti();
//        checkStatus();

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
            Log.w("unloading", "Aggresive Touch/Command!");
        }
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
        if (model_complains == null)
            model_complains = new ArrayList<>();
        else
            model_complains.clear();
    }

    void scrollNested(){
        layout_ada.fullScroll(View.FOCUS_UP);
        layout_ada.smoothScrollTo(0,0);
    }

    @Override
    protected void ShowAdapter() {
        if (model_complains != null && model_complains.size() > 0) {
            scrollNested();
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);

            String of = page_current + "  Of  " + page_last;
            String show = "Showing " + (model_complains.size()) + " of " + total_item + " results";
            index_list_invoice.setText(of);
            all_index_invoice.setText(show);

            Adapter_Complain adapter_project_list = new Adapter_Complain(getContext(), model_complains);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_project_list);
        } else {
            layout_ada.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void GenerateFilter(int page, int list) {
        if (status == 0) {
            if (UserData.isExists()) {
                Call<PublicList> call = UserData.i.getService().getComplainList(UserData.i.getToken(), String.valueOf(page));
                call.enqueue(new Callback<PublicList>() {
                    @Override
                    public void onResponse(@NotNull Call<PublicList> call, @NotNull Response<PublicList> response) {
                        PublicList respone = response.body();
                        if (Calling.TreatResponse(getContext(), "complain_listing", respone)) {
                            if (!filtering) {
                                assert respone != null;
                                model_complains.addAll(respone.data.model);
                                page_current = respone.data.current_page;
                                page_last = respone.data.last_page;
                                total_item = respone.data.total;
                                FinishFilter();
                            } else {
                                if (pmanager.loaded) {
                                    Log.i("complain_listing", " page = " + page);
                                    assert respone != null;
                                    List<Model_Project> data = respone.data.model;
                                    for (int i = list; i < data.size(); i++) {
                                        if (filter.checkFilter(data.get(i))) {
                                            if (model_complains.size() < pmanager.page_capacity) {
                                                model_complains.add(data.get(i));
//                                            Log.i("unloading", data.get(i).act_anchorage);
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
                                    Log.i("complain_listing", " page = " + page + " load = " + load);
                                    int i = 0;
                                    assert respone != null;
                                    for (Model_Project data : respone.data.model) {
                                        if (filter.checkFilter(data)) {
                                            if (pmanager.addPack(page, i) && load) {
                                                model_complains.add(data);
//                                        Log.i("booking_load", data.readString());
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
                                        layout_ada.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
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
                        Ready = true;
                        Log.e("complain_listing", "on Failure called!" + t);
                    }
                });
                try {
                    Thread.sleep(100, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            layout_ada.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
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
