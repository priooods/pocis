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

public class Project_List_Dasar extends Fragment {

    ImageView search_icon;
    ViewPager viewPager;
    TabLayout tabLayout;
    DialogFragment fragment;
    PublicList.Datas open,close,all;
//    Projects_List open_list,close_list,all_list;
    Projects_List selectTemp;
    Projects_List[] projects_lists;
    ViewpagerDefault viewpagerDefault;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dasar,container,false);

        search_icon = view.findViewById(R.id.btn_search_project_list);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, selectTemp);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        viewpagerDefault = new ViewpagerDefault(getChildFragmentManager());
        tabLayout = view.findViewById(R.id.list_tablayout);
        viewPager = view.findViewById(R.id.list_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTemp = projects_lists[position];
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        projects_lists = new Projects_List[3];
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        viewpagerDefault.Addfragment(selectTemp = projects_lists[0] = new Projects_List(0),"Open");
        viewpagerDefault.Addfragment(projects_lists[1] = new Projects_List(1),"Close");
        viewpagerDefault.Addfragment(projects_lists[2] = new Projects_List(2),"All");
        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
