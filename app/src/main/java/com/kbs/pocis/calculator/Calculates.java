package com.kbs.pocis.calculator;

import android.os.Bundle;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

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

        input_departure.setOnClickListener(v-> ShowDateTime(input_departure));


        kenalanSamaType();

        return view;
    }

    void listGoods(){
        model_complains = new ArrayList<>();
        model_complains.add(new Model_Complain(null,"Jasa PFS","100,000 T","600,000,000","",""));
        model_complains.add(new Model_Complain(null,"Jasa Dermaga","100,000 T","107,300,000","",""));
        model_complains.add(new Model_Complain(null,"Jasa Ship Unloader","100,000 T","2,100,000,000","",""));
        Adapter_Calculate adapter_calculate = new Adapter_Calculate(getContext(), model_complains,0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter_calculate);
    }

    void listShip(){
        model_complains = new ArrayList<>();
        //default sesuai figma, update kalau API udah ada ( sesuai field API )
        model_complains.add(new Model_Complain("Jasa Pandu","Jasa PFS","1 LS","600,000,000","12,842,280.00","4,500.00"));
        model_complains.add(new Model_Complain("Jasa Tunda","Jasa Dermaga","1 LS","107,300,000","24,993,500.00","13,300.00"));
        model_complains.add(new Model_Complain("Jasa Tambat","Jasa Ship Unloader","1 LS","2,100,000,000","12,000,000.00","10,800.00"));
        Adapter_Calculate adapter_calculate = new Adapter_Calculate(getContext(), model_complains,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter_calculate);
    }

    public void tableGood(){
        if (Objects.requireNonNull(input_total_tonage.getText()).toString().isEmpty()){
            Toasty.error(requireContext(),"Please Add Tonage Value", Toasty.LENGTH_LONG,true).show();
        } else {
            show_table.setVisibility(View.VISIBLE);
            listGoods();
            total1.setVisibility(View.GONE);
            total2.setText("2,807,300,000"); //default sesuai figma, update kalau API udah ada ( sesuai field API )
        }
    }

    public void tableShip(){
        if (Objects.requireNonNull(input_cross.getText()).toString().isEmpty() || Objects.requireNonNull(input_departure.getText()).toString().isEmpty()
                || Objects.requireNonNull(input_berthing.getText()).toString().isEmpty()){
            Toasty.error(requireContext(),"Please Add All Value", Toasty.LENGTH_LONG,true).show();
        } else {
            cons.setVisibility(View.GONE);
            show_table.setVisibility(View.VISIBLE);
            listShip();
            total1.setText("49,835,780.00"); //default sesuai figma, update kalau API udah ada ( sesuai field API )
            total2.setText("28,600.00"); //default sesuai figma, update kalau API udah ada ( sesuai field API )
        }
    }

    public void kenalanSamaType(){
        switch (status){
            case 0:
                title.setText(R.string.good_Services);
                calculate.setOnClickListener(v -> {
                    tableGood();
                    if (model_complains.size() > 0){
                        ln_btn.setVisibility(View.VISIBLE);
                        calculate.setVisibility(View.GONE);
                        clear.setOnClickListener(v1 -> {
                            show_table.setVisibility(View.GONE);
                            input_total_tonage.setText("");
                        });

                        cal2.setOnClickListener(v12 -> tableGood());
                    }
                });


                break;
            case 1:
                form_good.setVisibility(View.GONE);
                form_ship.setVisibility(View.VISIBLE);
                title.setText(R.string.ship_Services);
                calculate.setOnClickListener(v -> {
                    tableShip();
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
                });
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
                texit.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(calendar.getTime()));
            };
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(setListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
            timePickerDialog.show(getChildFragmentManager(), "TimePicker");
        };

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
////        datePickerDialog.setMinDate(calendar);
//        calendar.add(Calendar.MONTH, -2);
//        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.show(getChildFragmentManager(),"DateDialog");
    }
}
