package com.kbs.pocis.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbs.pocis.R;
import com.kbs.pocis.filter.Filter_MyProject;
import com.kbs.pocis.myproject.Projects_Approved;
import com.kbs.pocis.myproject.Projects_Bpaj;
import com.kbs.pocis.myproject.Projects_Open;
import com.kbs.pocis.myproject.Projects_Tariff_Info;

public class MyProject_Dasar extends AppCompatActivity {

    ImageView icon_back, icon_search;
    BottomNavigationView bottombar_myprojects;
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myproject_dasar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        icon_back = findViewById(R.id.btn_back_my_project);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProject_Dasar.this.onBackPressed();
                finish();
            }
        });

        icon_search = findViewById(R.id.btn_search_myproject);
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new Filter_MyProject();
                fragment.show(getSupportFragmentManager(),"Filter My project");
            }
        });
        bottombar_myprojects = findViewById(R.id.bottombar_myprojects);
        title = findViewById(R.id.titile);
        bottombar_myprojects.setOnNavigationItemSelectedListener(listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameMyProject, new Projects_Approved()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()){
                        case R.id.project_aprove:
                            selectFragment = new Projects_Approved();
                            title.setText("Projects Approved");
                            break;
                        case R.id.project_open:
                            selectFragment = new Projects_Open();
                            title.setText("Projects Open");
                            break;
                        case R.id.tarif_info:
                            selectFragment = new Projects_Tariff_Info();
                            title.setText("Tariff Info");
                            break;
                        case R.id.bpaj_aprove:
                            selectFragment = new Projects_Bpaj();
                            title.setText("BPAJ Approval");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameMyProject, selectFragment).commit();
                    return true;
                }
            };
}
