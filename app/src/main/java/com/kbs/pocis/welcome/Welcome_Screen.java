package com.kbs.pocis.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Pager_Onboard;
import com.kbs.pocis.item.Item_onboard;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager;

public class Welcome_Screen extends AppCompatActivity {

    AutoScrollViewPager viewPager;
    Pager_Onboard pager_onboard;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        //Ini untuk menambahkan komponen text dan background gambar pada screen welcome
        List<Item_onboard> item = new ArrayList<>();
        item.add(new Item_onboard("Welcome to", "KIPOS App", "Experience the new way of easier life", R.drawable.bg_one));
        item.add(new Item_onboard("Easy access with", "KIPOS App", "Hand-on app with 24/7 access", R.drawable.bg_two));
        item.add(new Item_onboard("Real-time success", "KIPOS App", "Encourage your near future success", R.drawable.bg_tiga));

        viewPager = findViewById(R.id.pagerwelcome);
        viewPager.startAutoScroll(4000);
        viewPager.setInterval(6000);
        ViewPagerIndicator circleIndicator = findViewById(R.id.indicator);
        Button goLogin = findViewById(R.id.btn_gologin);
        goLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Welcome_Screen.this, Auth.class);
            startActivity(intent);
            Welcome_Screen.this.finish();
        });
        //Ini untuk nampilin viewpager di screen welcome
        pager_onboard = new Pager_Onboard(this, item);
        viewPager.setAdapter(pager_onboard);
        //ini indicatornya
        circleIndicator.initWithViewPager(viewPager);
    }

}
