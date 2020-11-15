package com.kbs.pocis.createboking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class VesselInformation extends Fragment {

    Button next, prev;
    TextInputEditText vesel_name, shipcall, discharge, port, origin, estimate, departure;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vessel_information, container, false);

        vesel_name = view.findViewById(R.id.input_veselname_vesselinfo);
        shipcall = view.findViewById(R.id.input_shipcal_vesselinfo);
        discharge = view.findViewById(R.id.input_discharge_vesselinfo);
        port = view.findViewById(R.id.input_port_vesselinfo);
        origin = view.findViewById(R.id.input_origin_vesselinfo);
        estimate = view.findViewById(R.id.input_estimate_vesselinfo);
        departure = view.findViewById(R.id.input_departure_vesselinfo);

        estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateTime(estimate);
            }
        });

        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateTime(departure);
            }
        });

        next = view.findViewById(R.id.veselinfo_nextBtn);
        prev = view.findViewById(R.id.veselinfo_prevBtn);
        ButtonFunction();

        return view;
    }

    public void ButtonFunction(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusInputNull();
            }
        });
    }

    void StatusInputNull(){
        if (vesel_name.getText().toString().isEmpty() || port.getText().toString().isEmpty() ||
                discharge.getText().toString().isEmpty() || origin.getText().toString().isEmpty() ||
                estimate.getText().toString().isEmpty() ||shipcall.getText().toString().isEmpty() ||
                departure.getText().toString().isEmpty()){
            Toasty.error(getContext(), "Form Input Harus Diisi Lengkap", Toasty.LENGTH_SHORT, true).show();
        } else {
            Fragment fragment = new Summary();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    void ShowDateTime(TextInputEditText texit){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener setListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(android.icu.util.Calendar.MINUTE, minute);
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.LONG, DateFormat.SHORT);
                        texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(getContext(), setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


    }
}