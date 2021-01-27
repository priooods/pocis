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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbs.pocis.R;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.myproject.Project_List_Dasar;
import com.kbs.pocis.myproject.Projects_Approved;
import com.kbs.pocis.myproject.Projects_Bpaj;

public class MyProject_Dasar extends AppCompatActivity {

    ImageView icon_back;
    BottomNavigationView bottombar_myprojects;
    FilterFragment filterFragment;
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
        icon_back = findViewById(R.id.btn_back_myproject);
        icon_back.setOnClickListener(v -> {
            if (filterFragment != null){
                filterFragment.filtering = false;
            }
            MyProject_Dasar.this.onBackPressed();
            finish();
        });

        bottombar_myprojects = findViewById(R.id.bottombar_myprojects);
        title = findViewById(R.id.titile);
        if (Model_Project.CheckMenuProject == 1){
            title.setText(R.string.list);
            bottombar_myprojects.setSelectedItemId(R.id.project_open);
            bottombar_myprojects.setOnNavigationItemSelectedListener(listener);
            FragmentList(new Project_List_Dasar());
        } else {
            bottombar_myprojects.setOnNavigationItemSelectedListener(listener);
            FragmentList(new Projects_Approved());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (filterFragment != null){
            filterFragment.filtering = false;
        }
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()) {
                        case R.id.project_aprove:
                            selectFragment = new Projects_Approved();
                            title.setText(R.string.approval);
                            break;
                        case R.id.project_open:
                            selectFragment =  new Project_List_Dasar();
                            title.setText(R.string.list);
                            break;
                        case R.id.bpaj_aprove:
                            selectFragment = new Projects_Bpaj();
                            title.setText(R.string.bapj);
                            break;
                    }
                    assert selectFragment != null;
                    FragmentList(selectFragment);
                    return true;
                }
            };

    public void FragmentList(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameMyProject, fragment,"framentTujuan").commit();
    }
}
