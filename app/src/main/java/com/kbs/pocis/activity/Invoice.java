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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbs.pocis.R;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.invoice.Frag_Invoice;
import com.kbs.pocis.invoice.Performa;

public class Invoice extends AppCompatActivity {

    ImageView icon_back;
    BottomNavigationView bottombar_myprojects;
    FilterFragment filterFragment;
    TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        icon_back = findViewById(R.id.btn_back_invoice);
        icon_back.setOnClickListener(v -> {
//            filterFragment.Stop = true;
            onBackPressed();
            finish();
        });
        bottombar_myprojects = findViewById(R.id.bottombar_invoice);
        title = findViewById(R.id.titile);
        bottombar_myprojects.setOnNavigationItemSelectedListener(listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameInvoice, new Frag_Invoice()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        filterFragment.Stop = true;
//        getSupportFragmentManager().isDestroyed();
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()){
                        case R.id.invoice:
                            selectFragment = new Frag_Invoice();
                            title.setText(R.string.invoice);
                            break;
                        case R.id.performa:
                            selectFragment = new Performa();
                            title.setText(R.string.proforma);
                            break;
                    }
                    assert selectFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameInvoice, selectFragment).commit();
                    return true;
                }
            };
}
