package com.kbs.pocis.monitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbs.pocis.R;

public class Monitoring extends Fragment {

    ImageView icon_back;
    BottomNavigationView bottombar_monitoring;
    TextView title;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.monitoring_dasar, container, false);

        icon_back = view.findViewById(R.id.btn_back_monitoring);
        icon_back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        bottombar_monitoring = view.findViewById(R.id.bottombar_monitoring);
        title = view.findViewById(R.id.title);

        bottombar_monitoring.setOnNavigationItemSelectedListener(listener);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framemonitoring, new Unloading()).commit();

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()){
                        case R.id.unload:
                            selectFragment = new Unloading();
                            title.setText(R.string.loading);
                            break;
                        case R.id.vesel:
                            selectFragment = new Vessel_Schedule();
                            title.setText(R.string.vessel_schedule);
                            break;
                    }
                    assert selectFragment != null;
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framemonitoring, selectFragment).commit();
                    return true;
                }
            };
}
