package com.kbs.pocis.myproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.CallProjectList;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Project_List_Dasar extends FilterFragment {

    ImageView search_icon;
    ViewPager viewPager;
    TabLayout tabLayout;
    DialogFragment fragment;
    PublicList.Datas open,close,all;
    Projects_List open_list,close_list,all_list;
    ViewpagerDefault viewpagerDefault;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dasar,container,false);

        search_icon = view.findViewById(R.id.btn_search_project_list);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, Project_List_Dasar.this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        viewpagerDefault = new ViewpagerDefault(getChildFragmentManager());
        tabLayout = view.findViewById(R.id.list_tablayout);
        viewPager = view.findViewById(R.id.list_viewpager);

        max_list = 5;
        GenerateLists();
        viewpagerDefault.Addfragment(open_list = new Projects_List(0),"Open");
        viewpagerDefault.Addfragment(close_list = new Projects_List(1),"Closed");
        viewpagerDefault.Addfragment(all_list = new Projects_List(2),"All Projects");
        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    protected void Model_CheckorClear() {
        all = null;
        close = null;
        open = null;
    }

    @Override
    protected void GenerateFilter(int page, int list) {
        Log.i("project_list", "Call ProjectList page = " + page);
        Call<CallProjectList> call;
        call = UserData.i.getService().getListProject(UserData.i.getToken());//, String.valueOf(page));
        if (call == null) {
            Log.i("project_list", "CallProjectList Post Method is Bad!");
        }
        assert call != null;
        call.enqueue(new Callback<CallProjectList>() {
            @Override
            public void onResponse(@NotNull Call<CallProjectList> call, @NotNull Response<CallProjectList> response) {
                CallProjectList respone = response.body();
                if (Calling.TreatResponse(getContext(), "project_list", respone)) {
                    if (!filtering) {
                        assert respone != null;
                        all = respone.data.All;
                        open = respone.data.Open;
                        close = respone.data.Close;
                        if (all==null || open==null || close == null){
                            Log.e("project_list","failed to load!");
                            return;
                        }
                        Log.i("project_list","finish call all.size = "+all.model.size());
                        Log.i("project_list","finish call open.size = "+open.model.size());
                        Log.i("project_list","finish call close.size = "+close.model.size());
                        FinishFilter();
                    } else {
                        Log.e("project_list", "Project List API doesn't support page request! just filter in the page 1");
                        //This for Filter. Do not Editing Function. Edit Only model;
//                        if (pmanager.loaded) {
//                            Log.i("project_list_load"," page = " + page);
//                            assert respone != null;
//                            PublicList.Datas data = respone.data.All.data.;
//                            for (int i = list; i < data.length; i++) {
//                                if (filter.checkFilter(data[i])) {
//                                    if (respone.data.All.data.size() < pmanager.page_capacity) {
//                                        all.add(data[i].getModel());
//                                        Log.i("project_list_load", data[i].readString());
//                                    } else {
//                                        page_last = pmanager.page_last;
//                                        total_item = pmanager.total;
//                                        load = false;
//                                        FinishFilter();
//                                        return;
//                                    }
//                                }
//                            }
//                            if (page < respone.data.last_page) {
//                                GenerateFilter(page + 1, 0);
//                            } else {
//                                page_last = pmanager.page_last;
//                                total_item = pmanager.total;
//                                load = false;
//                                FinishFilter();
//                            }
//                        }
//                        else {
//                        Log.i("project_list_load", " page = " + page + " load = " + load);
                        int i = 0;
                        assert respone != null;
                        all = new PublicList.Datas().setUpFilter(filter, respone.data.All.model);
                        open = new PublicList.Datas().setUpFilter(filter, respone.data.Open.model);
                        close = new PublicList.Datas().setUpFilter(filter, respone.data.Close.model);
//                            if (page == respone.data.last_page) {
//                                if (pmanager.pack > 0) {
//                                    pmanager.finalPack(page, i - 1);
//                                }
//                                pmanager.finishLoad();
//                                page_last = pmanager.page_last;
//                                total_item = pmanager.total;
//                                layout_ada.setVisibility(View.VISIBLE);
//                                progressBar.setVisibility(View.GONE);
                        load = false;
                        FinishFilter();
//                            }
//                            else {
//                                GenerateFilter(page + 1, 0);
//                            }
//                        }
                        pmanager = null;
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
                Ready = true;
                Log.e("all_boking", "on Failure called!" + t);
            }
        });
        try {
            Thread.sleep(100, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void ShowAdapter() {
        all_list.RefreshData(all.model);
        close_list.RefreshData(close.model);
        open_list.RefreshData(open.model);
    }
}
