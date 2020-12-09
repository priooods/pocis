package com.kbs.pocis.createboking;

import android.Manifest;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
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
import com.kbs.pocis.adapter.createbooking.Adapter_AddForm;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CreateBok;
import com.kbs.pocis.service.createbooking.CallingList;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kbs.pocis.createboking.UploadDocument.FileUtils.TAG;

public class CustomerAddForm extends Fragment {

    TextView value_customerType, value_relatedVesel, value_contract;
    LinearLayout  expanded_vesel, expanded_contract, expanded_type;
    ImageView icon_type, icon_vesel, icon_contract;
    CardView card_vesel, card_contract, card_type;
    CheckBox yes_vesel, yes_contract, no_vesel, no_contract, type_general, type_agent;
    Button nextButton;

    Adapter_AddForm adapter_addForm;
    RecyclerView list_costumertype;
    public CallingList data;
    String CustomerType;

    private static int PRIVATE_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_add_form, container, false);

        // Sebab design yang beraneka ragam dan kompleks,
        // maka semua function dibuat manual untuk mendapatkan UI yang sama persis dan function yg sesuai

        list_costumertype = view.findViewById(R.id.list_customertype);

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
        card_type = view.findViewById(R.id.c1);
        card_contract = view.findViewById(R.id.c3);

        // expanded layout
        expanded_contract = view.findViewById(R.id.customerAdd_expand_contract);
        expanded_vesel = view.findViewById(R.id.customerAdd_expand_relatedvesel);
        expanded_type = view.findViewById(R.id.customerAdd_expand_type);

        //checkBoxes
        yes_vesel = view.findViewById(R.id.check_yes_customerAdd_relatedvesel);
        yes_contract = view.findViewById(R.id.check_yes_cutomerAdd_contract);
        no_contract = view.findViewById(R.id.check_no_customerAdd_contract);
        no_vesel = view.findViewById(R.id.check_no_customerAdd_relatedvesel);
        type_agent = view.findViewById(R.id.check_customerAdd_agent);
        type_general = view.findViewById(R.id.check_customerAdd_general);

        //nextButton
        nextButton = view.findViewById(R.id.cust_add_form_nextBtn);

        ExpandedLayouts();
        //CheckBox pada setiap expanded View
        CheckBoxesRelatedVessel();
        CheckBoxesContract();

        //Next Page
        if (BookingData.isExist()){
            BookingData bdata = BookingData.i;
            CustomerType = bdata.customerType;
            value_customerType.setText(CustomerType);
            value_contract.setText(bdata.contract);
            value_relatedVesel.setText(bdata.relatedVesel);
            Log.i("li","BookingData Exists at the First! "+(CustomerType!=null?CustomerType:"NULL"));
        }else{
            BookingData.i = new BookingData();
        }

        Log.i(TAG, "onCreateView: => " + UserData.i.getToken());
        CallingAPI();
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

        icon_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded_type.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(card_type, new AutoTransition());
                    expanded_type.setVisibility(View.VISIBLE);
                    icon_type.setImageResource(R.drawable.icon_top);
                } else {
                    TransitionManager.beginDelayedTransition(card_type, new AutoTransition());
                    expanded_type.setVisibility(View.GONE);
                    icon_type.setImageResource(R.drawable.icon_botom);
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
                    value_relatedVesel.setText("N");
                    yes_vesel.setChecked(false);
                } else {
                    value_relatedVesel.setText("Y");
                    yes_vesel.setChecked(true);
                }
            }
        });

        yes_vesel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    value_relatedVesel.setText("Y");
                    no_vesel.setChecked(false);
                } else {
                    value_relatedVesel.setText("N");
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
                    value_contract.setText("N");
                    yes_contract.setChecked(false);
                } else {
                    value_contract.setText("Y");
                    yes_contract.setChecked(true);
                }
            }
        });

        yes_contract.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    value_contract.setText("Y");
                    no_contract.setChecked(false);
                } else {
                    value_contract.setText("N");
                    no_contract.setChecked(true);
                }
            }
        });
    }


    //CallingAPi
    public void CallingAPI(){
        Call<CreateBok> call;
        call = UserData.i.getService().getCustumerType(UserData.i.getToken());
        if (call == null){
            Log.i(TAG, "CallingAPI: => Failure");
        } else {
            call.enqueue(new Callback<CreateBok>() {
                @Override
                public void onResponse(Call<CreateBok> call, Response<CreateBok> response) {
                    CreateBok createBok = response.body();
                    if (createBok.TreatResponse(getContext(), "costumer_type", createBok)) {
                        List<CallingList> commodities = new ArrayList<>();
                        if (CustomerType != null) {
                            for (CallingList dataCreate : createBok.data) {
                                //Log.i("tag",dataCreate.name+"="+CustomerType);
                                if (dataCreate.name.equals(CustomerType)) {
                                    //Log.i("tag",dataCreate.name+"="+CustomerType+"ADD");
                                    data = dataCreate;
                                }
                                commodities.add(dataCreate);
                            }
                        } else {
                            for (CallingList dataCreate : createBok.data) {
                                commodities.add(dataCreate);
                            }
                        }
                        if (data == null)
                            data = createBok.data[0];
                        value_customerType.setText(data.name);
                        adapter_addForm = new Adapter_AddForm(getContext(), commodities, value_customerType, CustomerAddForm.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        list_costumertype.setLayoutManager(layoutManager);
                        list_costumertype.setAdapter(adapter_addForm);
                    }
                }

                @Override
                public void onFailure(Call<CreateBok> call, Throwable t) {
                    Log.e(TAG, "onFailure: = " + t);
                }
            });
        }
    }


    //function untuk NextPage
    public void NextPage(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data!=null) {
                    BookingData.i.setCustumer(
                            String.valueOf(data.id),
                            data.name,
                            value_relatedVesel.getText().toString(),
                            value_contract.getText().toString()
                    );
                    Log.i(TAG, "onClick: " + BookingData.i.customerType);
                    Fragment fragment = new ShowTemplate();
                    Log.i(TAG, "onClick: => " + BookingData.i.customerId);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    //pesan harap isi customer type
                    Toasty.error(getContext(), "Please Check All Booking Information", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    //Permission Storage dari sini sampai bawah Di CustommerAddFrom
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