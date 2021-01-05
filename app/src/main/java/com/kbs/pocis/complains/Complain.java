package com.kbs.pocis.complains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;

public class Complain extends FilterFragment {

    ImageView search_icon;
    ViewPager viewPager;
    TabLayout tabLayout;
//    DialogFragment fragment;
    ImageView icon_back;
    ImageView new_complain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complain,container,false);

        new_complain = view.findViewById(R.id.new_complain);
        icon_back = view.findViewById(R.id.btn_back_complain);
        icon_back.setOnClickListener(v -> requireActivity().onBackPressed());

        new_complain.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new New_Complain();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framecomplain, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        search_icon = view.findViewById(R.id.btn_search_complain);
        search_icon.setVisibility(View.GONE);
        search_icon.setOnClickListener(v -> {
//            fragment = new Dialog_Filter(true, Complain.this);
//            fragment.show(getChildFragmentManager(), "filter_online");
            Toast.makeText(getContext(), "Complaint Search process developing", Toast.LENGTH_SHORT).show();
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
