package com.kbs.pocis;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.Invoice;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.monitoring.Monitoring;
import com.kbs.pocis.activity.MyProject_Dasar;
import com.kbs.pocis.activity.OnlineBook;
import com.kbs.pocis.adapter.AdapterNews;
import com.kbs.pocis.calculator.Tarif_Calculate;
import com.kbs.pocis.complains.Complain_Dasar;
import com.kbs.pocis.news.News_List;
import com.kbs.pocis.profile.Profile_Menu;
import com.kbs.pocis.progressbook.Progress_List;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.welcome.Contact_Us;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeMenu extends Fragment {

    ImageView menu_online_booking, menu_create_booking, iconprofile, menu_myproject, menu_invoice, menu_progres_booking,
        menu_monitoring, menu_complain, menu_tarif_calculate, menu_Onload_Progres,menu_open_project;
    FloatingActionButton floatingActionButton;
    RecyclerView news1,news2;
    TextView showall1,showall2,text_ucapan, time_lineup;
    List<Model_Project> model_news;
    Button go_downInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        text_ucapan = view.findViewById(R.id.text_ucapan);
        time_lineup = view.findViewById(R.id.time_lineup);
        go_downInfo = view.findViewById(R.id.go_downInfo);
        iconprofile = view.findViewById(R.id.iconprofile);
        news1 = view.findViewById(R.id.list_news1);
        news2 = view.findViewById(R.id.list_news2);
        showall1 = view.findViewById(R.id.show_all1);
        showall2 = view.findViewById(R.id.show_all2);



        menu_online_booking = view.findViewById(R.id.menu_online_booking);
        menu_online_booking.setOnClickListener(v -> startActivity(new Intent(getActivity(), OnlineBook.class)));

        menu_progres_booking = view.findViewById(R.id.menu_progres_booking);
        menu_progres_booking.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Progress_List();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        menu_monitoring = view.findViewById(R.id.menu_operation_monitor);
        menu_monitoring.setOnClickListener(v -> startActivity(new Intent(getActivity(), Monitoring.class)));

        menu_create_booking = view.findViewById(R.id.menu_create_booking);
        menu_create_booking.setOnClickListener(v -> {
            BookingData.i = null;
            Intent intent = new Intent(getContext(), CreateBooking.class);
            startActivity(intent);
        });

        menu_myproject = view.findViewById(R.id.menu_my_project);
        menu_myproject.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyProject_Dasar.class);
            intent.putExtra("id", 0);
            Model_Project.CheckMenuProject = 0;
            startActivity(intent);
        });

        menu_open_project = view.findViewById(R.id.menu_open_project);
        menu_open_project.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyProject_Dasar.class);
            intent.putExtra("id", 1);
            Model_Project.CheckMenuProject = 1;
            startActivity(intent);
        });

        menu_invoice = view.findViewById(R.id.menu_invoice_perfom);
        menu_invoice.setOnClickListener(v -> startActivity(new Intent(getActivity(), Invoice.class)));

        iconprofile.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Profile_Menu();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        //Go Contact Us
        floatingActionButton = view.findViewById(R.id.floating_action);
        floatingActionButton.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Contact_Us();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        menu_complain = view.findViewById(R.id.menu_my_complaint);
        menu_complain.setOnClickListener(v ->
            startActivity(new Intent(getActivity(), Complain_Dasar.class))
        );

        menu_tarif_calculate = view.findViewById(R.id.menu_tarif_calculate);
        menu_tarif_calculate.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Tarif_Calculate();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        menu_Onload_Progres = view.findViewById(R.id.menu_Onload_Progres);
        menu_Onload_Progres.setOnClickListener(v -> startActivity(new Intent(getActivity(), Monitoring.class)));

        if (UserData.isExists()){
            UserData userData = UserData.i;
            getTimeZone(text_ucapan, userData);
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listNewt();
        listReward();
        vesselLineUp();
    }
    public void vesselLineUp(){
        Call<CallingDetail> call = UserData.i.getService().vesselLineup(UserData.i.getToken());
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(getContext(), "vessel_info", data)){
                    assert data != null;
                    if (data.data.VesselLineUp.size() > 0){
                        formater(data.data.VesselLineUp.get(0).created, time_lineup);
                        go_downInfo.setOnClickListener(v->{
                            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(data.data.VesselLineUp.get(0).link));
                            startActivity(web);
                        });
                    } else {
                        Log.i(TAG, "onResponse: " + data.data.VesselLineUp.size());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });
    }

    public void formater(String str, TextView txt){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date date = fmt.parse(str);
            Calendar cal = Calendar.getInstance();
            assert date != null;
            cal.setTime(date);
            txt.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(cal.getTime()));
        } catch (ParseException e){
            txt.setText(str);
            Log.i(TAG, "formater: " + e);
        }
    }

    public void listNewt(){
        Call<PublicList> call = UserData.i.getService().customerNews(UserData.i.getToken());
        call.enqueue(new Callback<PublicList>() {
            @Override
            public void onResponse(@NotNull Call<PublicList> call, @NotNull Response<PublicList> response) {
                PublicList data = response.body();
                if (Calling.TreatResponse(getContext(), "news", data)){
                    assert data != null;
                    model_news = new ArrayList<>();
                    model_news.addAll(data.data.model);
                    AdapterNews adapterNews = new AdapterNews(getContext(), model_news,0,0);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                    news1.setLayoutManager(layoutManager);
                    news1.setAdapter(adapterNews);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PublicList> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });


        showall1.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new News_List(0);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    public void listReward(){
        model_news = new ArrayList<>();
        Call<PublicList> call = UserData.i.getService().customerRewards(UserData.i.getToken());
        call.enqueue(new Callback<PublicList>() {
            @Override
            public void onResponse(@NotNull Call<PublicList> call, @NotNull Response<PublicList> response) {
                PublicList data = response.body();
                if (Calling.TreatResponse(getContext(), "news", data)){
                    assert data != null;
                    model_news = new ArrayList<>();
                    model_news.addAll(data.data.model);
                    AdapterNews adapterNews = new AdapterNews(getContext(), model_news,0,1);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                    news2.setLayoutManager(layoutManager);
                    news2.setAdapter(adapterNews);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PublicList> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });
        showall2.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new News_List(1);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    private void getTimeZone(TextView text_ucapan, UserData userData){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);

        if(hours>=4 && hours<=12){
            String ucapan = "Good Morning "+ userData.username +"! Here's the quick menu";
            text_ucapan.setText(ucapan);
        }else if(hours>=12 && hours<=17){
            String ucapan = "Good Afternoon "+ userData.username +"! Here's the quick menu";
            text_ucapan.setText(ucapan);
        }else if(hours>=17){
            String ucapan = "Good Evening "+ userData.username +"! Here's the quick menu";
            text_ucapan.setText(ucapan);
        }
    }
}