package com.kbs.pocis.detailboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.UserData;

public class BookingDetails_Information extends Fragment {

    String nocontract, fgvesel, fgcontract, cName, vslName, ctype, bokDate, bokTime;
    //Box Booking Information
    TextView  nomerContract, flagVessel, customerName,
            vesselName, customerType, bookingDate, bookingTime, flagContract;
    //Box Schedule Information
    TextView detail_info_schedule_booking_vesselName, schedule_est_arival, schedule_est_departure,
        schedule_port_ship, schedule_port_cigd, schedule_loading, isempty;

    ConstraintLayout layout_schedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_information, container, false);

        nomerContract = view.findViewById(R.id.detail_info_booking_contractNo);
        flagVessel = view.findViewById(R.id.detail_info_booking_flagvessel);
        customerName = view.findViewById(R.id.detail_info_booking_customerName);
        vesselName = view.findViewById(R.id.detail_info_booking_vesselName);
        customerType = view.findViewById(R.id.detail_info_booking_customerType);
        bookingDate = view.findViewById(R.id.detail_info_booking_bookingDate);
        bookingTime = view.findViewById(R.id.detail_info_booking_bookingTime);
        flagContract = view.findViewById(R.id.detail_info_booking_flagContract);
        detail_info_schedule_booking_vesselName = view.findViewById(R.id.detail_info_schedule_booking_vesselName);
        schedule_est_arival = view.findViewById(R.id.detail_info_schedule_booking_estArival);
        schedule_est_departure = view.findViewById(R.id.detail_info_schedule_booking_departure);
        schedule_port_ship = view.findViewById(R.id.detail_info_schedule_booking_discharge);
        schedule_port_cigd = view.findViewById(R.id.detail_info_schedule_booking_port);
        schedule_loading = view.findViewById(R.id.detail_info_schedule_booking_loading);
        layout_schedule = view.findViewById(R.id.go_detail_booking);
        isempty = view.findViewById(R.id.schedule_isempty);

        if (BookingDetailData.i != null) {
            BookingDetailData bl = BookingDetailData.i;
            nomerContract.setText(bl.contract_no);
            flagVessel.setText(bl.relatedVesel);
            customerName.setText(bl.customer_name);
            vesselName.setText(bl.vessel_name);
            customerType.setText(bl.customerType);
            bookingDate.setText(bl.bookingDate);
            bookingTime.setText(bl.bookingTime);
            flagContract.setText(bl.contract);

            //Schedule Sett
            if (bl.vessel == null){
                layout_schedule.setVisibility(View.GONE);
                isempty.setVisibility(View.VISIBLE);
            } else {
                detail_info_schedule_booking_vesselName.setText(bl.vessel.vessel_name);
                schedule_est_arival.setText(bl.vessel.est_arival);
                schedule_port_cigd.setText(bl.vessel.cigading_port);
                schedule_loading.setText(bl.vessel.origin_port);
                schedule_loading.setText(bl.vessel.voy_no_in);
                schedule_est_departure.setText(bl.est_berthing);
            }

        } else if (BookingData.i != null) {
            BookingData bl = BookingData.i;
            nomerContract.setText(bl.contract);
            flagVessel.setText(bl.relatedVesel);
            customerName.setText(UserData.i.username);
            vesselName.setText(bl.vessel.vessel_name);
            customerType.setText(bl.customerType);
            bookingDate.setText(bl.bookingDate);
            bookingTime.setText(bl.bookingTime);
            flagContract.setText("");
            detail_info_schedule_booking_vesselName.setText(bl.vessel_name);
        } else {
            // TODO INFORMATION = NULL;
            Log.i("Tag", "list kosong: " + "List anda kosong");
        }
        //DataGet();

        return view;
    }
}
