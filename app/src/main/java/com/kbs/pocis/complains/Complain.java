package com.kbs.pocis.complains;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.monitoring.Unloading_List;
import com.kbs.pocis.myproject.Projects_Bpaj;

public class Complain extends FilterFragment {

    View view;
    ImageView search_icon;
    ViewPager viewPager;
    TabLayout tabLayout;
    DialogFragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.complain,container,false);

        search_icon = view.findViewById(R.id.btn_search_complain);
        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true, Complain.this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });

        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getChildFragmentManager());
        tabLayout = view.findViewById(R.id.complain_tablayout);
        viewPager = view.findViewById(R.id.complain_viewpager);
        viewpagerDefault.Addfragment(new Complain_List(0),"Open");
        viewpagerDefault.Addfragment(new Complain_List(1),"Closed");

        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

}
