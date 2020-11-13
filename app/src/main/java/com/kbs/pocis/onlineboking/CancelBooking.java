package com.kbs.pocis.onlineboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.onlineboking.Adapter_CancelBooking;
import com.kbs.pocis.model.Model_Bookings;
import com.kbs.pocis.service.CallingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelBooking extends Fragment {

    RecyclerView recyclerView;
    Adapter_CancelBooking adapter_cancelBooking;
    List<Model_Bookings> model_bookingsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancel_booking,container,false);

        recyclerView = view.findViewById(R.id.recycle_Cancelbooking);
//        model_bookingsList = new ArrayList<>();
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00087", "booking",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00088", "booking",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00089", "booking",
//                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
//                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
//
        GenerateList();
//        adapter_cancelBooking = new Adapter_CancelBooking(getContext(), model_bookingsList);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter_cancelBooking);

        return view;
    }
    /// Fungsi untuk membuat list
    void GenerateList(){
        UserData user = (UserData) getActivity().getIntent().getParcelableExtra("user");
        UserService service = user.getService();
        if (user==null)
            return;
        if (service == null) {
            Log.e("cancel_boking","ERROR SERVICE");
        }
        Call<CallingData> call = service.getAllCancel(user.getToken());
        if (call == null) {
            Log.i("cancel_boking","CallingData Post Method is Bad!");
        }
        call.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                CallingData respone = (CallingData) response.body();
                if (CallingData.TreatResponse(getContext(), "all_booking", respone)) {
                    List<Model_Bookings> list = new ArrayList<>();
                    for(CallingData.Booking data : respone.data.book) {
                        list.add(data.getModel());
                    }
                    model_bookingsList = list;

                    adapter_cancelBooking = new Adapter_CancelBooking(getContext(), model_bookingsList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter_cancelBooking);
                    Log.i("finish_list","Finish Listing "+ list.size());
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
}
