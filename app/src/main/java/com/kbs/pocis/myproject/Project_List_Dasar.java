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
import androidx.fragment.app.Fragment;
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
    Project_Pack open,close,all;
    Project_Pack selectProj;
    class Project_Pack{
        Projects_List list;
        PublicList.Datas data;
    }
//    Projects_List[] projects_lists;
    ViewpagerDefault viewpagerDefault;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dasar,container,false);

        search_icon = view.findViewById(R.id.btn_search_project_list);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        viewpagerDefault = new ViewpagerDefault(getChildFragmentManager());
        tabLayout = view.findViewById(R.id.list_tablayout);
        viewPager = view.findViewById(R.id.list_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        selectProj = open;
                        break;
                    case 1:
                        selectProj = close;
                        break;
                    case 2:
                        selectProj = all;
                        break;
                }
                ShowAdapter();
            }
        });

//        projects_lists = new Projects_List[3];
        all = new Project_Pack();
        close = new Project_Pack();
        open = new Project_Pack();
        GenerateLists();
        viewpagerDefault.Addfragment(open.list = new Projects_List(0, this),"Open");
        viewpagerDefault.Addfragment(close.list = new Projects_List(1, this),"Close");
        viewpagerDefault.Addfragment(all.list = new Projects_List(2,this),"All");
        selectProj = open;
        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    @Override
    protected void Model_CheckorClear() {}

    @Override
    public void GenerateLists() {
        if (selectProj!=null && selectProj.list!=null){
            page_current = selectProj.list.page_current;
            selectProj.list.LoadingBar(true);
        }
        super.GenerateLists();
    }

    @Override
    protected void GenerateFilter(int page, int list) {
        Log.i("project_list", "Call ProjectList page = " + page);
        Call<CallProjectList> call;
        call = UserData.i.getService().getListProject(UserData.i.getToken(), String.valueOf(page));
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
                        if (all.list.page_current==respone.data.All.current_page) {
                            all.data = respone.data.All;
                        }
                        if (open.list.page_current==respone.data.Open.current_page) {
                            open.data = respone.data.Open;
                        }
                        if (close.list.page_current==respone.data.Close.current_page) {
                            close.data = respone.data.Close;
                        }
                        if (all.data==null || open.data==null || close.data == null){
                            Log.e("project_list","failed to load!");
                            ShowAdapter();
                            return;
                        }
                        Log.i("project_list","finish call all.size = "+all.data.model.size()+"."+all.data.current_page);
                        Log.i("project_list","finish call open.size = "+open.data.model.size()+"."+all.data.current_page);
                        Log.i("project_list","finish call close.size = "+close.data.model.size()+"."+all.data.current_page);
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
                        if (selectProj == all) {
                            all.data = new PublicList.Datas().setUpFilter(filter, respone.data.All);
                        }else
                        if (selectProj == open) {
                            open.data = new PublicList.Datas().setUpFilter(filter, respone.data.Open);
                        }else
                        if (selectProj == close) {
                            close.data = new PublicList.Datas().setUpFilter(filter, respone.data.Close);
                        }
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
        if (selectProj.data!=null) {
            Log.e("project_list","send to list ShowAdapter page="+selectProj.data.current_page);
            selectProj.list.ShowAdapter(selectProj.data);
        }else
            Log.e("project_list","Data was lost in Switch Page Action!");
//        close_list.ShowAdapter(close.model);
//        open_list.ShowAdapter(open.model);
    }
}
