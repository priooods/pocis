package com.kbs.pocis.detailboking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;

public class BookingDetails_Information extends Fragment {

    String nocontract, fgvesel, fgcontract, cName, vslName, ctype, bokDate, bokTime;
    //Box Booking Information
    TextView  nomerContract, flagVessel, customerName,
            vesselName, customerType, bookingDate, bookingTime, flagContract;
    //Box Schedule Information
    TextView detail_info_schedule_booking_vesselName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_information,container,false);

        nomerContract = view.findViewById(R.id.detail_info_booking_contractNo);
        flagVessel = view.findViewById(R.id.detail_info_booking_flagvessel);
        customerName = view.findViewById(R.id.detail_info_booking_customerName);
        vesselName = view.findViewById(R.id.detail_info_booking_vesselName);
        customerType = view.findViewById(R.id.detail_info_booking_customerType);
        bookingDate = view.findViewById(R.id.detail_info_booking_bookingDate);
        bookingTime = view.findViewById(R.id.detail_info_booking_bookingTime);
        flagContract = view.findViewById(R.id.detail_info_booking_flagContract);
        detail_info_schedule_booking_vesselName = view.findViewById(R.id.detail_info_schedule_booking_vesselName);

//        BookingData bl = BookingData.i;
//        nomerContract.setText(bl.contract);
//        flagVessel.setText(bl.relatedVesel);
//        customerName.setText(UserData.i.username);
//        vesselName.setText(bl.vessel.vessel_name);
//        customerType.setText(bl.customerType);
//        bookingDate.setText(bl.uploadDate);
//        bookingTime.setText(bl.uploadTime);
//        flagContract.setText("");
        ScheduleInformation();
        //DataGet();

        return view;
    }
    //Set text detail Booking Information
    public void BookingInformation (){
        nomerContract.setText(nocontract);
        flagVessel.setText(fgvesel);
        customerName.setText(cName);
        vesselName.setText(vslName);
        customerType.setText(ctype);
        bookingDate.setText(bokDate);
        bookingTime.setText(bokTime);
        flagContract.setText(fgcontract);
    }

    void ScheduleInformation(){
        detail_info_schedule_booking_vesselName.setText(vslName);
    }
}
