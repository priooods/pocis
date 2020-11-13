package com.kbs.pocis.onlineboking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    Adapter_AllBooking adapter_allBooking;
    RecyclerView recyclerView;
    ImageView kanan, kiri;
    List<Model_Bookings> model_bookingsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_bookings,container,false);

        recyclerView = view.findViewById(R.id.recycle_Allbooking);
//        List<Model_Bookings> model_bookingsList = new ArrayList<>();
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00097", "approved",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00098", "cancelled",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00099", "booking",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00099", "verified",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//
        GenerateList();
//        adapter_allBooking = new Adapter_AllBooking(getContext(), model_bookingsList);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter_allBooking);

        return view;
    }
    void ganti(){
        kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            Call<CallingData> call = service.getAllBooking(user.getToken());
            if (call == null) {
                Log.i("all_boking","CallingData Post Method is Bad!");
            }
            call.enqueue(new Callback<CallingData>() {
                @Override
                public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                    CallingData respone = (CallingData) response.body();
                    if (CallingData.TreatResponse(getContext(), "all_booking", respone)) {
                        List<Model_Bookings> list = new ArrayList<>();
                        for (CallingData.Booking data : respone.data.book) {
                            list.add(data.getModel());
                        }
                        model_bookingsList = list;

                        int page_number = respone.data.current_page;
                        //to
                        int page_max = respone.data.last_page;
                        int page_now = respone.data.to_page - respone.data.from_page + 1;
                        //  of
                        int page_of = respone.data.total;

                        adapter_allBooking = new Adapter_AllBooking(getContext(), model_bookingsList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_allBooking);
                        Log.i("finish_list", "Finish Listing " + list.size());
                    } else {
                        //pesan(respone.desc);
                        Log.e("all_boking", "Failed : \n Error " + respone.error + " : " + respone.desc);
                    }
                }
                @Override
                public void onFailure(Call<CallingData> call, Throwable t) {
                    //pesan("onFailure called login!");
                    Log.e("all_boking", "on Failure called!"+ t);
                }
            });
    }
}
