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

    int page_current = 0, page_last = 2;
    boolean Ready;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
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
        layout_kosong = view.findViewById(R.id.lay_allbooking_kosong);
        layout_ada = view.findViewById(R.id.lay_allbooking_ada);

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
        UserData user = UserData.i;
        UserService service = user.getService();
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

                    if (model_bookingsList !=null ? model_bookingsList.size()>0 : false) {
                        layout_ada.setVisibility(View.VISIBLE);
                        layout_kosong.setVisibility(View.GONE);
                        adapter_allBooking = new Adapter_AllBooking(getContext(), model_bookingsList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                        adapter_allBooking.getFilter().filter(user.filter);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_allBooking);
                    } else {
                        layout_ada.setVisibility(View.GONE);
                        layout_kosong.setVisibility(View.VISIBLE);
                        Log.e("allBooking", "onResponse: " + " Layout nya kosong" );
                    }

                    return;
                } else {
                    Log.e("all_boking", "Failed : \n Error " + respone.error + " : " + respone.desc);
                }
                bar.setVisibility(View.INVISIBLE);
                all_index_allboking.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
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
