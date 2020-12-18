package com.kbs.pocis.monitoring;

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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.myproject.Projects_List;

public class Unloading extends Fragment {

    ImageView search_icon;
    ViewPager viewPager;
    TabLayout tabLayout;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unloading,container,false);

        search_icon = view.findViewById(R.id.btn_search_unload);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFilter(getContext());
            }
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


    public void OpenFilter(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_filters_myproject, null);
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_nomerBook = view.findViewById(R.id.filter_project_nomerbooking);
        TextInputEditText input_vesel = view.findViewById(R.id.filter_project_veselname);
        TextInputEditText input_nomerProject = view.findViewById(R.id.filter_project_nomerproject);
        Button button_back = view.findViewById(R.id.btn_filter_cancel_project);
        Button button_next = view.findViewById(R.id.btn_filter_go_project);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
