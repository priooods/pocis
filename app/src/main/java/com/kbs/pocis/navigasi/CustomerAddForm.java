package com.kbs.pocis.navigasi;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kbs.pocis.R;

public class CustomerAddForm extends Fragment {

    TextView value_customerType, value_relatedVesel, value_contract;
    LinearLayout  expanded_vesel, expanded_contract;
    ImageView icon_type, icon_vesel, icon_contract;
    CardView card_vesel, card_contract;
    CheckBox yes_vesel, yes_contract, no_vesel, no_contract;
    Button nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_add_form, container, false);

        // Sebab design yang beraneka ragam dan kompleks,
        // maka semua function dibuat manual untuk mendapatkan UI yang sama persis

        //value form
        value_customerType = view.findViewById(R.id.cutomerAdd_customertype_value);
        value_relatedVesel = view.findViewById(R.id.cutomerAdd_relatedvesel_value);
        value_contract = view.findViewById(R.id.customerAdd_contract_value);

        // icon untuk expanded
        icon_type = view.findViewById(R.id.customerAdd_customertype_icon);
        icon_vesel = view.findViewById(R.id.cutomerAdd_relatedvesel_icon);
        icon_contract = view.findViewById(R.id.customerAdd_contract_icon);

        // cardView
        card_vesel = view.findViewById(R.id.c2);
        card_contract = view.findViewById(R.id.c3);

        // expanded layout
        expanded_contract = view.findViewById(R.id.customerAdd_expand_contract);
        expanded_vesel = view.findViewById(R.id.customerAdd_expand_relatedvesel);

        //checkBoxes
        yes_vesel = view.findViewById(R.id.check_yes_customerAdd_relatedvesel);
        yes_contract = view.findViewById(R.id.check_yes_cutomerAdd_contract);
        no_contract = view.findViewById(R.id.check_no_customerAdd_contract);
        no_vesel = view.findViewById(R.id.check_no_customerAdd_relatedvesel);

        //nextButton
        nextButton = view.findViewById(R.id.cust_add_form_nextBtn);

        ExpandedLayouts();
        //CheckBox pada setiap expanded View
        CheckBoxesRelatedVessel();
        CheckBoxesContract();
        NextPage();
        return view;
    }

    //function untuk setting expand
    public void ExpandedLayouts(){
        icon_vesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded_vesel.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(card_vesel, new AutoTransition());
                    expanded_vesel.setVisibility(View.VISIBLE);
                    icon_vesel.setImageResource(R.drawable.icon_top);
                } else {
                    TransitionManager.beginDelayedTransition(card_vesel, new AutoTransition());
                    expanded_vesel.setVisibility(View.GONE);
                    icon_vesel.setImageResource(R.drawable.icon_botom);
                }
            }
        });

        icon_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded_contract.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(card_contract, new AutoTransition());
                    expanded_contract.setVisibility(View.VISIBLE);
                    icon_contract.setImageResource(R.drawable.icon_top);
                } else {
                    TransitionManager.beginDelayedTransition(card_contract, new AutoTransition());
                    expanded_contract.setVisibility(View.GONE);
                    icon_contract.setImageResource(R.drawable.icon_botom);
                }
            }
        });
    }

    //function untuk CheckBox
    public void CheckBoxesRelatedVessel(){
        no_vesel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    value_relatedVesel.setText("No");
                    yes_vesel.setChecked(false);
                } else {
                    value_relatedVesel.setText("Yes");
                    yes_vesel.setChecked(true);
                }
            }
        });

        yes_vesel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    value_relatedVesel.setText("Yes");
                    no_vesel.setChecked(false);
                } else {
                    value_relatedVesel.setText("No");
                    no_vesel.setChecked(true);
                }
            }
        });
    }
    public void CheckBoxesContract(){
        no_contract.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    value_contract.setText("No");
                    yes_contract.setChecked(false);
                } else {
                    value_contract.setText("Yes");
                    yes_contract.setChecked(true);
                }
            }
        });

        yes_contract.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    value_contract.setText("Yes");
                    no_contract.setChecked(false);
                } else {
                    value_contract.setText("No");
                    no_contract.setChecked(true);
                }
            }
        });
    }

    public void NextPage(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kirim data pada form ini
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.relatedvessel), value_relatedVesel.getText().toString());
                bundle.putString(getString(R.string.contract), value_contract.getText().toString());
                bundle.putString(getString(R.string.customertype), value_customerType.getText().toString());

                Fragment fragment = new ShowTemplate();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}