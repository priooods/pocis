package com.kbs.pocis.item;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.createboking.AddComodity;
import com.kbs.pocis.model.Model_Commodity;

import es.dmoral.toasty.Toasty;

public class FormCommodity extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    TextInputEditText inputConsigne, inputWeight, inputPackage, inpuComodity;
    Button cancel, add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_form_commodity, container,false);

        inputConsigne = view.findViewById(R.id.input_consigne_commodity);
        inpuComodity = view.findViewById(R.id.input_comodity_commodity);
        inputWeight = view.findViewById(R.id.input_weight_commodity);
        inputPackage = view.findViewById(R.id.input_packages_commodity);

        cancel = view.findViewById(R.id.cancel_form_comodity);
        add = view.findViewById(R.id.add_form_comodity);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickNext(getContext());
            }
        });

        return view;
    }

    private void ClickNext(Context v){
        Model_Commodity model_commodity = new Model_Commodity();
        model_commodity.setCommodity(inpuComodity.getText().toString());
        model_commodity.setConsigne(inputConsigne.getText().toString());
        model_commodity.setPackages(inputPackage.getText().toString());
        model_commodity.setWeight(inputWeight.getText().toString());
        if (model_commodity.getCommodity().isEmpty() || model_commodity.getConsigne().isEmpty() ||
                model_commodity.getPackages().isEmpty() || model_commodity.getWeight().isEmpty()){
            Toasty.error(v, "Harap Lengkapi Semua From !", Toasty.LENGTH_SHORT, true).show();
        } else {
//            Bundle args = new Bundle();
//            args.putString("consigne", inputConsigne.getText().toString());
//            args.putString("comodity", inpuComodity.getText().toString());
//            args.putString("package", inputPackage.getText().toString());
//            args.putString("weight", inputWeight.getText().toString());
            Fragment fragment = new AddComodity();
//            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
