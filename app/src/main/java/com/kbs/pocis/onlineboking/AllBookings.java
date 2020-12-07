package com.kbs.pocis.onlineboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.onlineboking.Adapter_AllBooking;
import com.kbs.pocis.model.onlineboking.Model_Bookings;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.api.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AllBookings extends Fragment {

    Adapter_AllBooking adapter_allBooking;
    RecyclerView recyclerView;
    RelativeLayout layout_ada, layout_kosong;
    ConstraintLayout bar;
    TextView kanan, kiri, kanan_banget, kiri_banget,
            index_list_allboking, all_index_allboking;
    List<Model_Bookings> model_bookingsList;
    NestedScrollView nestdall;

    int page_current = 0, page_last = 2 , total_item = 0;
    public PageManager pmanager;
    boolean Ready,load,filtering;
    public Filters filter;
    int booking_type;
    public AllBookings(int type){
        booking_type = type;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (booking_type == 0) {
            view = inflater.inflate(R.layout.fragment_all_bookings, container, false);
            recyclerView = view.findViewById(R.id.recycle_Allbooking);
            kanan = view.findViewById(R.id.kanan);
            kiri = view.findViewById(R.id.kiri);
            kanan_banget = view.findViewById(R.id.kanan_banget);
            kiri_banget = view.findViewById(R.id.kiri_banget);
            bar = view.findViewById(R.id.all_index_bar);
            index_list_allboking = view.findViewById(R.id.index_list_allboking);
            all_index_allboking = view.findViewById(R.id.all_index_allboking);
            nestdall = view.findViewById(R.id.nestdall);
            layout_kosong = view.findViewById(R.id.lay_allbooking_kosong);
            layout_ada = view.findViewById(R.id.lay_allbooking_ada);
        }else{
            view = inflater.inflate(R.layout.fragment_cancel_booking, container, false);
            kanan = view.findViewById(R.id.kanan_cancel);
            kiri = view.findViewById(R.id.kiri_cancel);
            kanan_banget = view.findViewById(R.id.kanan_banget_cancel);
            kiri_banget = view.findViewById(R.id.kiri_banget_cancel);
            bar = view.findViewById(R.id.all_index_bar_cancel);
            index_list_allboking = view.findViewById(R.id.index_list_cancelboking);
            all_index_allboking = view.findViewById(R.id.all_index_cancelboking);
            recyclerView = view.findViewById(R.id.recycle_Cancelbooking);
            layout_ada = view.findViewById(R.id.lay_cancelbooking_ada);
            layout_kosong = view.findViewById(R.id.lay_cancelbooking_kosong);
            nestdall = view.findViewById(R.id.nestdcan);
        }


        filter = null;
        GenerateLists();
        ganti();

        return view;
    }


    void ScrolltoTop(){
        nestdall.fullScroll(View.FOCUS_UP);
        nestdall.smoothScrollTo(0,0);
    }

    void ChangePage(int target_page){
        if (Ready) {
            page_current = target_page;
            GenerateLists();
            Ready = false;
        }else{
            Log.w("all_booking","Aggresive Touch/Command!");
        }
    }

    void ganti(){
        kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(page_current + 1);
            }
        });
        kanan_banget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(page_last);
            }
        });
        kiri_banget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(1);
            }
        });
        kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(page_current-1);
            }
        });
    }

    /// Fungsi untuk membuat list
    void GenerateFilter(int page, int list) {
        Log.i("all_booking", "Call AllBooking page = "+page);
        Call<CallingData> call;
        if (booking_type == 0) {
            call = UserData.i.getService().getAllBooking(UserData.i.getToken(), String.valueOf(page));
        }else{
            call = UserData.i.getService().getAllCancel(UserData.i.getToken(), String.valueOf(page));
        }
        if (call == null) {
            Log.i("all_booking", "CallingData Post Method is Bad!");
        }
        call.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                CallingData respone = response.body();
                if (CallingData.TreatResponse(getContext(), "all_booking", respone)) {
                    if (!filtering) {
                        for (CallingData.Booking data : respone.data.book) {
                            model_bookingsList.add(data.getModel());
                        }
                        page_current = respone.data.current_page;
                        page_last = respone.data.last_page;
                        total_item = respone.data.total;
                        ShowAdapter();
                        return;
                    }else {
                        if (pmanager.loaded) {
                            Log.i("booking_load", "pmanager.load"+pmanager.loaded+" page = "+page);
                            CallingData.Booking[] data = respone.data.book;
                            for (int i = list; i < data.length; i++) {
                                if (filter.checkFilter(data[i])) {
                                    if (model_bookingsList.size() < pmanager.page_capacity) {
                                        model_bookingsList.add(data[i].getModel());
                                        Log.i("booking_load",data[i].readString());
                                    }else{
                                        page_last = pmanager.page_last;
                                        total_item = pmanager.total;
                                        load = false;
                                        ShowAdapter();
                                        return;
                                    }
                                }
                            }
                            if (page<respone.data.last_page){
                                GenerateFilter(page +1, 0);
                            }else {
                                page_last = pmanager.page_last;
                                total_item = pmanager.total;
                                load = false;
                                ShowAdapter();
                                return;
                            }
                        } else {
                            Log.i("booking_load", "pmanager.load"+pmanager.loaded+" page = "+page+" load = "+load);
                            int i = 0;
                            for (CallingData.Booking data : respone.data.book) {
                                if (filter.checkFilter(data)) {
                                    if (pmanager.addPack(page, i) && load) {
                                        model_bookingsList.add(data.getModel());
                                        Log.i("booking_load",data.readString());
                                    } else {
                                        load = false;
                                    }
                                }
                                i++;
                            }
                            if (page == respone.data.last_page) {
                                if (pmanager.pack > 0) {
                                    pmanager.finalPack(page, i - 1);
                                }
                                pmanager.finishLoad();
                                page_last = pmanager.page_last;
                                total_item = pmanager.total;
                                load = false;
                                ShowAdapter();
                                return;
                            } else {
                                GenerateFilter(page + 1, 0);
                            }
                        }
                    }
                }else{
                    try {
                        Thread.sleep(4500,0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        GenerateFilter(page, 0);
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
                Ready = true;
                Log.e("all_boking", "on Failure called!" + t);
            }
        });
        try {
            Thread.sleep(100,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void GenerateLists() {
        if (UserData.i == null) {
            Log.e("all_booking", "ERROR SERVICE");
        }
        if (model_bookingsList == null)
            model_bookingsList = new ArrayList<>();
        else
            model_bookingsList.clear();
        if (filter != null) {
            filtering = load = true;
            if (pmanager == null) {
                pmanager = new PageManager();
                page_current = 1;
                GenerateFilter(1, 0);
            } else {
                GenerateFilter(pmanager.getPage(page_current), pmanager.getList(page_current));
            }
            return;
        }
        filtering = false;
        GenerateFilter(page_current, 0);
    }
    void ShowAdapter() {
        Ready = true;
        if (model_bookingsList != null ? model_bookingsList.size() > 0 : false) {
            ScrolltoTop();
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);
            index_list_allboking.setText(page_current + " of " + page_last);
            all_index_allboking.setText("Showing " + (model_bookingsList.size()) + " of " + total_item + " results");
            layout_ada.setVisibility(View.VISIBLE);
            layout_kosong.setVisibility(View.GONE);
            bar.setVisibility(View.VISIBLE);
            all_index_allboking.setVisibility(View.VISIBLE);
            adapter_allBooking = new Adapter_AllBooking(getContext(), model_bookingsList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_allBooking);
        } else {
            bar.setVisibility(View.INVISIBLE);
            all_index_allboking.setVisibility(View.INVISIBLE);
            layout_ada.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: " + booking_type );
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    };
}
