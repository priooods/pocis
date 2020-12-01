package com.kbs.pocis.myproject.detail;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    //Top untuk detail Project Approved
    TextView booking_No, schedule_code, temp_info_aprroved;

    //Top untuk detail Project List
    TextView temp_info_projectList, dated_issue, ppj_number;

    //textview lainnya
    TextView formPage, status;

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView btn_back;

    String get_form, get_booking_no, getstatus, get_schedule, get_temp_proj, get_date_issue, get_ppj_number;

    RelativeLayout formTopAprrove, formTopProjectList;
    LinearLayout top_kiri_approved, top_kiri_projectList;
    int codeTemplate, id_list;

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

        codeTemplate = getIntent().getIntExtra("get", 0);
        getstatus = getIntent().getStringExtra("status");
        get_form = getIntent().getStringExtra("form");
        get_temp_proj = getIntent().getStringExtra("temp_project");
        id_list = getIntent().getIntExtra("id",1);

        //Kondisi layout GONE and VISIBLE di detail project
        top_kiri_approved = findViewById(R.id.lay_top_kiri_proejctApproved);
        top_kiri_projectList = findViewById(R.id.lay_top_kiri_projectList);
        formTopProjectList = findViewById(R.id.lay_top_detail_info_projectlist);
        formTopAprrove = findViewById(R.id.lay_top_detail_info_aproved);

        //Info Top di detail project untuk project aprroveed
        booking_No = findViewById(R.id.detail_myproject_bookingNo);
        schedule_code = findViewById(R.id.detail_myproject_info_schedule_approved);
        temp_info_aprroved = findViewById(R.id.detail_myproject_info_tempProj_aproved);
        temp_info_aprroved.setText(get_temp_proj);

        //Info Top di detail project untuk project List
        temp_info_projectList = findViewById(R.id.detail_myproject_TempNo);
        temp_info_projectList.setText(get_temp_proj);
        dated_issue = findViewById(R.id.detail_myproject_info_dateissue_projectList);
        ppj_number = findViewById(R.id.detail_myproject_info_ppjnumber_projectList);

        //Lainnya
        formPage = findViewById(R.id.project_details_Textfrompage);
        formPage.setText(get_form);
        status = findViewById(R.id.detail_myproject_projectstatus);
        status.setText(getstatus);

        /* Setting Status di detail */
        if (status.getText().toString().equals("PREPARED")){
            status.setTextColor(Color.parseColor("#00a1d1"));
        } else if (status.getText().toString().equals("OPEN")){
            status.setTextColor(Color.parseColor("#4BA459"));
        }


        if (codeTemplate == 1){
            get_booking_no = getIntent().getStringExtra("boking_no");
            get_schedule = getIntent().getStringExtra("schedule");
            booking_No.setText(get_booking_no);
            schedule_code.setText(get_schedule);
        } else if (codeTemplate == 2){
            top_kiri_approved.setVisibility(View.GONE);
            top_kiri_projectList.setVisibility(View.VISIBLE);
            formTopAprrove.setVisibility(View.GONE);
            formTopProjectList.setVisibility(View.VISIBLE);
            get_date_issue = getIntent().getStringExtra("date_isue");
            get_ppj_number = getIntent().getStringExtra("ppj");
            dated_issue.setText(get_date_issue);
            ppj_number.setText(get_ppj_number);
        }


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
