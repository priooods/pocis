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
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.CallProjectList;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.onlinebooking.CallingData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<Model_Project> filtered_list = new ArrayList<>();
    ViewpagerDefault viewpagerDefault;
    boolean freeGenerate = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dasar,container,false);

        search_icon = view.findViewById(R.id.btn_search_project_list);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, selectProj.list);
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
        max_list = 10;
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
        if (freeGenerate) {
            if (selectProj != null && selectProj.list != null) {
                //pmanager = selectProj.list.pmanager;
                filter = selectProj.list.filter;
                page_current = selectProj.list.page_current;
                selectProj.list.LoadingBar(true);
                filtered_list.clear();
            }
            super.GenerateLists();
            freeGenerate = false;
        }else{
            Log.e("project_list","can't do action, one Fragment on progress!");
        }
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
        final Project_Pack on_list = selectProj;
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
                        Log.i("project_list","finish call all.size = "+(all.data.model!=null?all.data.model.size():"null")+"."+all.data.current_page);
                        Log.i("project_list","finish call open.size = "+(open.data.model!=null?open.data.model.size():"null")+"."+open.data.current_page);
                        Log.i("project_list","finish call close.size = "+(close.data.model!=null?close.data.model.size():"null")+"."+close.data.current_page);
                        FinishFilter();
                        freeGenerate = true;
                    } else {
                        Log.e("project_list", "Project List unique Filter with 4 page limits");

                        //This for Filter. Do not Editing Function. Edit Only model;
                            Log.i("booking_load",  " page = " + page + " load = " + load);
                            int i = 0;
                            assert respone != null;
                        PublicList.Datas take;
                        if (all == on_list) {
                            take = respone.data.All;
                        } else if (open == on_list) {
                            take = respone.data.Open;
                        } else {
                            take = respone.data.Close;
                        }
                            for (Model_Project data : take.model) {
                                if (filter.checkFilter(data)) {
                                    if (pmanager.addPack(page, i) && load) {
                                        filtered_list.add(data);
                                    } else {
                                        load = false;
                                    }
                                }
                                i++;
                            }
                            if (page == take.last_page || page > 4 || !load) {
                                Log.i("finish_filter",(page == take.last_page)+" "+(page > 4)+" "+!load);
                                pmanager = null;
                                page_last = 1;
                                load = false;
                                take.last_page = 1;
                                take.current_page = 1;
                                take.total = take.per_page = filtered_list.size();
                                take.model = filtered_list;
                                if (all == on_list) {
                                    all.data = take;
                                } else if (open == on_list) {
                                    open.data = take;
                                } else {
                                    close.data = take;
                                }
                                freeGenerate = true;
                                FinishFilter();
                            } else {
                                GenerateFilter(page + 1, 0);
                            }
                        }
//                        int i = 0;
//                        assert respone != null;
//                        if (on_list == all) {
//                            all.data = new PublicList.Datas().setUpFilter(filter, respone.data.All);
//                        }else
//                        if (on_list == open) {
//                            open.data = new PublicList.Datas().setUpFilter(filter, respone.data.Open);
//                        }else
//                        if (on_list == close) {
//                            close.data = new PublicList.Datas().setUpFilter(filter, respone.data.Close);
//                        }
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
                freeGenerate = true;
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
    }
}
