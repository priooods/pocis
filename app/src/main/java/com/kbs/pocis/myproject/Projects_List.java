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
import com.kbs.pocis.service.PublicList.PublicList;
import java.util.List;

public class Projects_List extends FilterFragment {
    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    ProgressBar progressBar;
    RelativeLayout layout_kosong;
    NestedScrollView nested;
    TextView kanan, kiri, kanan_banget, kiri_banget,title_progress,
            index_list_invoice, all_index_invoice,title_nodata;
    ImageView search_icon;
    int total_item = 0;
    Adapter_Project adapter_project_list;
    Project_List_Dasar parent;

    TextView title_text;
    ConstraintLayout tp;
    String title_close = "Showing all list of closed projects. Tap to see details.";
    String title_open = "Showing all list of opened projects. Tap to see details.";
//    Call<CallProjectList> call;
    int list_status;
    public Projects_List(int type, Project_List_Dasar parent) {
        list_status = type;
        this.parent = parent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
//        model_project_s = new ArrayList<>();
        tp = view.findViewById(R.id.a);
        tp.setVisibility(View.GONE);
        title_text = view.findViewById(R.id.q);
        title_nodata = view.findViewById(R.id.title_nodata);

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
        LoadingBar(true);
        Ganti();
        return view;
    }


    void ChangePage(int target_page) {
        if (Ready) {
            Log.w("project_list", "Change Page "+list_status+" to "+target_page);
            model_project_s.clear();
            page_current = target_page;
            GenerateLists();
            Ready = false;
        } else {
            Log.w("project_list", "Aggresive Touch/Command!");
        }
    }

    public void Ganti() {
        kanan.setOnClickListener(view -> ChangePage(page_current + 1));
        kanan_banget.setOnClickListener(view -> ChangePage(page_last));
        kiri_banget.setOnClickListener(view -> ChangePage(1));
        kiri.setOnClickListener(view -> ChangePage(page_current - 1));
    }

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
        Ready = !stat;
    }

    void scrollNested(){
        nested.fullScroll(View.FOCUS_UP);
        nested.smoothScrollTo(0,0);
    }


    protected void ShowAdapter(PublicList.Datas data) {
        Ready = true;
        LoadingBar(false);
        if (data.current_page != page_current && filter == null) {
            Log.e("project_list", list_status + " load adapter!" + data.current_page + "|" + page_current);
            parent.GenerateLists();
            LoadingBar(true);
            return;
        } else if (model_project_s != data.model || pmanager != parent.pmanager) {
            model_project_s = data.model;
            pmanager = parent.pmanager;
        } else {
            if (recyclerView.getAdapter() == null) {
                Log.e("project_list", "refresh adapter!");
            } else {
                Log.e("project_list", "not refresh anything!");
                return;
            }
        }
        page_current = data.current_page;
        page_last = data.last_page;
        total_item = data.total;
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
            adapter_project_list = new Adapter_Project(getContext(), model_project_s, 1);
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
    public void GenerateLists() {
        layout_kosong.setVisibility(View.GONE);
        parent.GenerateLists();
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    }
}