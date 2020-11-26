package com.kbs.pocis.filter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.onlineboking.AllBookings;
import com.kbs.pocis.service.UserData;

public class Filter_OnlineBooking extends DialogFragment {

    TextInputEditText input_vesel, input_nomerBook;
    Button button_back, button_next;
    private static int REQUEST_CODE = 123;
    AllBookings online_booking;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    public Filter_OnlineBooking(AllBookings online_booking){
        this.online_booking = online_booking;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = getLayoutInflater().inflate(R.layout.dialog_filters, container, false);

        input_nomerBook = view.findViewById(R.id.filter_nomerbooking);
        input_vesel = view.findViewById(R.id.filter_veselname);
        button_back = view.findViewById(R.id.btn_cancelbooking);
        button_next = view.findViewById(R.id.btn_filterbooking);
        if (UserData.isExists()) {
            if (UserData.i.filter!=null) {
                input_nomerBook.setText(UserData.i.filter.nomor);
                input_vesel.setText(UserData.i.filter.vessel);
            }
        }
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserData.isExists()){
                    UserData.i.updateFilter(input_nomerBook.getText().toString(),input_vesel.getText().toString());
                }
                if (online_booking != null) {
                    online_booking.filter = UserData.i.filter;
                    online_booking.pmanager = null;
                    online_booking.GenerateLists();
                }
                dismiss();
            }
        });

        return view;

    }
}
