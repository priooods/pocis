package com.kbs.pocis.createboking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingList;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AddComodity extends Fragment {

    Button next, prev, addcommodity_two, addcommodity_one;
    AutoCompleteTextView input_comdity, input_consigne;
    TextInputEditText input_weigth,input_package;
    LinearLayout line_addcommodity_one;
    RelativeLayout line_addcommodity_two;

    RecyclerView recyclerView;
    ListComoodity listComoodity;

    ArrayList<Model_Commodity> model_commodity;
    Call<List<CallingList>> call;

    CallingList consigne, commodity;
    int idConsigne, idCommodity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_comodity, container, false);

        next = view.findViewById(R.id.add_commodity_nextBtn);
        prev = view.findViewById(R.id.add_commodity_prevBtn);
        recyclerView = view.findViewById(R.id.recycle_listcomodity);
        line_addcommodity_one = view.findViewById(R.id.line_addcommodity_one);
        line_addcommodity_two = view.findViewById(R.id.line_addcommodity_two);
        addcommodity_two = view.findViewById(R.id.add_commodity_btnUploadtwo);
        addcommodity_one = view.findViewById(R.id.add_commodity_btnUpload);

        addcommodity_one.setOnClickListener(v -> AddCommodityNya(getContext()));

        addcommodity_two.setOnClickListener(v -> AddCommodityNya(getContext()));
        if (BookingData.isExist()){
            if (BookingData.i.commodity != null){
                // Already Opened
                model_commodity = BookingData.i.commodity;
            }else
            if (model_commodity == null){
                model_commodity = new ArrayList<>();
            }
        }

        //check file booking when user change again show templates
        if (BookingData.i.file != null){
            Log.i(TAG, "check_file_booking: " + BookingData.i.file);
        } else {
            Log.i(TAG, "check_file_booking: " + "Nothing File");
        }

        ButtonFunction();
        SettList(model_commodity);

        return view;
    }

    void ListingList(String sequence, boolean status, Context context, AutoCompleteTextView autoCompleteTextView){
        if (status) {
           call  = UserData.i.getService().getConsignee(sequence);
        } else {
            call  = UserData.i.getService().getCommodityType(sequence);
        }
        call.enqueue(new Callback<List<CallingList>>() {
            @Override
            public void onResponse(@NotNull Call<List<CallingList>> call, @NotNull Response<List<CallingList>> response) {
                List<CallingList> createBok = response.body();
                assert createBok != null;
                if (createBok.size()>0) {
                    String[] arr = new String[createBok.size()];
                    if (status) {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "onResponse: list =>  " + createBok.get(i).name);
                            arr[i] = createBok.get(i).name;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.model_spiner, R.id.val_spiner, arr);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(2);
                        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                            //TODO Getting commodity_id
                            Log.i(TAG, "onItemClick in consigne: => " + createBok.get(position).id);
                            idConsigne = createBok.get(position).id;
                            consigne = createBok.get(position);
                        });
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            Log.i(TAG, "onResponse: list =>  " + createBok.get(i).desc);
                            arr[i] = createBok.get(i).desc;
                        }
                        Log.i(TAG, "onResponse: list finish");
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.model_spiner, R.id.val_spiner, arr);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(2);
                        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                            //TODO Getting m_custommer_id
                            idCommodity = createBok.get(position).id;
                            commodity = createBok.get(position);
                        });
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CallingList>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t );
            }
        });
    }

    public void AddCommodityNya (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_form_commodity, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(false);
        dialogFragment.setContentView(view);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        input_weigth = view.findViewById(R.id.input_weight_commodity);
        input_package = view.findViewById(R.id.input_packages_commodity);
        input_consigne = view.findViewById(R.id.input_consigne_commodity);
        input_comdity = view.findViewById(R.id.input_comodity_commodity);
        input_consigne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>=2){
                    ListingList(s.toString(),true, context, input_consigne);
                } else {
                    idConsigne = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_comdity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>=2){
                    ListingList(s.toString(), false, context, input_comdity);
                } else {
                    idCommodity = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button btn_close = view.findViewById(R.id.cancel_form_comodity);
        Button btn_go = view.findViewById(R.id.add_form_comodity);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_go.setOnClickListener(v -> {
            if (input_comdity.getText().toString().isEmpty() || input_consigne.getText().toString().isEmpty() ||
                    Objects.requireNonNull(input_weigth.getText()).toString().isEmpty() || Objects.requireNonNull(input_package.getText()).toString().isEmpty()){
                MDToast.makeText(context, "You Must Add all Form !", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            } else if (idCommodity == 0){
                MDToast.makeText(requireContext(),"Commodity Type not Valid !" , MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            } else if (idConsigne == 0) {
                MDToast.makeText(requireContext(),"Consignee Type not Valid !" , MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            } else {
                String weight = input_weigth.getText().toString();
                String pack = input_package.getText().toString();
                model_commodity.add(new Model_Commodity(consigne,commodity,pack,weight));
                Log.i("commodity_add", commodity.name+" "+pack+" "+weight+" "+ consigne.id);
                SettList(model_commodity);

                dialogFragment.dismiss();
            }
        });
        dialogFragment.show();
    }

    public void SettList(ArrayList<Model_Commodity> commodity){
        if (commodity != null && commodity.size() > 0){
            line_addcommodity_two.setVisibility(View.VISIBLE);
            line_addcommodity_one.setVisibility(View.GONE);
            listComoodity = new ListComoodity(getContext(), commodity);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(listComoodity);
        }
    }

    public void ButtonFunction(){
        prev.setOnClickListener(v -> {
            BookingData.i.commodity = model_commodity;
            requireActivity().onBackPressed();
        });

        next.setOnClickListener(v -> {
            if (BookingData.i.checkVesselInfoSkip()){
                BookingData.i.vessel = new BookingData.VesselData();
                BookingData.i.commodity = null;
                Fragment fragment = new Summary();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                BookingData.i.commodity = model_commodity;
                Fragment fragment = new VesselInformation();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public class ListComoodity extends RecyclerView.Adapter<ListComoodity.vHolder>{
        Context context;
        List<Model_Commodity> model_commodities;

        public ListComoodity(Context context, List<Model_Commodity> model_commodities) {
            this.context = context;
            this.model_commodities = model_commodities;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_add_commodity, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.weight.setText(model_commodities.get(position).weight + " Tonage");
            holder.consigne.setText(model_commodities.get(position).consigne.name);
            holder.comodity.setText(model_commodities.get(position).commodity.desc);
            holder.packages.setText(model_commodities.get(position).packages + " Package");

            holder.delete.setOnClickListener(v -> {
                model_commodities.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position, model_commodities.size());
                if (model_commodities.size() == 0){
                    line_addcommodity_one.setVisibility(View.VISIBLE);
                    line_addcommodity_two.setVisibility(View.GONE);
                }
            });

        }

        @Override
        public int getItemCount() {
            return model_commodities.size();
        }

        public class vHolder extends RecyclerView.ViewHolder{

            TextView consigne, weight, comodity, packages, delete;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                consigne = itemView.findViewById(R.id.model_consigne_comodity);
                weight = itemView.findViewById(R.id.model_weight_comodity);
                comodity = itemView.findViewById(R.id.model_comodity_comodity);
                packages = itemView.findViewById(R.id.model_package_comodity);
                delete = itemView.findViewById(R.id.model_delete_comodity);

            }
        }


    }

}