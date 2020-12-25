package com.kbs.pocis.monitoring;

import android.os.Bundle;
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

public class Unloading extends FilterFragment {

    ImageView search_icon;
    ViewPager viewPager;
    TabLayout tabLayout;
    View view;
    DialogFragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unloading,container,false);

        search_icon = view.findViewById(R.id.btn_search_unload);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, Unloading.this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getChildFragmentManager());
        tabLayout = view.findViewById(R.id.monitoring_tablayout);
        viewPager = view.findViewById(R.id.monitoring_viewpager);
        viewpagerDefault.Addfragment(new Unloading_List(0),"Berthing");
        viewpagerDefault.Addfragment(new Unloading_List(1),"Planned");
        viewpagerDefault.Addfragment(new Unloading_List(2),"Departure");

        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
