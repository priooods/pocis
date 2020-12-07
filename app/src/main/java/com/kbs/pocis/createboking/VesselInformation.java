package com.kbs.pocis.createboking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingList;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class VesselInformation extends Fragment {

    Button next, prev;
    TextInputEditText vesel_name,
            estimate_arival, estimate_departure;
    AutoCompleteTextView  port_discharge, port_origin, voyage_number;
    TextView tobeNominated;
    ImageView iconArrow;
    LinearLayout expanded, card;
    CheckBox no_vesel, yes_vesel;
    Call<List<CallingList>> call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vessel_information, container, false);

        vesel_name = view.findViewById(R.id.input_veselname_vesselinfo);
        port_discharge = view.findViewById(R.id.input_port_vesselinfo);
        port_origin = view.findViewById(R.id.input_origin_vesselinfo);
        estimate_arival = view.findViewById(R.id.input_estimate_vesselinfo);
        estimate_departure = view.findViewById(R.id.input_departure_vesselinfo);
        voyage_number = view.findViewById(R.id.input_voyagenumber_vesselinfo);


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

        port_discharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    GetAPIPortDischarge(s, port_discharge, true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        port_origin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    GetAPIPortDischarge(s, port_origin, false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        voyage_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    GetApiVoyageNumber(s.toString(), voyage_number);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        next = view.findViewById(R.id.veselinfo_nextBtn);
        prev = view.findViewById(R.id.veselinfo_prevBtn);

        if (BookingData.isExist()){
            BookingData.VesselData bd = BookingData.i.vessel;
            if (bd !=null) {
                vesel_name.setText(bd.vessel_name);
                port_discharge.setText(bd.port_discharge);
                port_origin.setText(bd.port_origin);
                estimate_arival.setText(bd.estimate_arival);
                estimate_departure.setText(bd.estimate_departure);
                voyage_number.setText(bd.voyage_number);
            }
        }
        ButtonFunction();
        return view;
    }

    void GetApiVoyageNumber(CharSequence s, AutoCompleteTextView textView){
        Call<List<BookingDetailData>> call = UserData.i.getService().getVoyageNumber(s.toString());
        call.enqueue(new Callback<List<BookingDetailData>>() {
            @Override
            public void onResponse(Call<List<BookingDetailData>> call, Response<List<BookingDetailData>> response) {
                List<BookingDetailData> detailData = response.body();
                if (detailData.size() > 0){
                    String[] tr = new String[detailData.size()];
                    for (int i=0; i < tr.length; i++){
                        tr[i] = detailData.get(i).voyage_no;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.model_spiner, R.id.val_spiner, tr);
                    textView.setAdapter(adapter);
                    textView.setThreshold(2);
                    textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //TODO Getting voyage_no
                            Log.i(TAG, "onItemClick voyageId: => " + detailData.get(position).voyage_no);
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BookingDetailData>> call, Throwable t) {
                Log.e(TAG, "onFailure: => " + t );
            }
        });
    }

    void GetAPIPortDischarge(CharSequence s, AutoCompleteTextView textView, boolean val){
        if (val == true){
            call = UserData.i.getService().getPortDischarge(s.toString());
        } else {
            call = UserData.i.getService().getPortOrigin();
        }

        call.enqueue(new Callback<List<CallingList>>() {
            @Override
            public void onResponse(Call<List<CallingList>> call, Response<List<CallingList>> response) {
                List<CallingList> forms = response.body();
                if (forms.size() > 0) {
                    String[] arr = new String[forms.size()];
                    if (val == true) {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "onResponse: list =>  " + forms.get(i).name);
                            arr[i] = forms.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "onResponse: list =>  " + forms.get(i).name);
                            arr[i] = forms.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //TODO dibawah ini sample get id. bisa disesuaikan sama kebutuhan. mau id atau name dst
                                // atau kalau cuman get name/desc aja udah automatis ke save ko di singletoon yg udah dibuat kemarin - kemarin
                                Log.i(TAG, "onItemClick: getportOrigin = " + forms.get(position).id);
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CallingList>> call, Throwable t) {
                Log.e(TAG, "onFailure: => " + t );
            }
        });
    }

    void ShowDateTime(TextInputEditText texit){
        Calendar calendar = Calendar.getInstance();
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
//        datePickerDialog.setMinDate(calendar);
        calendar.add(Calendar.MONTH, -2);
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

    void UpdateData(){
        BookingData.i.vessel = new BookingData.VesselData(
                vesel_name.getText().toString(),
                port_discharge.getText().toString(),
                port_origin.getText().toString(),
                estimate_arival.getText().toString(),
                estimate_departure.getText().toString(),
                voyage_number.getText().toString()
        );
    }

    void StatusInputMessage(){
        if (vesel_name.getText().toString().isEmpty() || port_discharge.getText().toString().isEmpty() ||
                port_origin.getText().toString().isEmpty() ||
                estimate_arival.getText().toString().isEmpty() ||
                estimate_departure.getText().toString().isEmpty()){
            Toasty.error(getContext(), "Form Input Harus di isi Lengkap", Toasty.LENGTH_SHORT, true).show();
        }
        if (vesel_name.getText().length() < 4 ){
            pesanError("Vessel Name, Loading Ship, Discharge Ship Minimun 4 Character");
        } else if (port_origin.getText().length() < 2 || port_discharge.getText().length() < 2){
            pesanError("Port Discharge and Port Origin Minimun 2 Character");
        } else {
            UpdateData();
            Fragment fragment = new Summary();
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
                UpdateData();
                getActivity().onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusInputMessage();
            }
        });
    }

    void pesanError(String pesan){
        Toasty.error(getContext(), pesan, Toasty.LENGTH_SHORT, true).show();
    }
}