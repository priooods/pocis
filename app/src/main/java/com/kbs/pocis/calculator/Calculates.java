package com.kbs.pocis.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Calculate;
import com.kbs.pocis.model.Model_Complain;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Calculates extends Fragment {

    int status;
    public Calculates(int type){
        status = type;
    }
    ImageView backk;
    TextView title;
    Button calculate, clear, cal2;
    ConstraintLayout cons;
    RelativeLayout ln_btn;
    TextView total1,total2;
    RecyclerView list;
    List<Model_Complain> model_complains;
    LinearLayout show_table,form_ship,form_good;
    TextInputEditText input_total_tonage, input_cross, input_berthing,input_departure;
    String est_departure, est_berthing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tarif_detail,container,false);

        title = view.findViewById(R.id.title);
        calculate = view.findViewById(R.id.btn_calculate);
        show_table = view.findViewById(R.id.show_table);
        clear = view.findViewById(R.id.btn_clear);
        cal2 = view.findViewById(R.id.btn_cal2);
        ln_btn = view.findViewById(R.id.ln_btn);
        form_ship = view.findViewById(R.id.form_ship);
        form_good = view.findViewById(R.id.form_good);

        input_total_tonage = view.findViewById(R.id.input_total_tonage);
        input_cross = view.findViewById(R.id.input_cross_tonage);
        input_berthing = view.findViewById(R.id.input_bething);
        input_departure = view.findViewById(R.id.input_departure);
        cons = view.findViewById(R.id.ln_goods);
        total1 = view.findViewById(R.id.total1);

        total2 = view.findViewById(R.id.total2);
        list = view.findViewById(R.id.list_tarif_detail);
        model_complains = new ArrayList<>();

        backk = view.findViewById(R.id.btn_back_tarif_detail);
        backk.setOnClickListener(v -> requireActivity().onBackPressed());

        input_berthing.setOnClickListener(v-> ShowDateTime(input_berthing));

        input_departure.setOnClickListener(v-> ShowDateTime2(input_departure));

        kenalanSamaType();

        return view;
    }

    public void getVesselService(String gt_kapal,String est_berthing,String est_departure){
        Call<CallingDetail> call = UserData.i.getService().vesselService(UserData.i.getToken(),"Jasa Kapal",gt_kapal,est_berthing, est_departure);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(getContext(),"vessel_service", data)){
                    assert data != null;
                    Model_Complain.CalculatorResults = data.data.CalculatorResults;
                    double total_1 = 0;
                    double total_2 = 0;
                    for (int i=0; i < data.data.CalculatorResults.size(); i++){
                        if (data.data.CalculatorResults.get(2).tarif_domestik == null){
                            total_1 = Double.parseDouble(data.data.CalculatorResults.get(0).tarif_domestik.trim()) + Double.parseDouble(data.data.CalculatorResults.get(1).tarif_domestik.trim());
                            total_2 = Double.parseDouble(data.data.CalculatorResults.get(0).tarif_internasional.trim()) + Double.parseDouble(data.data.CalculatorResults.get(1).tarif_internasional.trim());
                            String str = new DecimalFormat("#,###,###.##").format(total_1);
                            String str_2 = new DecimalFormat("#,###,###.##").format(total_2);
                            total1.setText(str);
                            total2.setText(str_2);
                        } else {
                            total_2 += Double.parseDouble(data.data.CalculatorResults.get(i).tarif_internasional);
                            String str_2 = new DecimalFormat("#,###,###.##").format(total_2);
                            total2.setText(str_2);
                            total_1 += Double.parseDouble(data.data.CalculatorResults.get(i).tarif_domestik);
                            String str = new DecimalFormat("#,###,###.##").format(total_1);
                            total1.setText(str);
                        }
                    }
                    model_complains = new ArrayList<>();
                    model_complains = data.data.CalculatorResults;
                    Adapter_Calculate adapter_calculate = new Adapter_Calculate(getContext(), model_complains,1);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    list.setLayoutManager(layoutManager);
                    list.setAdapter(adapter_calculate);


                    if (model_complains.size() > 0){
                        ln_btn.setVisibility(View.VISIBLE);
                        calculate.setVisibility(View.GONE);
                        clear.setOnClickListener(v13 -> {
                            show_table.setVisibility(View.GONE);
                            input_berthing.setText("");
                            input_departure.setText("");
                            input_cross.setText("");
                        });
                        cal2.setOnClickListener(v14 -> tableShip());
                    }
                } else {
                    Log.i(TAG, "onResponse: " + "data null");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {

            }
        });
    }

    public void getGoodService(String total_tonnage){
        Call<CallingDetail> call = UserData.i.getService().goodsService(UserData.i.getToken(), total_tonnage);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(getContext(),"good_service", data)){
                    assert data != null;
                    double total_1 = 0;
                    for (int i=0; i < data.data.CalculatorResults.size(); i++) {
                        if (data.data.CalculatorResults.get(i).tariff != null) {
                            try {
                                total_1 += Double.parseDouble(data.data.CalculatorResults.get(i).tariff);
                                String str_2 = new DecimalFormat("#,###,###.##").format(total_1);
                                total2.setText(str_2);
                            } catch (Exception e){
                                total2.setText(null);
                            }
                        }
                    }

                    model_complains = new ArrayList<>();
                    model_complains = data.data.CalculatorResults;
                    Log.i(TAG, "onResponse: " + model_complains.get(0).parameter);
                    Adapter_Calculate adapter_calculate = new Adapter_Calculate(getContext(), model_complains,0);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    list.setLayoutManager(layoutManager);
                    list.setAdapter(adapter_calculate);
                    if (model_complains.size() > 0){
                        ln_btn.setVisibility(View.VISIBLE);
                        calculate.setVisibility(View.GONE);
                        clear.setOnClickListener(v1 -> {
                            show_table.setVisibility(View.GONE);
                            input_total_tonage.setText("");
                        });

                        cal2.setOnClickListener(v12 -> tableGood());
                    }
                } else {
                    Log.i(TAG, "onResponse: " + "data null");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });
    }

    public void tableGood(){
        if (Objects.requireNonNull(input_total_tonage.getText()).toString().isEmpty()){
            MDToast.makeText(requireContext(),"Please Add Tonnage Value", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        } else {
            show_table.setVisibility(View.VISIBLE);
            getGoodService(input_total_tonage.getText().toString());
            total1.setVisibility(View.GONE);
        }
    }

    public void tableShip(){
        if (Objects.requireNonNull(input_cross.getText()).toString().isEmpty() || Objects.requireNonNull(input_departure.getText()).toString().isEmpty()
                || Objects.requireNonNull(input_berthing.getText()).toString().isEmpty()){
            Toasty.error(requireContext(),"Please Add All Value", Toasty.LENGTH_LONG,true).show();
        } else {
            cons.setVisibility(View.GONE);
            show_table.setVisibility(View.VISIBLE);
            getVesselService(input_cross.getText().toString(), est_berthing, est_departure); //default sesuai figma, update kalau API udah ada ( sesuai field API )
        }
    }

    public void kenalanSamaType(){
        switch (status){
            case 0:
                title.setText(R.string.good_Services);
                calculate.setOnClickListener(v ->
                    tableGood()
                );
                break;
            case 1:
                form_good.setVisibility(View.GONE);
                form_ship.setVisibility(View.VISIBLE);
                title.setText(R.string.ship_Services);
                calculate.setOnClickListener(v ->
                    tableShip()
                );
                break;
        }
    }

    void ShowDateTime(TextInputEditText texit){
        Calendar calendar = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener setListener = (view1, hourOfDay, minute, second) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat fr = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss", Locale.ENGLISH);
                est_berthing = fr.format(calendar.getTime());
                Log.i(TAG, "ShowDateTime: berthing" + est_berthing);
                texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
            };
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
            timePickerDialog.show(getChildFragmentManager(), "TimePicker");
        };

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.show(getChildFragmentManager(),"DateDialog");
    }
    void ShowDateTime2(TextInputEditText texit){
        Calendar calendar = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener setListener = (view1, hourOfDay, minute, second) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat fr = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss", Locale.ENGLISH);
                est_departure = fr.format(calendar.getTime());
                Log.i(TAG, "ShowDateTime2: departure" + est_departure);
                texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
            };
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
            timePickerDialog.show(getChildFragmentManager(), "TimePicker");
        };

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.show(getChildFragmentManager(),"DateDialog");
    }
}
