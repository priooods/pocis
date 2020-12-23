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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.adapter.myprojects.Adapter_Project_Service;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.myproject.detail.Documents;
import com.kbs.pocis.myproject.detail.Informations;
import com.kbs.pocis.myproject.detail.Services;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_MyProject extends AppCompatActivity {

    LinearLayout ln_3,ln_4,ln_5;

    //textview lainnya
    TextView booking_No, status, titlePage, subtile, title_top1,title_top2,title_sub3,title_sub4,title_sub5,
            title_sub1,title_sub2, item_sub1, item_sub2,item_sub3, item_sub4,item_sub5;

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView btn_back;
    RelativeLayout ln_sub0;


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
        ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
        tabLayout = findViewById(R.id.project_details_tablayout);
        viewPager = findViewById(R.id.project_details_viewpager);
        btn_back = findViewById(R.id.project_details_btn_back);
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
                    titlePage.setText(R.string.project_aprov_detail);
                    subtile.setText(R.string.project_aprov);
                    booking_No.setText(data.booking_no);
                    status.setText(data.status);
                    item_sub1.setText(data.temp_proj_no);
                    item_sub2.setText(data.schedule_code);

                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    break;
                case 1:
                    titlePage.setText(R.string.project_list_detail);
                    subtile.setText(R.string.project_list);
                    title_top1.setText(R.string.temp_proj);
                    booking_No.setText(data.temp_proj_no);
                    status.setText(data.status);
                    title_sub1.setText(R.string.date_isue);
                    title_sub2.setText(R.string.ppjno);
                    item_sub1.setText(data.date_issue);
                    item_sub2.setText(data.ppj_nomer);
                    viewpagerDefault.Addfragment(new Informations(),"Information");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    break;
                case 2:
                    titlePage.setText(R.string.project_bpaj_detail);
                    subtile.setText(R.string.project_bpaj);
                    title_top1.setText(R.string.booking_no);
                    booking_No.setText(data.booking_no);
                    status.setText(data.status);
                    item_sub1.setText(data.temp_proj_no);
                    item_sub2.setText(data.schedule_code);

                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {
                            Log.i("tag", "onPageSelected: " + position);
                            switch (position){
                                case 0:
                                    title_top1.setText(R.string.booking_no);
                                    booking_No.setText(data.booking_no);
                                    status.setText(data.status);
                                    item_sub1.setText(data.temp_proj_no);
                                    item_sub2.setText(data.schedule_code);

                                    status.setTextSize(12);
                                    ln_sub0.setVisibility(View.VISIBLE);
                                    status.setTextColor(getResources().getColor(R.color.colorVerified));
                                    break;
                                case 1:
                                case 2:
                                    title_top1.setText(R.string.bpaj_no);
                                    title_top2.setText(R.string.date_isue);
                                    booking_No.setText(data.bpaj_no);
                                    status.setText(data.date_issue);

                                    ln_sub0.setVisibility(View.GONE);
                                    status.setTextSize(10);
                                    status.setTextColor(getResources().getColor(R.color.colorGrey));
                                    break;
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    viewpagerDefault.Addfragment(new Informations(),"Document");
                    viewpagerDefault.Addfragment(new Services(0),"Service");
                    viewpagerDefault.Addfragment(new Services(1),"Vessel Report");

                    break;
                case 3:
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
        }

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
