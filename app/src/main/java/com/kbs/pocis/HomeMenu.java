package com.kbs.pocis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.activity.Invoice;
import com.kbs.pocis.activity.Monitoring;
import com.kbs.pocis.activity.MyProject_Dasar;
import com.kbs.pocis.activity.OnlineBook;
import com.kbs.pocis.adapter.AdapterNews;
import com.kbs.pocis.adapter.Adapter_Complain;
import com.kbs.pocis.calculator.Tarif_Calculate;
import com.kbs.pocis.complains.Complain_Dasar;
import com.kbs.pocis.item.Popup_Profile;
import com.kbs.pocis.model.Model_News;
import com.kbs.pocis.news.News_List;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.welcome.Contact_Us;

import java.util.ArrayList;
import java.util.List;

public class HomeMenu extends Fragment {

    ImageView menu_online_booking, menu_create_booking, iconprofile, menu_myproject, menu_invoice,
        menu_monitoring, menu_complain, menu_tarif_calculate, menu_Onload_Progres,menu_open_project;
    FloatingActionButton floatingActionButton;
    RecyclerView news1,news2;
    TextView showall1,showall2,text_ucapan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        text_ucapan = view.findViewById(R.id.text_ucapan);
        iconprofile = view.findViewById(R.id.iconprofile);
        news1 = view.findViewById(R.id.list_news1);
        news2 = view.findViewById(R.id.list_news2);
        showall1 = view.findViewById(R.id.show_all1);
        showall2 = view.findViewById(R.id.show_all2);


        menu_online_booking = view.findViewById(R.id.menu_online_booking);
        menu_online_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OnlineBook.class));
            }
        });

        menu_monitoring = view.findViewById(R.id.menu_operation_monitor);
        menu_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Monitoring.class));
            }
        });

        menu_create_booking = view.findViewById(R.id.menu_create_booking);
        menu_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingData.i = null;
                Intent intent = new Intent(getContext(), CreateBooking.class);
                startActivity(intent);
            }
        });

        menu_myproject = view.findViewById(R.id.menu_my_project);
        menu_myproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProject_Dasar.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });

        menu_open_project = view.findViewById(R.id.menu_open_project);
        menu_open_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProject_Dasar.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        menu_invoice = view.findViewById(R.id.menu_invoice_perfom);
        menu_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Invoice.class));
            }
        });

        iconprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new Popup_Profile();
                fragment.show(getChildFragmentManager(),"PopUp");
            }
        });

        floatingActionButton = view.findViewById(R.id.floating_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new Contact_Us();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        menu_complain = view.findViewById(R.id.menu_my_complaint);
        menu_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new Complain_Dasar();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        menu_tarif_calculate = view.findViewById(R.id.menu_tarif_calculate);
        menu_tarif_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new Tarif_Calculate();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        menu_Onload_Progres = view.findViewById(R.id.menu_Onload_Progres);
        menu_Onload_Progres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Monitoring.class));
            }
        });

        UserData userData = UserData.i;
        text_ucapan.setText("Good Morning "+ userData.username +"! Here's the quick menu");

        listNewt();
        listReward();

        return view;
    }

    public void listNewt(){
        showall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new News_List(0);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        List<Model_News> model_news = new ArrayList<>();
        model_news.add(new Model_News(null,"Lorem Ipsum dua tiga","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","Ini desc",2));
        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","",3));
        AdapterNews adapterNews = new AdapterNews(getContext(), model_news,0,0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        news1.setLayoutManager(layoutManager);
        news1.setAdapter(adapterNews);
    }

    public void listReward(){
        showall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new News_List(1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        List<Model_News> model_news = new ArrayList<>();
        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
        model_news.add(new Model_News(null,"Yeey anonim nomer5 dapet hadiah pesawat baru lohh","02/04/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",2));
        model_news.add(new Model_News(null,"Yeey anonim nomer6 dapet hadiah motor baru lohh","06/02/2019","",3));
        AdapterNews adapterNews = new AdapterNews(getContext(), model_news,0,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        news2.setLayoutManager(layoutManager);
        news2.setAdapter(adapterNews);
    }
}