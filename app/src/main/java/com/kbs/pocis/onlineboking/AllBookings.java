package com.kbs.pocis.onlineboking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.adapter.onlineboking.Adapter_AllBooking;
import com.kbs.pocis.model.Model_Bookings;
import com.kbs.pocis.service.CallingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.UserService;
import com.kbs.pocis.welcome.Login;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBookings extends Fragment {
    //TODO Saran, keknya perlu ditambahin tampilan loading (layar abu2 hitam dengan tulisan "loading..." misalnya) supaya selama load Booking user tau harus nunggu
    Adapter_AllBooking adapter_allBooking;
    RecyclerView recyclerView;
    ConstraintLayout bar;
    TextView kanan, kiri,kanan_banget,kiri_banget, index_list_allboking, all_index_allboking;
    List<Model_Bookings> model_bookingsList;
    NestedScrollView nestdall;
    int page_current = 1,page_last = 2;
    boolean Ready;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_bookings,container,false);

        recyclerView = view.findViewById(R.id.recycle_Allbooking);
        kanan = view.findViewById(R.id.kanan);
        kiri = view.findViewById(R.id.kiri);
        kanan_banget = view.findViewById(R.id.kanan_banget);
        kiri_banget = view.findViewById(R.id.kiri_banget);
        bar = view.findViewById(R.id.all_index_bar);
        index_list_allboking = view.findViewById(R.id.index_list_allboking);
        all_index_allboking = view.findViewById(R.id.all_index_allboking);
        nestdall = view.findViewById(R.id.nestdall);

        GenerateList();
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
            GenerateList();
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
    void GenerateList(){
        UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
        UserService service = user.getService();
        if (user==null)
            return;
        if (service == null) {
            Log.e("all_booking","ERROR SERVICE");
        }
        Call<CallingData> call = service.getAllBooking(user.getToken(),String.valueOf(page_current));
        if (call == null) {
            Log.i("all_boking","CallingData Post Method is Bad!");
        }
        call.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                Ready = true;
                CallingData respone = (CallingData) response.body();
                if (CallingData.TreatResponse(getContext(), "all_booking", respone)) {
                    List<Model_Bookings> list = new ArrayList<>();
                    for (CallingData.Booking data : respone.data.book) {
                        list.add(data.getModel());
                    }
                    model_bookingsList = list;
                    page_current = respone.data.current_page;
                    page_last = respone.data.last_page;
                    //region KONTROL VISIBILITAS KOMPONEN
                    bar.setVisibility(View.VISIBLE);
                    all_index_allboking.setVisibility(View.VISIBLE);
                    ScrolltoTop();
                    SetVisibility(kiri, page_current>1);
                    SetVisibility(kiri_banget, page_current>2);
                    SetVisibility(kanan, page_current<page_last);
                    SetVisibility(kanan_banget, page_current+1<page_last);
                    //endregion
                    index_list_allboking.setText(page_current + " of " + page_last);
                    all_index_allboking.setText("Showing "+ (respone.data.to_page - respone.data.from_page + 1) + " of " + respone.data.total + " results");

                    adapter_allBooking = new Adapter_AllBooking(getContext(), model_bookingsList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter_allBooking);
                    Log.i("finish_list", "In Page : "+page_current+" | Finish Listing " + list.size());
                    return;
                } else {
                    //pesan(respone.desc);
                    Log.e("all_boking", "Failed : \n Error " + respone.error + " : " + respone.desc);
                }
                bar.setVisibility(View.INVISIBLE);
                all_index_allboking.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
                //pesan("onFailure called login!");
                Ready = true;
                Log.e("all_boking", "on Failure called!"+ t);
            }
        });
    }
    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    };
}
