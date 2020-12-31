package com.kbs.pocis.myproject;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.myproject.detail.Documents;
import com.kbs.pocis.myproject.detail.Informations;
import com.kbs.pocis.myproject.detail.Services;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_MyProject extends AppCompatActivity {

    LinearLayout ln_3,ln_4,ln_5, ln_2;

    //textview lainnya
    TextView booking_No, status, titlePage, subtile, title_top1,title_top2,title_sub3,title_sub4,title_sub5,
            title_sub1,title_sub2, item_sub1, item_sub2,item_sub3, item_sub4,item_sub5;

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView btn_back;
    RelativeLayout ln_sub0, progress,notfound;


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
        progress = findViewById(R.id.progress);
        notfound = findViewById(R.id.detail_kosong);
        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
        tabLayout = findViewById(R.id.project_details_tablayout);
        viewPager = findViewById(R.id.project_details_viewpager);
        btn_back = findViewById(R.id.project_details_btn_back);
        ln_2 = findViewById(R.id.ln_sub2);
        ln_3 = findViewById(R.id.ln_sub3);
        ln_4 = findViewById(R.id.ln_sub4);
        ln_5 = findViewById(R.id.ln_sub5);
        ln_sub0 = findViewById(R.id.ln_sub0);

        //public TextView
        title_top1 = findViewById(R.id.title_top1);
        title_top2 = findViewById(R.id.title_top2);
        title_sub1 = findViewById(R.id.title_sub1);
        title_sub2 = findViewById(R.id.title_sub2);
        title_sub3 = findViewById(R.id.title_sub3);
        title_sub4 = findViewById(R.id.title_sub4);
        title_sub5 = findViewById(R.id.title_sub5);
        item_sub1 = findViewById(R.id.item_sub1);
        item_sub2 = findViewById(R.id.item_sub2);
        item_sub3 = findViewById(R.id.item_sub3);
        item_sub4 = findViewById(R.id.item_sub4);
        item_sub5 = findViewById(R.id.item_sub5);
        titlePage = findViewById(R.id.title_detail);
        subtile = findViewById(R.id.forPage);
        status = findViewById(R.id.item_top2);
        booking_No = findViewById(R.id.item_top1);

        if (Model_Project.isExist()){
            Model_Project data = Model_Project.mp;
            switch (Model_Project.Code){
                case 0:
                    progress.setVisibility(View.VISIBLE);
                    CallingDetailApproval();
                    titlePage.setText(R.string.project_aprov_detail);
                    subtile.setText(R.string.project_aprov);
                    booking_No.setText(data.no_booking);
                    status.setText(data.status_project);
                    item_sub1.setText(data.temp_project_no);
                    Log.i("detail", "onCreate: approval => " + data.temp_project_no);
                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    break;
                case 1:
                    progress.setVisibility(View.VISIBLE);
                    CallingDetailList();
                    titlePage.setText(R.string.project_list_detail);
                    subtile.setText(R.string.project_list);
                    title_top1.setText(R.string.temp_proj);
                    booking_No.setText(data.temp_project_no);
                    status.setText(data.status_project);
                    title_sub1.setText(R.string.date_isue);
                    title_sub2.setText(R.string.ppjno);
                    item_sub1.setText(data.date_project_issued);
                    item_sub2.setText(data.ppj_no);
                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (position == 0){
                                status.setText(data.status_project);
                                if (status.getText().toString().equals("OPEN")){
                                    status.setTextColor(getResources().getColor(R.color.colorGreen));
                                }
                                ln_2.setVisibility(View.VISIBLE);
                            } else if (position == 1){
                                title_top2.setText(R.string.date_isue);
                                status.setText(data.date_project_issued);
                                status.setTextColor(getResources().getColor(R.color.colorGrey));
                                ln_2.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    break;
                case 2:
                    progress.setVisibility(View.VISIBLE);
                    CallingDetailBAPJ();
                    ln_3.setVisibility(View.VISIBLE);
                    titlePage.setText(R.string.project_bpaj_detail);
                    subtile.setText(R.string.project_bpaj);
                    title_top1.setText(R.string.Project_Report_No);
                    title_top2.setText(R.string.BAPJ_Status);
                    booking_No.setText(data.booking_no);
                    status.setText(data.status_bapj);

                    title_sub3.setText(R.string.date_isue);

                    item_sub1.setText(data.temp_project_no);
                    item_sub2.setText(data.schedule_code);
                    item_sub3.setText(data.date_issued);

                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    tabLayout.setTabGravity(TabLayout.GRAVITY_START);
                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    viewpagerDefault.Addfragment(new Services(1),"Vessel Report");
                    viewpagerDefault.Addfragment(new Documents(),"Document");
                    break;
                case 3:
                    progress.setVisibility(View.VISIBLE);
                    CallingDetailInvoice();
                    titlePage.setText(R.string.invoice_detail);
                    subtile.setText(R.string.invoice_sub);
                    title_top1.setText(R.string.invoice_no);
                    status.setText(data.status_payment);
                    booking_No.setText(data.invoice_no);

                    ln_3.setVisibility(View.VISIBLE);
                    ln_4.setVisibility(View.VISIBLE);
                    ln_5.setVisibility(View.VISIBLE);
                    title_sub1.setText(R.string.due_date);
                    title_sub2.setText(R.string.invoice_type);
                    title_sub3.setText(R.string.va_number);
                    title_sub4.setText(R.string.payment_type);
                    title_sub5.setText(R.string.bilpay_reff);

                    item_sub2.setText(data.invoice_type);
                    item_sub1.setText(data.due_date);
                    item_sub4.setText(data.payment_type);
                    item_sub3.setText(data.va_reff_no);
                    item_sub5.setText(data.bill_payment_reff_no);


                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    viewpagerDefault.Addfragment(new Documents(),"Document");
                    break;
                case 4:
                    progress.setVisibility(View.VISIBLE);
                    CallingDetailProforma();
                    titlePage.setText(R.string.porfoma_detail);
                    subtile.setText(R.string.porforma_sub);
                    status.setText(data.status_payment);
                    booking_No.setText(data.booking_no);

                    ln_3.setVisibility(View.VISIBLE);
                    ln_4.setVisibility(View.VISIBLE);
                    title_sub1.setText(R.string.va_number);
                    title_sub2.setText(R.string.payment_type);
                    title_sub3.setText(R.string.bilpay_reff);
                    title_sub4.setText(R.string.book_stat);

                    item_sub1.setText(data.va_reff_no);
                    item_sub2.setText(data.tipe_pembayaran);
                    item_sub3.setText(data.bill_payment_reff_no);
                    item_sub4.setText(data.status_booking);

                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    viewpagerDefault.Addfragment(new Documents(),"Document");
                    break;
            }
        }

        switch (status.getText().toString()){
            case "PREPARED":
                status.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "CLOSED":
                status.setTextColor(getResources().getColor(R.color.colorGrey));
                break;
            case "Unpaid":
                status.setTextColor(getResources().getColor(R.color.colorRed));
                break;
            case "DRAFT":
                status.setTextColor(getResources().getColor(R.color.colorVerified));
                break;
            case "ON APPROVAL":
                status.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
        }

        viewPager.setAdapter(viewpagerDefault);
        tabLayout.setupWithViewPager(viewPager);


        btn_back.setOnClickListener(v -> onBackPressed());
    }

    public void CallingDetailBAPJ(){
        Model_Project data = Model_Project.mp;
        Log.i("detail", "CallingDetailBAPJ: " + data.t_vessel_schedule_id);
        Log.i("detail", "CallingDetailBAPJ: " + data.t_project_report_header_id);
        Call<CallingDetail> call = UserData.i.getService().getDetailBAPJ(UserData.i.getToken(),data.t_project_report_header_id,data.t_vessel_schedule_id);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail callingDetail = response.body();
                if (Calling.TreatResponse(Detail_MyProject.this,"tag", callingDetail)){
                    assert callingDetail != null;
                    Model_Project.mp = callingDetail.data.Information;
                    Model_Project.Service = callingDetail.data.Service;
                    Model_Project.VesselReport = callingDetail.data.VesselReport;
                    Model_Project.Piloting = callingDetail.data.Piloting;
                    Model_Project.Documents = callingDetail.data.Documents;
                    Log.i("service", "onResponse: => " + Model_Project.Service);
                    Log.i("service", "onResponse: piloting => " + Model_Project.Piloting.get(0).size());
                     progress.setVisibility(View.GONE);
                } else {
                    progress.setVisibility(View.GONE);
                    notfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("frag_bapj", "on Failure called!" + t);
            }
        });
    }

    public void CallingDetailList(){
        if (UserData.isExists()) {
            Model_Project data = Model_Project.mp;
            Call<CallingDetail> call = UserData.i.getService().getDetailList(UserData.i.getToken(),data.t_booking_id,data.t_project_header_id);
            call.enqueue(new Callback<CallingDetail>() {
                @Override
                public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                    CallingDetail callingDetail = response.body();
                    if (Calling.TreatResponse(Detail_MyProject.this,"tag", callingDetail)) {
                        assert callingDetail != null;
                        Model_Project.Service = callingDetail.data.Service;
                        Model_Project.mp = callingDetail.data.Information;
                        Log.i("service", "onResponse: => " + Model_Project.Service);
                        progress.setVisibility(View.GONE);
                    } else {
                        progress.setVisibility(View.GONE);
                        notfound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                    Log.e("frag_approved", "on Failure called!" + t);
                }
            });
        }
    }

    public void CallingDetailInvoice(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().getDetailInvoice(UserData.i.getToken(), data.t_billing_invoice_id, data.flag_compound);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(Detail_MyProject.this, "service", detail)) {
                    assert detail != null;
                    Log.i("service", "onResponse: => " + detail.data.Service);
                    Model_Project.InformationAndDocument = detail.data.InformationAndDocument;
                    Model_Project.Service = detail.data.Service;
                    Log.i("service", "onResponse: => " + Model_Project.Service);
                    progress.setVisibility(View.GONE);
                } else {
                    progress.setVisibility(View.GONE);
                    notfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("frag_service", "on Failure called!" + t);
            }
        });
    }

    public void CallingDetailApproval(){
        if (UserData.isExists()) {
            Model_Project data = Model_Project.mp;
            Call<CallingDetail> call = UserData.i.getService().getDetailApproval(UserData.i.getToken(),data.t_booking_id,data.t_project_header_id);
            call.enqueue(new Callback<CallingDetail>() {
                @Override
                public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                    CallingDetail callingDetail = response.body();
                    if (Calling.TreatResponse(Detail_MyProject.this,"tag", callingDetail)) {
                        assert callingDetail != null;
                        Model_Project.mp = callingDetail.data.Information;
                        Model_Project.Service = callingDetail.data.Service;
                        Log.i("service", "onResponse: => " + Model_Project.Service);
                        item_sub2.setText(callingDetail.data.Information.schedule_code);
                        progress.setVisibility(View.GONE);
                    } else {
                        progress.setVisibility(View.GONE);
                        notfound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                    Log.e("frag_approved", "on Failure called!" + t);
                }
            });
        }
    }

    public void CallingDetailProforma(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().getDetailProforma(UserData.i.getToken(), data.t_project_header_id, data.flag_compound);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(Detail_MyProject.this, "service", detail)) {
                    assert detail != null;
                    Log.i("service", "onResponse: => " + detail.data.Service);
                    Model_Project.InformationAndDocument = detail.data.InformationAndDocument;
                    Model_Project.Service = detail.data.Service;
                    progress.setVisibility(View.GONE);
                } else {
                    progress.setVisibility(View.GONE);
                    notfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("frag_service", "on Failure called!" + t);
            }
        });
    }


}
