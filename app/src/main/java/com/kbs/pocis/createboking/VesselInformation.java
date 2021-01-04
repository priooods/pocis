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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class VesselInformation extends Fragment {

    Button next, prev;
    TextInputEditText estimate_arival, estimate_departure;
    AutoCompleteTextView  port_discharge, port_origin, voyage_number,vesel_name;
    TextView dischargeLoading;
    String disload_value = "D";
    ImageView iconArrow;
    LinearLayout expanded, card, line_voyageNumber;
    CheckBox dis_check, load_check;
    Call<List<CallingList>> call;
    Call<List<BookingDetailData>> callist;
    int idvoyage, idvessel, discharge_id, origin_id;
    boolean load_vessel = false;

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

        //Line voyage
        line_voyageNumber = view.findViewById(R.id.z);
        //Expanded for Discharge or Loading
        dischargeLoading = view.findViewById(R.id.veselinfo_disload_value);
        iconArrow = view.findViewById(R.id.vesel_relatedvesel_icon);
        expanded = view.findViewById(R.id.veselinfo_expand_relatedvesel);
        card = view.findViewById(R.id.a);
        dis_check = view.findViewById(R.id.check_discharge_veselinfo);
        load_check = view.findViewById(R.id.check_loading_veselinfo);
        DisorLoad();
        CheckBoxesDisLoad();

        BookingData data = BookingData.i;
        if (data.customerType.equals("GENERAL")) {
            line_voyageNumber.setVisibility(View.GONE);
        }

        if (BookingData.isExist() && BookingData.i.vessel!=null) {
            BookingData.VesselData bd = BookingData.i.vessel;
            vesel_name.setText(bd.vessel_name);
            port_discharge.setText(bd.port_discharge);
            discharge_id = bd.port_discharge_id;
            port_origin.setText(bd.port_origin);
            origin_id = bd.port_origin_id;
            estimate_arival.setText(bd.estimate_arival);
            estimate_departure.setText(bd.estimate_departure);
            voyage_number.setText(bd.voyage_number);
            idvoyage = bd.id_voyage;
            idvessel = bd.id_vessel;
            Log.i(TAG, "onCreateView: => " + BookingData.i.customerId);
        } else{
            BookingData.i.vessel = new BookingData.VesselData();
            discharge_id = -1;
            origin_id = -1;
            idvoyage = -1;
            idvessel = -1;
        }


        load_vessel = true;
        estimate_arival.setOnClickListener(v -> {
            ShowDateTime(estimate_arival);
            //BookingData.i.vessel.estimate_arival = estimate_arival.getText().toString();
        });
        estimate_departure.setOnClickListener(v -> {
            ShowDateTime(estimate_departure);
            //BookingData.i.vessel.estimate_departure = estimate_departure.getText().toString();
        });

        vesel_name.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2 && load_vessel){
                    GetAPIVesselName(s.toString(), vesel_name);
                }
            }
        });

        port_discharge.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2 && load_vessel){
                    GetAPIPortDischarge(s, port_discharge, true);
                }
            }
        });

        port_origin.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>=2 && load_vessel){
                    GetAPIPortDischarge(s, port_origin, false);
                }
            }
        });

        voyage_number.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                load_vessel = false;
            }
            @Override public void afterTextChanged(Editable s) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                load_vessel = true;
                if (s.length()>=2 && load_vessel){
                    GetApiVoyageNumber(s.toString(), voyage_number, load_vessel);
                }
            }
        });

        next = view.findViewById(R.id.veselinfo_nextBtn);
        prev = view.findViewById(R.id.veselinfo_prevBtn);

        ButtonFunction();
        return view;
    }

    private void GetApiVoyageNumber(CharSequence s, AutoCompleteTextView textView, boolean st){
        if (st) {
            callist = UserData.i.getService().getVoyageNumber(s.toString());
            callist.enqueue(new Callback<List<BookingDetailData>>() {
                @Override
                public void onResponse(@NotNull Call<List<BookingDetailData>> call, @NotNull Response<List<BookingDetailData>> response) {
                    List<BookingDetailData> detailData = response.body();
                    assert detailData != null;
                    if (detailData.size() > 0) {
                        String[] tr = new String[detailData.size()];
                        for (int i = 0; i < tr.length; i++) {
                            tr[i] = detailData.get(i).voyage_no;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, tr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        textView.setOnItemClickListener((parent, view, position, id) -> {
                            //TODO Getting voyage_no
                            Log.i(TAG, "onItemClick voyageId: => " + detailData.get(position).voyage_no);
                            Log.i(TAG, "onItemClick: => " + detailData.get(position).id);
                            idvoyage = detailData.get(position).id;
                            //BookingData.i.vessel.id_voyage = idvoyage;
                        });
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.i(TAG, "onResponse: => " + " Not Running API");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<BookingDetailData>> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: => " + t);
                }
            });
        }
    }

    private void GetAPIPortDischarge(CharSequence s, AutoCompleteTextView textView, boolean val){
        if (val){
            call = UserData.i.getService().getPortDischarge(s.toString());
        } else {
            call = UserData.i.getService().getPortOrigin();
        }

        call.enqueue(new Callback<List<CallingList>>() {
            @Override
            public void onResponse(@NotNull Call<List<CallingList>> call, @NotNull Response<List<CallingList>> response) {
                List<CallingList> forms = response.body();
                assert forms != null;
                if (forms.size() > 0) {
                    String[] arr = new String[forms.size()];
                    if (val) {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "onResponse: list =>  " + forms.get(i).name);
                            arr[i] = forms.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        textView.setOnItemClickListener((parent, view, position, id) -> {
                            discharge_id = forms.get(position).id;
                            Log.i(TAG, "onItemClick: getportDischarge = " + forms.get(position).id);
                            //BookingData.i.vessel.port_discharge = String.valueOf(forms.get(position).id);
                        });
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "onResponse: list =>  " + forms.get(i).name);
                            arr[i] = forms.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        textView.setOnItemClickListener((parent, view, position, id) -> {
                            origin_id = forms.get(position).id;
                            Log.i(TAG, "onItemClick: getportOrigin = " + forms.get(position).id);
                            //BookingData.i.vessel.port_origin = String.valueOf(forms.get(position).id);
                        });
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CallingList>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: => " + t );
            }
        });
    }

    private void GetAPIVesselName(CharSequence s, AutoCompleteTextView textView){
            call = UserData.i.getService().getVesselId(s.toString());
            call.enqueue(new Callback<List<CallingList>>() {
            @Override
            public void onResponse(@NotNull Call<List<CallingList>> call, @NotNull Response<List<CallingList>> response) {
                List<CallingList> forms = response.body();
                assert forms != null;
                if (forms.size() > 0) {
                    String[] arr = new String[forms.size()];
                    for (int i = 0; i < arr.length; i++) {
                        Log.i(TAG, "onResponse: list =>  " + forms.get(i).name);
                        arr[i] = forms.get(i).name;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                    textView.setAdapter(adapter);
                    textView.setThreshold(2);
                    textView.setOnItemClickListener((parent, view, position, id) -> {
                        Log.i(TAG, "onItemClick: getportOrigin = " + forms.get(position).id);
                        idvessel = forms.get(position).id;
                        //BookingData.i.vessel.id_vessel = idvessel;
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CallingList>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: => " + t );
            }
        });
    }

    private void ShowDateTime(TextInputEditText texit){
        Calendar calendar = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener setListener = (view1, hourOfDay, minute, second) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
            };
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
            timePickerDialog.show(getChildFragmentManager(), "TimePicker");
        };

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.setMinDate(calendar);
        calendar.add(Calendar.MONTH, -2);
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.show(getChildFragmentManager(),"DateDialog");
    }

    private void DisorLoad(){
        iconArrow.setOnClickListener(v -> {
            if (expanded.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(card, new AutoTransition());
                expanded.setVisibility(View.VISIBLE);
                iconArrow.setImageResource(R.drawable.icon_top);
            } else {
                TransitionManager.beginDelayedTransition(card, new AutoTransition());
                expanded.setVisibility(View.GONE);
                iconArrow.setImageResource(R.drawable.icon_botom);
            }
        });
    }

    private void CheckBoxesDisLoad(){
        dis_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                dischargeLoading.setText(R.string.Discharge);
                disload_value = "D";
                load_check.setChecked(false);
            } else {
                dischargeLoading.setText(R.string.Loading);
                disload_value = "L";
                load_check.setChecked(true);
            }
        });

        load_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                dischargeLoading.setText(R.string.Loading);
                disload_value = "L";
                dis_check.setChecked(false);
            } else {
                dischargeLoading.setText(R.string.Discharge);
                disload_value = "D";
                dis_check.setChecked(true);
            }
        });
    }

    void UpdateData(){
        BookingData.i.vessel = new BookingData.VesselData(
                vesel_name.getText().toString(),
                port_discharge.getText().toString(),
                discharge_id,
                port_origin.getText().toString(),
                origin_id,
                Objects.requireNonNull(estimate_arival.getText()).toString(),
                Objects.requireNonNull(estimate_departure.getText()).toString(),
                idvoyage,
                idvessel,
                voyage_number.getText().toString(),
                disload_value
        );
        Log.i(TAG, "onCreateView: => " + idvoyage);
        Log.i(TAG, "onCreateView: => " + disload_value);
    }

    private void StatusInputMessage(){
        if (voyage_number.getVisibility() == View.VISIBLE){
            if (voyage_number.getText().toString().isEmpty() && idvoyage <= 0){
                pesanError("please check Voyage Number correctly!");
            } else if ( Objects.requireNonNull(estimate_arival.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(estimate_departure.getText()).toString().isEmpty()) {
                Toasty.error(requireContext(), "Form Input Harus di isi Lengkap", Toasty.LENGTH_SHORT, true).show();
            } else if (idvessel <= 0) {
                pesanError("please check Vessel Name correctly!");
            } else if (origin_id <= 0 || discharge_id <= 0) {
                pesanError("please check Port Discharge or Port Origin correctly!");
            } else {
                UpdateData();
                Fragment fragment = new Summary();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else {
            if ( Objects.requireNonNull(estimate_arival.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(estimate_departure.getText()).toString().isEmpty()) {
                Toasty.error(requireContext(), "Form Input Harus di isi Lengkap", Toasty.LENGTH_SHORT, true).show();
            } else if (idvessel <= 0) {
                pesanError("please check Vessel Name correctly!");
            } else if (origin_id <= 0 || discharge_id <= 0) {
                pesanError("please check Port Discharge or Port Origin correctly!");
            } else {
                UpdateData();
                Fragment fragment = new Summary();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        }

    }

    public void ButtonFunction(){
        prev.setOnClickListener(v -> {
            UpdateData();
            requireActivity().onBackPressed();
        });

        next.setOnClickListener(v -> {
            Log.i(TAG, "onClick: => " + idvessel);
            StatusInputMessage();
        });
    }

    private void pesanError(String pesan){
        Toasty.error(requireContext(), pesan, Toasty.LENGTH_SHORT, true).show();
    }
}