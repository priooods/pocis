package com.kbs.pocis.onlineboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.onlineboking.Adapter_CancelBooking;
import com.kbs.pocis.model.onlineboking.Model_Bookings;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.api.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelBooking extends Fragment {
    //TODO INI CANCEL BOOKING MAU DIBUAT KEK MANA? KALO DAH LENGKAP, NNTI GUA LENGKAPIN BUAT SAFECONTROL SAMA TAMPILANNYA
    RecyclerView recyclerView;
    NestedScrollView nestdcan;
    RelativeLayout relativeLayout_ada, relativeLayout_kosong;
    ConstraintLayout bar;
    Adapter_CancelBooking adapter_cancelBooking;
    TextView kanan, kiri,kanan_banget,kiri_banget,index_list_cancelboking, all_index_cancelboking;
    List<Model_Bookings> model_bookingsList;
    int page_current = 1,page_last = 1;
    boolean Ready;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancel_booking,container,false);

        kanan = view.findViewById(R.id.kanan_cancel);
        kiri = view.findViewById(R.id.kiri_cancel);
        kanan_banget = view.findViewById(R.id.kanan_banget_cancel);
        kiri_banget = view.findViewById(R.id.kiri_banget_cancel);
        bar = view.findViewById(R.id.all_index_bar_cancel);
        index_list_cancelboking = view.findViewById(R.id.index_list_cancelboking);
        all_index_cancelboking = view.findViewById(R.id.all_index_cancelboking);
        recyclerView = view.findViewById(R.id.recycle_Cancelbooking);
        relativeLayout_ada = view.findViewById(R.id.lay_cancelbooking_ada);
        relativeLayout_kosong = view.findViewById(R.id.lay_cancelbooking_kosong);


        nestdcan = view.findViewById(R.id.nestdcan);

        GenerateList();
        ganti();

        return view;
    }

    void ScrolltoTop(){
        nestdcan.fullScroll(View.FOCUS_UP);
        nestdcan.smoothScrollTo(0,0);
    }

    void ChangePage(int target_page){
        if (Ready) {
            page_current = target_page;
            GenerateList();
            Ready = false;
        }else{
            Log.w("cancel_booking","Aggresive Touch/Command!");
        }
    }


    void ganti(){
        kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                page_current+=1;
//                GenerateList();
                ChangePage(page_current + 1);
            }
        });
        kanan_banget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                page_current=page_last;
//                GenerateList();
                ChangePage(page_last);
            }
        });
        kiri_banget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                page_current=1;
//                GenerateList();
                ChangePage(1);
            }
        });
        kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                page_current-=1;
//                GenerateList();
                ChangePage(page_current-1);
            }
        });
    }
    /// Fungsi untuk membuat list
    void GenerateList(){
        UserData user = UserData.i;
        UserService service = user.getService();
        if (user==null)
            return;
        if (service == null) {
            Log.e("cancel_boking","ERROR SERVICE");
        }
        Call<CallingData> call = service.getAllCancel(user.getToken(),String.valueOf(page_current));
        if (call == null) {
            Log.i("cancel_boking","CallingData Post Method is Bad!");
        }
        call.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                Ready = true;
                CallingData respone = (CallingData) response.body();
                if (CallingData.TreatResponse(getContext(), "all_booking", respone)) {
                    List<Model_Bookings> list = new ArrayList<>();
                    for(CallingData.Booking data : respone.data.book) {
                        list.add(data.getModel());
                    }
                    model_bookingsList = list;
                    page_current = respone.data.current_page;
                    page_last = respone.data.last_page;

                    //region KONTROL VISIBILITAS KOMPONEN
                    bar.setVisibility(View.VISIBLE);
                    all_index_cancelboking.setVisibility(View.VISIBLE);

                    ScrolltoTop();
                    SetVisibility(kiri, page_current>1);
                    SetVisibility(kiri_banget, page_current>2);
                    SetVisibility(kanan, page_current<page_last);
                    SetVisibility(kanan_banget, page_current+1<page_last);

                    //endregion
                    index_list_cancelboking.setText(page_current + " of " + page_last);
                    all_index_cancelboking.setText("Showing "+ (respone.data.to_page - respone.data.from_page + 1) + " of " + respone.data.total + " results");

                    if (model_bookingsList !=null ? model_bookingsList.size()>0 : false) {
                        relativeLayout_kosong.setVisibility(View.GONE);
                        relativeLayout_ada.setVisibility(View.VISIBLE);
                        adapter_cancelBooking = new Adapter_CancelBooking(getContext(), model_bookingsList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_cancelBooking);
                        adapter_cancelBooking.notifyDataSetChanged();
                    } else {
                        relativeLayout_ada.setVisibility(View.GONE);
                        relativeLayout_kosong.setVisibility(View.VISIBLE);
                        Log.e("cancel", "onResponse: " + "Kosong Layoutnya" );
                    }
                    Log.i("cancel","Finish Listing "+ list.size());
                } else {
                    //pesan(respone.desc);
                    Log.e("cancel_boking", "Failed : \n Error " + respone.error + " : " + respone.desc);
                }
            }
            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
                //pesan("onFailure called login!");
                Log.e("cancel_boking", "on Failure called!"+ t);
            }
        });
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    };
}
