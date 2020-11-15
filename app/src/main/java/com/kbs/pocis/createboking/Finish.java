package com.kbs.pocis.createboking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.service.UserData;

public class Finish extends AppCompatActivity {

    Button backhome;
    TextView bookagain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        setContentView(R.layout.fragment_finish);
        backhome = findViewById(R.id.finish_backHome);
        bookagain = findViewById(R.id.finish_bookegain);

        ButtonFunction();
    }

    public void ButtonFunction(){
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData user = (UserData) getIntent().getParcelableExtra("user");
                Intent intent = new Intent(Finish.this, HomePage.class).putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });


        bookagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData user = (UserData) getIntent().getParcelableExtra("user");
                Intent intent = new Intent(Finish.this, CreateBooking.class).putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }

}