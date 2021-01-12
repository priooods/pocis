package com.kbs.pocis.monitoring;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.adapter.detailMonitoring.Adapter_Monitoring_Detail;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Dasar extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    RelativeLayout ln_vessel, ln_progress,progress,notfound;
    TextView header1,header2,item_vesel1,item_vesel2, title2, title_topship,title_botship;
    TextView item_progres_code,item_info1,item_info2,item_info3,item_info4,item_info5,item_info6;
    List<Model_Project> model_monitorings;
    Adapter_Monitoring_Detail adapter_actual_truck_monitoring,adapter_contact_agent, adapter_topship,adapter_botship,
            adapter_contact_pbm,adapter_vessel_in_progress,adapter_summary;
    LinearLayoutManager manager_contact_agent,manager_contact_pbm, manager_actual_truck_monitoring,manager_summary,
            manager_vessel_in_progress, manager_topship,manager_botship;

    //for layout null data UI
    LinearLayout ln_cctv_no,ln_contact_no,ln_truck_no,ln_vessel_no,ln_summary_no;

    WebView content_cctv;
    LinearLayout ln_ship_all,ln_ship_all_no;
    //Progress
    ImageView icon_cctv, icon_contact,icon_truck,icon_operational,icon_vesel,icon_stowage,icon_sumary, icon_back;
    ExpandableLayout expand_cctv,expand_contact,expand_truck,expand_operational,expand_vesel,expand_stowage,expand_sumary;
    RecyclerView list_contact_agent,list_contact_pbm, list_actual_truck_monitoring, list_vessel_in_progress,list_sumary,
            list_topship,list_botship;

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

        //for UI No DATA
        //region
        ln_cctv_no = findViewById(R.id.ln_cctv_no);
        ln_contact_no = findViewById(R.id.ln_contact_no);
        ln_truck_no =findViewById(R.id.ln_truck_monitor_no);
        ln_vessel_no = findViewById(R.id.ln_actual_vesel_no);
        ln_summary_no = findViewById(R.id.ln_summary_no);
        //endregion






        ln_ship_all = findViewById(R.id.ln_ship_all);
        ln_ship_all_no = findViewById(R.id.ln_ship_all_no);
        list_topship =findViewById(R.id.list_top_view);
        title_topship = findViewById(R.id.title_name_top);
        title_botship = findViewById(R.id.title_name_bot);
        list_botship = findViewById(R.id.list_side_view);

        progress = findViewById(R.id.progress);
        notfound = findViewById(R.id.detail_kosong);
        icon_back = findViewById(R.id.btn_back);
        icon_back.setOnClickListener(v-> onBackPressed());
        item_progres_code = findViewById(R.id.item_p1);
        content_cctv = findViewById(R.id.content_cctv);

        item_info1 = findViewById(R.id.item_info1);
        item_info2 = findViewById(R.id.item_info2);
        item_info3 = findViewById(R.id.item_info3);
        item_info4 = findViewById(R.id.item_info4);
        item_info5 = findViewById(R.id.item_info5);
        item_info6 = findViewById(R.id.item_info6);

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

        list_contact_agent = findViewById(R.id.list_contact_agent);
        list_contact_pbm = findViewById(R.id.list_contact_pbm);
        list_actual_truck_monitoring = findViewById(R.id.list_actual);
//        list_opertional = findViewById(R.id.list_operational);
        list_vessel_in_progress = findViewById(R.id.list_actual_vessel);
//        list_stowage = findViewById(R.id.list_stowage);
        list_sumary = findViewById(R.id.list_sumary);

        // forr Vessel
        title2 = findViewById(R.id.title_top2);
        tabLayout = findViewById(R.id.tab_vessel);
        viewPager = findViewById(R.id.viewpager);
        header1 = findViewById(R.id.title);
        header2 = findViewById(R.id.title2);
        item_vesel1 = findViewById(R.id.item_top1);
        item_vesel2 = findViewById(R.id.item_top2);
        ln_vessel = findViewById(R.id.ln_vesel);

        check();

    }

    public void check(){
        Model_Project data = Model_Project.mp;
        switch (Model_Project.Code){
            case 5: // detail loading/unloading
                ln_progress.setVisibility(View.VISIBLE);
                header1.setText(R.string.header1_progress);
                header2.setText(R.string.header2_progress);
                item_vesel1.setText(data.vessel_name);
                title2.setText(R.string.project_status);
                item_vesel2.setText(data.plan_status);
                item_progres_code.setText(data.schedule_code);
                item_info1.setText(data.voyage_no);
                item_info2.setText(data.jetty_name);
                item_info3.setText(data.est_berthing);
                item_info4.setText(data.act_berthing);
                item_info5.setText(data.est_departure);
                item_info6.setText(data.act_departure);
                clickExpand();
                getDetailUnloading();
                statusColor();
                break;
            case 6: // detail for vessel schedule
                progress.setVisibility(View.VISIBLE);
                getDetailVesselSchedule();
                ln_vessel.setVisibility(View.VISIBLE);
                header1.setText(R.string.header1_vessel);
                header2.setText(R.string.header2_vessel);
                item_vesel1.setText(data.vessel_name);
                break;
        }

    }

    //Calling API
    public void getDetailVesselSchedule(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().detailsVessel(UserData.i.getToken(), data.t_vessel_schedule_id, data.voyage_no);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(Detail_Dasar.this,"detail_monitor", data)){
                    assert data!=null;
                    item_vesel2.setText(data.data.Header.get(0).no_booking);
                    Model_Project.Header = data.data.Header;
                    Model_Project.mp = data.data.AllTabsData.data;
                    Log.i("detail_monitor", "onResponse: " + data.data.Header);
                    progress.setVisibility(View.GONE);
                    ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(0),"Jetty");
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(1),"Vessel");
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(2),"Berth");
                    viewpagerDefault.Addfragment(new List_Detail_Vessel(3),"Time Schedule");
                    viewPager.setAdapter(viewpagerDefault);
                    tabLayout.setupWithViewPager(viewPager);
                } else {
                    progress.setVisibility(View.GONE);
                    notfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("detail_monitor", "onFailure: ", t);
            }
        });
    }

    public void getDetailUnloading(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().detailUnloading(UserData.i.getToken(), data.t_vessel_schedule_id, data.voyage_no);
        call.enqueue(new Callback<CallingDetail>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(Detail_Dasar.this,"detail_monitor", data)){
                    assert data!=null;
                    Model_Project.ContactAgent = data.data.ContactAgent;
                    Model_Project.ContactPbm = data.data.ContactPbm;
                    Model_Project.ActualTruckMonitoring = data.data.ActualTruckMonitoring;
                    Model_Project.ItemSummary = data.data.ItemSummary;
                    Model_Project.ActualVesselInProgress = data.data.ActualVesselInProgress;
                    Model_Project.HatchTotal = data.data.ActualStowageMonitoring.HatchTotal;
                    Model_Project.HatchDetails = data.data.ActualStowageMonitoring.HatchDetails;
                    Model_Project.HeaderAndCCTV = data.data.HeaderAndCCTV;
                    Log.i("detail_monitor", "cctv: " + Model_Project.HeaderAndCCTV.get(0).link_cctv);
                    Log.i("detail_monitor", "contact Agent: " + Model_Project.ContactAgent.size());
                    Log.i("detail_monitor", "contact Pbm: " + Model_Project.ContactPbm.size());
                    Log.i("detail_monitor", "Summary: " + Model_Project.ItemSummary.size());
                    Log.i("detail_monitor", "ActualVesselInProgress: " + Model_Project.ActualVesselInProgress.size());
                    Log.i("detail_monitor", "HatchTotal: " + Model_Project.HatchTotal.size());
                    Log.i("detail_monitor", "HatchDetails: " + Model_Project.HatchDetails.size());
                    Log.i("detail_monitor", "ActualTruckMonitoring: " + Model_Project.ActualTruckMonitoring.size());

//                    //for CCTV Logic
                    if (Model_Project.HeaderAndCCTV.get(0).link_cctv != null){
                        showCCTV(Model_Project.HeaderAndCCTV.get(0).link_cctv);
                    } else {
                        content_cctv.setVisibility(View.GONE);
                        ln_cctv_no.setVisibility(View.VISIBLE);
                    }

                    // For Contact
                    if (Model_Project.ContactAgent.size() > 0){
                        listContactAgent();
                    } else {
                        list_contact_agent.setVisibility(View.GONE);
                    }
                    if (Model_Project.ContactPbm.size() > 0){
                        listContactPbm();
                    } else {
                        list_contact_pbm.setVisibility(View.GONE);
                    }

                    //For Truck
                    if (Model_Project.ActualTruckMonitoring.size() > 0){
                        listActualTruckMonitoring();
                    } else {
                        list_actual_truck_monitoring.setVisibility(View.GONE);
                        ln_truck_no.setVisibility(View.VISIBLE);
                    }

                    if (Model_Project.ActualVesselInProgress.size() > 0){
                        listVesselInProgress();
                    } else {
                        list_vessel_in_progress.setVisibility(View.GONE);
                        ln_vessel_no.setVisibility(View.VISIBLE);
                    }


//                    //for Ship Logic
                    if (Model_Project.HatchDetails.size() > 0){
                        Log.i("detail_monitor", "desc: " + Model_Project.HatchDetails.get(0).description);
                        title_botship.setText(Model_Project.HatchDetails.get(0).description);
                        listStowageMonitoring();
                        if (Model_Project.HatchTotal.size() > 0){
                            Log.i("detail_monitor", "hatch_total side: " + Model_Project.HatchTotal.get(0).hatch_side);
                            title_topship.setText(Model_Project.HatchTotal.get(0).hatch_side);
                        }
                    } else {
                        ln_ship_all.setVisibility(View.GONE);
                        ln_ship_all_no.setVisibility(View.VISIBLE);
                    }

                    if (Model_Project.ItemSummary.size() > 0) {
                        listItemSummary();
                    } else {
                        list_sumary.setVisibility(View.GONE);
                        ln_summary_no.setVisibility(View.VISIBLE);
                    }
                    progress.setVisibility(View.GONE);
                } else {
                    progress.setVisibility(View.GONE);
                    notfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("detail_monitor", "onFailure: ", t);
            }
        });
    }



    //Setting List With Expanded Animation
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showCCTV(String link){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            content_cctv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            content_cctv.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.e("error_view", "onReceivedError: " + "error_desc = " + description + "failure url = " + failingUrl);
                    Toasty.error(Detail_Dasar.this, "Your Connection Failure or " + description, Toasty.LENGTH_SHORT, true).show();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onLoadResource(WebView view, String url) {
//                    content_cctv.setVisibility(View.GONE);
                    content_cctv.setEnabled(false);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
//                    content_cctv.setVisibility(View.VISIBLE);
                    content_cctv.setEnabled(true);
                }
            });
            content_cctv.getSettings().setJavaScriptEnabled(true);
            content_cctv.getSettings().setLoadWithOverviewMode(true);
            content_cctv.getSettings().setUseWideViewPort(true);
            content_cctv.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.117 Safari/537.36");
            content_cctv.loadUrl(link);
        }
    }


    private void listContactAgent(){
        model_monitorings = new ArrayList<>();
        model_monitorings.addAll(Model_Project.ContactAgent);
        adapter_contact_agent = new Adapter_Monitoring_Detail(model_monitorings,this,0);
        manager_contact_agent = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_contact_agent.setLayoutManager(manager_contact_agent);
        list_contact_agent.setAdapter(adapter_contact_agent);
    }

    private void listContactPbm(){
        model_monitorings = new ArrayList<>();
        model_monitorings.addAll(Model_Project.ContactPbm);
        adapter_contact_pbm = new Adapter_Monitoring_Detail(model_monitorings,this,1);
        manager_contact_pbm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_contact_pbm.setLayoutManager(manager_contact_pbm);
        list_contact_pbm.setAdapter(adapter_contact_pbm);
    }

    private void listActualTruckMonitoring(){
        model_monitorings = new ArrayList<>();
        model_monitorings.addAll(Model_Project.ActualTruckMonitoring);
        adapter_actual_truck_monitoring = new Adapter_Monitoring_Detail(model_monitorings,this,3);
        manager_actual_truck_monitoring = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_actual_truck_monitoring.setLayoutManager(manager_actual_truck_monitoring);
        list_actual_truck_monitoring.setAdapter(adapter_actual_truck_monitoring);
    }

    private void listItemSummary(){
        model_monitorings = new ArrayList<>();
        model_monitorings.addAll(Model_Project.ItemSummary);
        adapter_summary = new Adapter_Monitoring_Detail(model_monitorings,this,5);
        manager_summary = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_sumary.setLayoutManager(manager_summary);
        list_sumary.setAdapter(adapter_summary);
    }

    private void listVesselInProgress(){
        model_monitorings = new ArrayList<>();
        model_monitorings.addAll(Model_Project.ActualVesselInProgress);
        adapter_vessel_in_progress = new Adapter_Monitoring_Detail(model_monitorings,this,4);
        manager_vessel_in_progress = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_vessel_in_progress.setLayoutManager(manager_vessel_in_progress);
        list_vessel_in_progress.setAdapter(adapter_vessel_in_progress);
    }

    private void listStowageMonitoring(){
        model_monitorings = new ArrayList<>();
        model_monitorings.addAll(Model_Project.HatchDetails);

        //for top
        adapter_topship = new Adapter_Monitoring_Detail(model_monitorings,this,6);
        manager_topship = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        list_topship.setLayoutManager(manager_topship);
        list_topship.setAdapter(adapter_topship);
        //for bot
        adapter_botship = new Adapter_Monitoring_Detail(model_monitorings,this,6);
        manager_botship = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        list_botship.setLayoutManager(manager_botship);
        list_botship.setAdapter(adapter_botship);
    }

    private void statusColor(){
        switch (item_vesel2.getText().toString()){
            case "Planned":
                item_vesel2.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
            case "Not Planned":
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
