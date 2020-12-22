package com.kbs.pocis.filter;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.adapter.onlineboking.Adapter_AllBooking;
import com.kbs.pocis.model.onlineboking.Model_Bookings;
import com.kbs.pocis.onlineboking.Filters;
import com.kbs.pocis.onlineboking.PageManager;
import com.kbs.pocis.service.UserData;

public class FilterFragment extends Fragment {
    public Filters filter;
    protected int page_last = 0 , page_current = 0;
    public PageManager pmanager;
    protected boolean Ready,load,filtering;
    public int max_list;

    protected void GenerateFilter(int page, int list){
        Log.e("filter","FilterFragment.GenerateFilter not override well!");
    }
    protected void ShowAdapter(){
        Log.e("filter","FilterFragment.ShowAdapter not override well!");
    }
    protected void Model_CheckorClear(){
        Log.e("filter","FilterFragment.Model_CheckorClear not override well!");
    }
    protected void LoadingBar(boolean start){}
    protected void GenerateLists() {
        LoadingBar(true);
        //TODO START LOADING
        if (UserData.i == null) {
            Log.e("all_booking", "ERROR SERVICE");
        }
        Model_CheckorClear();
        if (filter != null) {
            filtering = load = true;
            if (pmanager == null) {
                pmanager = new PageManager(max_list);
                page_current = 1;
                GenerateFilter(1, 0);
            } else {
                GenerateFilter(pmanager.getPage(page_current), pmanager.getList(page_current));
            }
            return;
        }
        filtering = false;
        GenerateFilter(page_current, 0);
    }
    protected void FinishFilter() {
        //TODO SELESAI LOADING
        LoadingBar(false);
        Log.i("Filter","FinishFiltes()");
        Ready = true;
        ShowAdapter();
    }

}
