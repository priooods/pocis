package com.kbs.pocis.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_CancelBooking;
import com.kbs.pocis.adapter.Adapter_TarifApproved;
import com.kbs.pocis.model.Model_Bookings;

import java.util.ArrayList;
import java.util.List;

public class TarifApprove extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_TarifApproved adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarif_approve);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }


        List<Model_Bookings> model_bookings = new ArrayList<>();
        recyclerView = findViewById(R.id.tarif_approve_recycleview);
        adapter = new Adapter_TarifApproved(this, model_bookings);

        model_bookings.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00067", "verified",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
        model_bookings.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00068", "verified",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
        model_bookings.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00069", "verified",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));

        adapter = new Adapter_TarifApproved(this, model_bookings);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
