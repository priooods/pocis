package com.kbs.pocis.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerDefault extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> namefragment = new ArrayList<>();

    public ViewpagerDefault(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return namefragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return namefragment.get(position);
    }

    public void Addfragment (Fragment fragment, String name){
        fragmentList.add(fragment);
        namefragment.add(name);
    }
}
