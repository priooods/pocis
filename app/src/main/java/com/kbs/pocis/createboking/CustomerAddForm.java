package com.kbs.pocis.createboking;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.createbooking.Adapter_AddForm;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CreateBok;
import com.kbs.pocis.service.createbooking.CallingList;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


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
    String related_vessel = "Y";
    String contract = "N";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_add_form, container, false);

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
            BookingData b_data = BookingData.i;
            CustomerType = b_data.customerType;
            value_customerType.setText(CustomerType);
            if (b_data.contract.equals("Y")){
                value_contract.setText(R.string.yes);
            } else {
                value_contract.setText(R.string.no);
            }
            if (b_data.relatedVesel.equals("Y")){
                value_relatedVesel.setText(R.string.yes);
            } else {
                value_relatedVesel.setText(R.string.no);
            }
            if (b_data.status_change)
                b_data.status_change = false;

            Log.i("li","BookingData Exists at the First! "+(CustomerType!=null?CustomerType:"NULL"));
        }else{
            BookingData.i = new BookingData();
        }

        Log.i(TAG, "token: => " + UserData.i.getToken());
        CallingAPI();
        NextPage();
        return view;
    }

    //function untuk setting expand
    public void ExpandedLayouts(){
        icon_vesel.setOnClickListener(v -> {
            if (expanded_vesel.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(card_vesel, new AutoTransition());
                expanded_vesel.setVisibility(View.VISIBLE);
                icon_vesel.setImageResource(R.drawable.icon_top);
            } else {
                TransitionManager.beginDelayedTransition(card_vesel, new AutoTransition());
                expanded_vesel.setVisibility(View.GONE);
                icon_vesel.setImageResource(R.drawable.icon_botom);
            }
        });
        icon_contract.setOnClickListener(v -> {
            if (expanded_contract.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(card_contract, new AutoTransition());
                expanded_contract.setVisibility(View.VISIBLE);
                icon_contract.setImageResource(R.drawable.icon_top);
            } else {
                TransitionManager.beginDelayedTransition(card_contract, new AutoTransition());
                expanded_contract.setVisibility(View.GONE);
                icon_contract.setImageResource(R.drawable.icon_botom);
            }
        });
        icon_type.setOnClickListener(v -> {
            if (expanded_type.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(card_type, new AutoTransition());
                expanded_type.setVisibility(View.VISIBLE);
                icon_type.setImageResource(R.drawable.icon_top);
            } else {
                TransitionManager.beginDelayedTransition(card_type, new AutoTransition());
                expanded_type.setVisibility(View.GONE);
                icon_type.setImageResource(R.drawable.icon_botom);
            }
        });
    }

    //function untuk CheckBox
    public void CheckBoxesRelatedVessel(){
        no_vesel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                value_relatedVesel.setText(R.string.no);
                related_vessel = "N";
                yes_vesel.setChecked(false);
            } else {
                value_relatedVesel.setText(R.string.yes);
                related_vessel = "Y";
                yes_vesel.setChecked(true);
            }
        });
        yes_vesel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                value_relatedVesel.setText(R.string.yes);
                related_vessel = "Y";
                no_vesel.setChecked(false);
            } else {
                value_relatedVesel.setText(R.string.no);
                related_vessel = "N";
                no_vesel.setChecked(true);
            }
        });

        no_vesel.setOnClickListener(v->BookingData.i.checkChange(true));
        yes_vesel.setOnClickListener(v->BookingData.i.checkChange(true));
    }

    public void CheckBoxesContract(){
        no_contract.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                value_contract.setText(R.string.no);
                contract = "N";
                yes_contract.setChecked(false);
            } else {
                value_contract.setText(R.string.yes);
                contract = "Y";
                yes_contract.setChecked(true);
            }
        });
        yes_contract.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                value_contract.setText(R.string.yes);
                contract = "Y";
                no_contract.setChecked(false);
            } else {
                value_contract.setText(R.string.no);
                contract = "N";
                no_contract.setChecked(true);
            }
        });

        no_contract.setOnClickListener(v->BookingData.i.checkChange(true));
        yes_contract.setOnClickListener(v->BookingData.i.checkChange(true));
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
                public void onResponse(@NotNull Call<CreateBok> call, @NotNull Response<CreateBok> response) {
                    CreateBok createBok = response.body();
                    if (Calling.TreatResponse(getContext(), "costumer_type", createBok)) {
                        List<CallingList> commodities = new ArrayList<>();
                        assert createBok != null;
                        if (CustomerType != null) {
                            for (CallingList dataCreate : createBok.data) {
                                if (dataCreate.name.equals(CustomerType)) {
                                    data = dataCreate;
                                }
                                commodities.add(dataCreate);
                            }
                        } else {
                            Collections.addAll(commodities, createBok.data);
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
                public void onFailure(@NotNull Call<CreateBok> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: = " + t);
                }
            });
        }
    }

    //function untuk NextPage
    public void NextPage(){
        nextButton.setOnClickListener(v -> {
            if (data!=null) {
                BookingData.i.setCustumer(
                        String.valueOf(data.id),
                        data.name,
                        related_vessel,
                        contract
                );

                Log.i(TAG, "customer_type: " + BookingData.i.customerType);
                Log.i(TAG, "related_vessel: " + related_vessel);
                Log.i(TAG, "contract: " + contract);
                Log.i(TAG, "customer_id: => " + BookingData.i.customerId);
                Log.i(TAG, "status_change: " + BookingData.i.status_change);
                Fragment fragment = new ShowTemplate();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }else{
                MDToast.makeText(requireContext(), "Please Check All Booking Information", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            }
        });
    }

}