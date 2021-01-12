package com.kbs.pocis.filter;

import android.util.Log;
import androidx.fragment.app.Fragment;
import com.kbs.pocis.onlineboking.Filters;
import com.kbs.pocis.onlineboking.PageManager;
import com.kbs.pocis.service.UserData;

///Override GenerateFilter, ShowAdapter, Model_CheckorClear & LoadingBar
public class FilterFragment extends Fragment {
    public Filters filter;
    public int page_last = 0 , page_current = 1;
    public PageManager pmanager;
    public boolean Ready,load,filtering, Stop;
    public int max_list;

    protected void GenerateFilter(int page, int list){
        LoadingBar(true);
        Log.e("filter","FilterFragment.GenerateFilter not override well!");
    }
    protected void ShowAdapter(){
        Log.e("filter","FilterFragment.ShowAdapter not override well!");
    }
    protected void Model_CheckorClear(){
        Log.e("filter","FilterFragment.Model_CheckorClear not override well!");
    }
    protected void LoadingBar(boolean start){}

    public void GenerateLists() {
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
                pmanager.getCallPage(page_current);
                GenerateFilter(pmanager.getPage(), pmanager.getList());
            }
            return;
        }
        filtering = false;
        pmanager = null;
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
