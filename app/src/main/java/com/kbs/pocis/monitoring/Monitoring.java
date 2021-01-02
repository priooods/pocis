package com.kbs.pocis.monitoring;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbs.pocis.R;

public class Monitoring extends AppCompatActivity {

    ImageView icon_back;
    BottomNavigationView bottombar_monitoring;
    TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_dasar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        icon_back = findViewById(R.id.btn_back_monitoring);
        icon_back.setOnClickListener(v -> this.onBackPressed());
        bottombar_monitoring = findViewById(R.id.bottombar_monitoring);
        title = findViewById(R.id.title);

        bottombar_monitoring.setOnNavigationItemSelectedListener(listener);
        FragmentList(new Unloading());
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
                    FragmentList(selectFragment);
                    return true;
                }
            };

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framemonitoring, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }
}
