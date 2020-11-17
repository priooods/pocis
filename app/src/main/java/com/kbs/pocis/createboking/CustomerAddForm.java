package com.kbs.pocis.createboking;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.service.BookingData;

import static com.kbs.pocis.createboking.UploadDocument.FileUtils.TAG;

public class CustomerAddForm extends Fragment {

    TextView value_customerType, value_relatedVesel, value_contract;
    LinearLayout  expanded_vesel, expanded_contract;
    ImageView icon_type, icon_vesel, icon_contract;
    CardView card_vesel, card_contract;
    CheckBox yes_vesel, yes_contract, no_vesel, no_contract;
    Button nextButton;

    private static int PRIVATE_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_add_form, container, false);

        // Sebab design yang beraneka ragam dan kompleks,
        // maka semua function dibuat manual untuk mendapatkan UI yang sama persis dan function yg sesuai

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

        //Next Page
        if (BookingData.isExist()){
            Log.i("li","BookingData Exists at the First!");
            BookingData data = BookingData.i;
            value_customerType.setText(data.customerType);
            value_contract.setText(data.contract);
            value_relatedVesel.setText(data.relatedVesel);
        }else{
            BookingData.i = new BookingData();
        }
        NextPage();

        //Permission
        Permision();
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

    //function untuk NextPage
    public void NextPage(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingData.i.setCustumer(
                        value_customerType.getText().toString(),
                        value_relatedVesel.getText().toString(),
                        value_contract.getText().toString()
                );
                Log.i(TAG, "onClick: " + BookingData.i.customerType);
                Fragment fragment = new ShowTemplate();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    //TODO Permission Storage dari sini sampai bawah Di CustommerAddFrom
    public void Permision(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            } else {
                IjinStorage();
            }
        }

    }

    public void IjinStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(getContext()).setTitle("Membutuhkan Ijin")
                    .setMessage("Dibutuhkan ijin untuk menemukan File PDF di storage anda")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PRIVATE_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Ijin Berhasil Diberikan", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Ijin Gagal Diberikan", Toast.LENGTH_LONG).show();
            }
        }
    }



}