package com.kbs.pocis.createboking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.CallingData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class VesselInformation extends Fragment {

    Button next, prev;
    TextInputEditText vesel_name, loading_shipcall, discharge_ship, port_discharge, port_origin,
            estimate_arival, estimate_departure;
    TextView tobeNominated;
    ImageView iconArrow;
    LinearLayout expanded, card;
    CheckBox no_vesel, yes_vesel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vessel_information, container, false);

        vesel_name = view.findViewById(R.id.input_veselname_vesselinfo);
        loading_shipcall = view.findViewById(R.id.input_shipcal_vesselinfo);
        discharge_ship = view.findViewById(R.id.input_discharge_vesselinfo);
        port_discharge = view.findViewById(R.id.input_port_vesselinfo);
        port_origin = view.findViewById(R.id.input_origin_vesselinfo);
        estimate_arival = view.findViewById(R.id.input_estimate_vesselinfo);
        estimate_departure = view.findViewById(R.id.input_departure_vesselinfo);

        //Expanded for To Be Nominated Box
        tobeNominated = view.findViewById(R.id.veselinfo_relatedvesel_value);
        iconArrow = view.findViewById(R.id.vesel_relatedvesel_icon);
        expanded = view.findViewById(R.id.veselinfo_expand_relatedvesel);
        card = view.findViewById(R.id.a);
        yes_vesel = view.findViewById(R.id.check_yes_vesel_relatedvesel);
        no_vesel = view.findViewById(R.id.check_no_vessel_relatedvesel);
        ToBeNominated();
        CheckBoxesRelatedVessel();

        estimate_arival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateTime(estimate_arival);
            }
        });

        estimate_departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateTime(estimate_departure);
            }
        });

        next = view.findViewById(R.id.veselinfo_nextBtn);
        prev = view.findViewById(R.id.veselinfo_prevBtn);

        ButtonFunction();


        return view;
    }

    void ShowDateTime(TextInputEditText texit){
        Calendar calendar = Calendar.getInstance();
        // TODO INI DATANYA GA BISA DIAMBIL PAKE DATE INI ERROR SEMUA
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener dateSetListener = new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener setListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(android.icu.util.Calendar.MINUTE, minute);
                        texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
                    }
                };
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                timePickerDialog.show(getChildFragmentManager(), "TimePicker");
            }
        };

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.show(getChildFragmentManager(),"DateDialog");
    }

    void ToBeNominated(){
        iconArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    expanded.setVisibility(View.VISIBLE);
                    iconArrow.setImageResource(R.drawable.icon_top);
                } else {
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    expanded.setVisibility(View.GONE);
                    iconArrow.setImageResource(R.drawable.icon_botom);
                }
            }
        });
    }

    void CheckBoxesRelatedVessel(){
        no_vesel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tobeNominated.setText("No");
                    yes_vesel.setChecked(false);
                } else {
                    tobeNominated.setText("Yes");
                    yes_vesel.setChecked(true);
                }
            }
        });

        yes_vesel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tobeNominated.setText("Yes");
                    no_vesel.setChecked(false);
                } else {
                    tobeNominated.setText("No");
                    no_vesel.setChecked(true);
                }
            }
        });
    }

    void ConditionError(){
        if (vesel_name.getText().length() < 4 || loading_shipcall.getText().length() < 4 || discharge_ship.getText().length() < 4){
            pesanError("Vessel Name, Loading Ship, Discharge Ship Minimun 4 Character");
        } else if (port_origin.getText().length() < 2 || estimate_arival.getText().length() < 2){
            pesanError("Port Discharge and Port Origin Minimun 2 Character");
        }
    }

    void StatusInputNull(){
        if (vesel_name.getText().toString().isEmpty() || port_discharge.getText().toString().isEmpty() ||
                discharge_ship.getText().toString().isEmpty() || port_origin.getText().toString().isEmpty() ||
                estimate_arival.getText().toString().isEmpty() ||loading_shipcall.getText().toString().isEmpty() ||
                estimate_departure.getText().toString().isEmpty()){
            Toasty.error(getContext(), "Form Input Harus Diisi Lengkap", Toasty.LENGTH_SHORT, true).show();
        } else {
            Bundle arg = new Bundle();
            arg.putString("vesel", vesel_name.getText().toString());
            arg.putString("port", port_discharge.getText().toString());
            arg.putString("discharge", discharge_ship.getText().toString());
            arg.putString("origin", port_origin.getText().toString());
            arg.putString("estimate", estimate_arival.getText().toString());
            arg.putString("shipcall", loading_shipcall.getText().toString());
            arg.putString("departure", estimate_departure.getText().toString());
            BookingData.i.vessel = new BookingData.VesselData(
                    vesel_name.getText().toString(),
                    loading_shipcall.getText().toString(),
                    discharge_ship.getText().toString(),
                    port_discharge.getText().toString(),
                    port_origin.getText().toString(),
                    estimate_arival.getText().toString(),
                    estimate_departure.getText().toString()
            );

            Fragment fragment = new Summary();
            fragment.setArguments(arg);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }
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
                ConditionError();
                StatusInputNull();


            }
        });
    }

    void pesanError(String pesan){
        Toasty.error(getContext(), pesan, Toasty.LENGTH_SHORT, true).show();
    }
}