package com.kbs.pocis.myproject.detail;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.detailboking.BookingDetails_Commodity;
import com.kbs.pocis.detailboking.BookingDetails_Information;
import com.kbs.pocis.detailboking.BookingDetails_Service;

public class Detail_MyProject extends AppCompatActivity {

    TextView  tempNo, date_issue;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView btn_back;
    String temp, date_isu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myproject_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        temp = getIntent().getStringExtra("id");
        date_isu = getIntent().getStringExtra("date");


        tempNo = findViewById(R.id.project_details_TempNo);
        date_issue = findViewById(R.id.project_details_date_issue);
        tempNo.setText(temp);
        date_issue.setText(date_isu);
        tabLayout = findViewById(R.id.project_details_tablayout);
        viewPager = findViewById(R.id.project_details_viewpager);
        btn_back = findViewById(R.id.project_details_btn_back);

        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
        viewpagerDefault.Addfragment(new Project_Information(),"Information");
        viewpagerDefault.Addfragment(new Project_Service(),"Service");
        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
