package com.kbs.pocis.onlineboking;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class CancelBooking extends Fragment {

    RecyclerView recyclerView;
    Adapter_CancelBooking adapter_cancelBooking;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancel_booking,container,false);

        recyclerView = view.findViewById(R.id.recycle_Cancelbooking);

        List<Model_Bookings> model_bookingsList = new ArrayList<>();
        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00087", "booking",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00088", "booking",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));
        model_bookingsList.add(new Model_Bookings("PS.00/172.01/PMS/XI/2014", "K0001-2020-00089", "booking",
                "BG. LKH 3883","CNEE","PT. KRAKATAU POSCO","Yes",
                "Yes","2020-04-01 13:02:43","2020-03-27 08:00:00"));

        adapter_cancelBooking = new Adapter_CancelBooking(getContext(), model_bookingsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_cancelBooking);


        return view;
    }
}
