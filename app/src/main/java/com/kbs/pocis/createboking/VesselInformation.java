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
import com.valdesekamdem.library.mdtoast.MDToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
    String estimate_value, departure_value;
    ImageView iconArrow;
    LinearLayout expanded, card, line_voyageNumber;
    CheckBox dis_check, load_check;
    Call<List<CallingList>> call;
    int id_voyage, id_vessel, discharge_id, origin_id;
    String ves_name = "";
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
        Log.i(TAG, "Related Vessel " + data.relatedVesel);
        Log.i(TAG, "Customer Type " + data.customerType);
        Log.i(TAG, "Customer Type " + BookingData.i.status_change);

        if (BookingData.i.hideVoyage()) {
            line_voyageNumber.setVisibility(View.GONE);
        }

        if (BookingData.isExist() && BookingData.i.vessel!=null && !BookingData.i.status_change) {
            BookingData.VesselData bd = BookingData.i.vessel;
            vesel_name.setText(bd.vessel_name);
            port_discharge.setText(bd.port_discharge);
            discharge_id = bd.port_discharge_id;
            port_origin.setText(bd.port_origin);
            origin_id = bd.port_origin_id;
            estimate_arival.setText(bd.estimate_arival);
            estimate_departure.setText(bd.estimate_departure);
            voyage_number.setText(bd.voyage_number);
            id_voyage = bd.id_voyage;
            id_vessel = bd.id_vessel;
            Log.i(TAG, "onCreateView: => " + BookingData.i.customerId);
        } else{
            BookingData.i.vessel = new BookingData.VesselData();
            discharge_id = -1;
            origin_id = -1;
            id_voyage = -1;
            id_vessel = -1;
        }


        load_vessel = true;
        estimate_arival.setOnClickListener(v -> ShowDateTime(estimate_arival));
        estimate_departure.setOnClickListener(v -> ShowDateTime(estimate_departure));

        vesel_name.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2 && load_vessel){
                    GetAPIVesselName(s.toString(), vesel_name);
                }
            }
        });
        voyage_number.setOnClickListener(v-> GetApiVoyageNumber(voyage_number, id_vessel));

        port_discharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >=2 && load_vessel) {
                    GetAPIPortDischarge(s, port_discharge, true);
                }
            }
        });
        port_origin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >=2 && load_vessel) {
                    GetAPIPortDischarge(s, port_origin, false);
                }
            }
        });

        next = view.findViewById(R.id.veselinfo_nextBtn);
        prev = view.findViewById(R.id.veselinfo_prevBtn);

        ButtonFunction();
        return view;
    }

    boolean checkVoyageNumber(){
        return id_vessel == -1 || vesel_name.getText().toString().isEmpty() || ves_name == null;
    }

    private void GetApiVoyageNumber(AutoCompleteTextView textView, int ids){
        if (checkVoyageNumber()){
            pesanError("Please Add Vessel Name");
        } else {
            Call<List<BookingDetailData>> callist = UserData.i.getService().getVoyageNumber(disload_value, ids);
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
                        textView.setThreshold(1);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, tr);
                        textView.setAdapter(adapter);
                        textView.setOnItemClickListener((parent, view, position, id) -> {
                            Log.i(TAG, "voyage_no: => " + detailData.get(position).voyage_no);
                            Log.i(TAG, "voyage_id: => " + detailData.get(position).id);
                            id_voyage = detailData.get(position).id;
                        });
//                        click box input_type, show suggestions
                        textView.setOnTouchListener((v, event) -> {
                            if (detailData.size() > 0) {
                                if (!textView.getText().toString().equals(""))
                                    adapter.getFilter().filter(null);
                                textView.showDropDown();
                            }
                            return false;
                        });
                        adapter.notifyDataSetChanged();
                    } else {
                        textView.setThreshold(0);
                        textView.setAdapter(null);
                        pesanError("Vessel Name Not Have Voyage Number");
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
                            Log.i(TAG, "Port Discharge list =>  " + forms.get(i).name);
                            arr[i] = forms.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        textView.setOnItemClickListener((parent, view, position, id) -> {
                            discharge_id = forms.get(position).id;
                            Log.i(TAG, "port Discharge = " + forms.get(position).id);
                        });
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "port Origin list =>  " + forms.get(i).name);
                            arr[i] = forms.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                        textView.setAdapter(adapter);
                        textView.setThreshold(2);
                        textView.setOnItemClickListener((parent, view, position, id) -> {
                            origin_id = forms.get(position).id;
                            Log.i(TAG, "onItemClick: port Origin = " + forms.get(position).id);
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
                        Log.i(TAG, "Vessel Name: list =>  " + forms.get(i).name);
                        arr[i] = forms.get(i).name;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                    textView.setAdapter(adapter);
                    textView.setThreshold(2);
                    textView.setOnItemClickListener((parent, view, position, id) -> {
                        Log.i(TAG, "onItemClick: Vessel Name = " + forms.get(position).id);
                        id_vessel = forms.get(position).id;
                        ves_name = forms.get(position).name;
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

                SimpleDateFormat fr = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss", Locale.ENGLISH);
                estimate_value = fr.format(calendar.getTime());
                departure_value = fr.format(calendar.getTime());

                texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
            };
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), true);
            timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
            timePickerDialog.show(getChildFragmentManager(), "TimePicker");
        };

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                voyage_number.setText(null);
                load_check.setChecked(false);
            } else {
                dischargeLoading.setText(R.string.Loading);
                disload_value = "L";
                voyage_number.setText(null);
                load_check.setChecked(true);
            }
            BookingData.i.checkChange(false);
        });

        load_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                dischargeLoading.setText(R.string.Loading);
                disload_value = "L";
                voyage_number.setText(null);
                dis_check.setChecked(false);
            } else {
                dischargeLoading.setText(R.string.Discharge);
                disload_value = "D";
                voyage_number.setText(null);
                dis_check.setChecked(true);
            }
            BookingData.i.checkChange(false);
        });
    }

    public void UpdateData(){
        BookingData.i.vessel = new BookingData.VesselData(
                vesel_name.getText().toString(),
                port_discharge.getText().toString(),
                discharge_id,
                port_origin.getText().toString(),
                origin_id,
                estimate_arival.getText().toString(),
                estimate_departure.getText().toString(),
                id_voyage,
                id_vessel,
                disload_value,
                voyage_number.getText().toString(),
                estimate_value,
                departure_value
        );
        Log.i(TAG, "estimate: => " + estimate_value);
        Log.i(TAG, "departure: => " + departure_value);
    }

    private void StatusInputMessage(){
        if (line_voyageNumber.getVisibility() == View.VISIBLE){
            if ( Objects.requireNonNull(port_discharge.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(port_origin.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(vesel_name.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(estimate_arival.getText()).toString().isEmpty() ||
                    voyage_number.getText().toString().isEmpty() ||
                    Objects.requireNonNull(estimate_departure.getText()).toString().isEmpty()) {
                pesanError("Please Add All Vessel Information");
            } else if (id_vessel <= 0 || id_voyage <= 0) {
                pesanError("please check Vessel or Voyage Name correctly!");
            } else if (origin_id <= 0 || discharge_id <= 0) {
                pesanError("please check Port Discharge or Port Origin correctly!");
            } else {
                UpdateData();
                BookingData.i.checkChange(false);
                Fragment fragment = new Summary();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else {
            if ( Objects.requireNonNull(port_discharge.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(port_origin.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(vesel_name.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(estimate_arival.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(estimate_departure.getText()).toString().isEmpty()) {
                pesanError("Please Add All Vessel Information");
            } else if (id_vessel <= 0) {
                pesanError("please check Vessel Name correctly!");
            } else if (origin_id <= 0 || discharge_id <= 0) {
                pesanError("please check Port Discharge or Port Origin correctly!");
            } else {
                UpdateData();
                BookingData.i.checkChange(false);
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
            BookingData.i.checkChange(false);
            requireActivity().onBackPressed();
        });

        next.setOnClickListener(v -> {
            Log.i(TAG, "onClick: => " + id_vessel);
            StatusInputMessage();
        });
    }

    private void pesanError(String pesan){
        MDToast.makeText(requireContext(), pesan, MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
    }
}