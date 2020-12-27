package com.kbs.pocis.monitoring;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.adapter.detailMonitoring.Adapter_Monitoring_Detail;
import com.kbs.pocis.model.Model_Monitoring;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class Detail_Dasar extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    RelativeLayout ln_vessel, ln_progress;
    TextView header1,header2,item_vesel1,item_vesel2, title2;
    List<Model_Monitoring> model_monitorings;
    Adapter_Monitoring_Detail adapter_truck,adapter_contact,adapter_operational,adapter_summary;
    LinearLayoutManager manager_contact, manager_truck,manager_operational,manager_summary;

    //Progress
    ImageView icon_cctv, icon_contact,icon_truck,icon_operational,icon_vesel,icon_stowage,icon_sumary, icon_back;
    ExpandableLayout expand_cctv,expand_contact,expand_truck,expand_operational,expand_vesel,expand_stowage,expand_sumary;
    RecyclerView list_contact, list_truck, list_opertional,list_stowage,list_sumary;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_dasar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        icon_back = findViewById(R.id.btn_back);
        icon_back.setOnClickListener(v-> onBackPressed());

        //Progress item
        ln_progress = findViewById(R.id.ln_progress);
        expand_cctv = findViewById(R.id.expand_cctv);
        expand_contact = findViewById(R.id.expand_contact);
        expand_truck = findViewById(R.id.expand_actual);
        expand_operational = findViewById(R.id.expand_operational);
        expand_vesel = findViewById(R.id.expand_actual_vesel);
        expand_stowage = findViewById(R.id.expand_stowage);
        expand_sumary = findViewById(R.id.expand_sumary);
        ln_vessel = findViewById(R.id.ln_vesel);

        icon_cctv = findViewById(R.id.icon_cctv);
        icon_contact = findViewById(R.id.icon_contact);
        icon_truck = findViewById(R.id.icon_actual);
        icon_operational = findViewById(R.id.icon_operational);
        icon_vesel = findViewById(R.id.icon_actual_vesel);
        icon_stowage = findViewById(R.id.icon_stowage);
        icon_sumary = findViewById(R.id.icon_sumary);

        list_contact = findViewById(R.id.list_contact);
        list_truck = findViewById(R.id.list_actual);
        list_opertional = findViewById(R.id.list_operational);
        list_stowage = findViewById(R.id.list_stowage);
        list_sumary = findViewById(R.id.list_sumary);

        clickExpand();

        // forr Vessel
        title2 = findViewById(R.id.title_top2);
        tabLayout = findViewById(R.id.tab_vessel);
        viewPager = findViewById(R.id.viewpager);
        header1 = findViewById(R.id.title);
        header2 = findViewById(R.id.title2);
        item_vesel1 = findViewById(R.id.item_top1);
        item_vesel2 = findViewById(R.id.item_top2);
        ln_vessel = findViewById(R.id.ln_vesel);
        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());

        if (Model_Monitoring.isExist()){
            Model_Monitoring data = Model_Monitoring.mn;
            switch (Model_Monitoring.Code){
                case 0:
                    ln_progress.setVisibility(View.VISIBLE);
                    header1.setText(R.string.header1_progress);
                    header2.setText(R.string.header2_progress);
                    item_vesel1.setText(data.name);
                    title2.setText(R.string.project_status);
                    item_vesel2.setText(data.status);
                    statusColor();
                    ListExpand();
                    listStowage();
                    break;
                case 1: // detail for vessel schedule
                    ln_vessel.setVisibility(View.VISIBLE);
                    header1.setText(R.string.header1_vessel);
                    header2.setText(R.string.header2_vessel);
                    item_vesel1.setText(data.name);
                    item_vesel2.setText(data.status);
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(0),"Jetty");
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(1),"Vessel");
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(2),"Berth");
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(3),"Time Schedule");
                    viewPager.setAdapter(viewpagerDefault);
                    tabLayout.setupWithViewPager(viewPager);
                    break;
            }
        }

    }

    private void ListExpand(){
        model_monitorings = new ArrayList<>();
        model_monitorings.add(new Model_Monitoring("HUDRENI","rendysyah75@gmail.com","087774442056","PELAYARAN SAMUDERA KARANA LINE PT.","AGENT","B9773YU","1","21 Dec 2020 05:30","POS 5 IN","36","18","939.83"));
        model_monitorings.add(new Model_Monitoring("HUMEDI","msb.stev@gmail.com","081380993773","MULTI SENTANA BAJA PT","PBM","B9570YU","2","29 Dec 2020 05:28","POS 5 OUT","125","18","3,220.95"));
        model_monitorings.add(new Model_Monitoring("HUDRENI","rendysyah75@gmail.com","081380993773","PELAYARAN SAMUDERA KARANA LINE PT.","PBM","B9352YU","3","21 Dec 2020 05:23","POS 5 IN","78","18","3,220.95"));

        adapter_contact = new Adapter_Monitoring_Detail(model_monitorings,this,0);
        adapter_operational = new Adapter_Monitoring_Detail(model_monitorings,this,2);
        adapter_summary = new Adapter_Monitoring_Detail(model_monitorings,this,4);
        adapter_truck = new Adapter_Monitoring_Detail(model_monitorings,this,1);
        manager_contact = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        manager_truck = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        manager_operational = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        manager_summary = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        list_contact.setLayoutManager(manager_contact);
        list_contact.setAdapter(adapter_contact);
        list_truck.setLayoutManager(manager_truck);
        list_truck.setAdapter(adapter_truck);
        list_opertional.setLayoutManager(manager_operational);
        list_opertional.setAdapter(adapter_operational);
        list_sumary.setLayoutManager(manager_summary);
        list_sumary.setAdapter(adapter_summary);
    }


    private void listStowage(){
        model_monitorings = new ArrayList<>();
        model_monitorings.add(new Model_Monitoring("HUDRENI","rendysyah75@gmail.com","087774442056","PELAYARAN SAMUDERA KARANA LINE PT.","AGENT","B9773YU","1","21 Dec 2020 05:30","POS 5 IN","36","18","939.83"));
        Adapter_Monitoring_Detail adapter_project_list = new Adapter_Monitoring_Detail(model_monitorings,this,3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_stowage.setLayoutManager(layoutManager);
        list_stowage.setAdapter(adapter_project_list);
    }

    private void statusColor(){
        switch (item_vesel2.getText().toString()){
            case "PLANNED":
                item_vesel2.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
            case "NOT PLANNED":
                item_vesel2.setTextColor(getResources().getColor(R.color.colorRed));
                break;
        }
    }

    private void clickExpand(){
        icon_cctv.setOnClickListener(v -> {
            if (expand_cctv.isExpanded()){
                expand_cctv.collapse(true);
                icon_cctv.setImageResource(R.drawable.icon_botom);
            } else {
                expand_cctv.expand();
                icon_cctv.setImageResource(R.drawable.icon_top);
            }
        });

        icon_contact.setOnClickListener(v -> {
            if (expand_contact.isExpanded()){
                expand_contact.collapse(true);
                icon_contact.setImageResource(R.drawable.icon_botom);
            } else {
                expand_contact.expand();
                icon_contact.setImageResource(R.drawable.icon_top);
            }
        });

        icon_operational.setOnClickListener(v -> {
            if (expand_operational.isExpanded()){
                expand_operational.collapse(true);
                icon_operational.setImageResource(R.drawable.icon_botom);
            } else {
                expand_operational.expand();
                icon_operational.setImageResource(R.drawable.icon_top);
            }
        });

        icon_stowage.setOnClickListener(v -> {
            if (expand_stowage.isExpanded()){
                expand_stowage.collapse(true);
                icon_stowage.setImageResource(R.drawable.icon_botom);
            } else {
                expand_stowage.expand();
                icon_stowage.setImageResource(R.drawable.icon_top);
            }
        });

        icon_sumary.setOnClickListener(v -> {
            if (expand_sumary.isExpanded()){
                expand_sumary.collapse(true);
                icon_sumary.setImageResource(R.drawable.icon_botom);
            } else {
                expand_sumary.expand();
                icon_sumary.setImageResource(R.drawable.icon_top);
            }
        });

        icon_truck.setOnClickListener(v -> {
            if (expand_truck.isExpanded()){
                expand_truck.collapse(true);
                icon_truck.setImageResource(R.drawable.icon_botom);
            } else {
                expand_truck.expand();
                icon_truck.setImageResource(R.drawable.icon_top);
            }
        });

        icon_vesel.setOnClickListener(v -> {
            if (expand_vesel.isExpanded()){
                expand_vesel.collapse(true);
                icon_vesel.setImageResource(R.drawable.icon_botom);
            } else {
                expand_vesel.expand();
                icon_vesel.setImageResource(R.drawable.icon_top);
            }
        });
    }
}
