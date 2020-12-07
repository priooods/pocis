package com.kbs.pocis.createboking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingShowTemp;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kbs.pocis.createboking.UploadDocument.FileUtils.TAG;

public class ShowTemplate extends Fragment {

    Button btnPrev, btnNext;
    RecyclerView listtemplate;
    ListTemplate adapter;
    ArrayList<Model_ShowTemplate> model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_template, container, false);

        listtemplate = view.findViewById(R.id.recycle_showtemplate);
        btnPrev = view.findViewById(R.id.cust_add_form_prevBtn);
        btnNext = view.findViewById(R.id.cust_add_form_nextBtn);

        ShowListTemplate();

        btnNext.setOnClickListener(v -> {
            if (model != null){
                GoNextPage();
            } else {
                Toast.makeText(getContext(), " Please back again and choose another information", Toast.LENGTH_SHORT).show();
            }
        });
        btnPrev.setOnClickListener(v -> {
            if (model != null){
                BookingData.i.ShowBookUpdate(model);
            }
            getActivity().onBackPressed();
        });

        return view;
    }

    void ShowListTemplate(){
        Call<CallingShowTemp> call = UserData.i.getService().getShowTemplate(UserData.i.getToken(),BookingData.i.customerId, BookingData.i.relatedVesel, BookingData.i.contract);
        call.enqueue(new Callback<CallingShowTemp>() {
            @Override
            public void onResponse(Call<CallingShowTemp> call, Response<CallingShowTemp> response) {
                CallingShowTemp data = response.body();
                if (data.TreatResponse(getContext(),"tag", data)){
                    model = data.data;
                    if (model == null){
                         Toasty.error(getContext(),"List Kosong", Toasty.LENGTH_SHORT, true).show();
                    } else {
                        //TODO SELESAI SHOW_TEMPLATE
                        int i = 0;
                        if (BookingData.isExist()){
                            Log.i("call","id = "+BookingData.i.customerId);
                            // Load Data
                            if (BookingData.i.template != null) {
                                for (BookingData.BookTemplate temp : BookingData.i.template) {
                                    for (; i < model.size(); i++) {
                                        if (temp.id == model.get(i).id) {
                                            model.get(i).checked = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }else{
                            // Error Back to CustomerAddForm
                        }

                        adapter = new ListTemplate(getContext(), model);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        listtemplate.setLayoutManager(layoutManager);
                        listtemplate.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CallingShowTemp> call, Throwable t) {
                Log.i(TAG, "onFailure: => " + t );
            }
        });
    }

    void GoNextPage(){
        if (getOneIsChecked()){
            BookingData.i.ShowBookUpdate(model);
            Log.i("TAG", "lis: " + model);
            Fragment fragment = new SelectTemplate();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            Toasty.error(getContext(), "Please Selecting Template Type", Toast.LENGTH_SHORT, true).show();
        }
    }

    //ini untuk check apakah checkbox kosong atau engga
    boolean getOneIsChecked() {
        for(Model_ShowTemplate m : model) {
            if (m.checked)
                return true;
        }
        return false;
    }

    //ini adapter untuk list Show Template
    public static class ListTemplate extends RecyclerView.Adapter<ListTemplate.Vholder>{

        Context context;
        ArrayList<Model_ShowTemplate> model;

        public ListTemplate(Context context, ArrayList<Model_ShowTemplate> model) {
            this.context = context;
            this.model = model;
        }

        @NonNull
        @Override
        public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_showtemplate,parent, false);
            return new Vholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Vholder holder, final int position) {
            holder.id.setText(model.get(position).code);
            holder.name.setText(model.get(position).display_desc_header);
            holder.status.setChecked(model.get(position).checked);
            holder.status.setOnCheckedChangeListener((buttonView, isChecked) ->
                    model.get(position).checked = isChecked
            );
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public static class Vholder extends RecyclerView.ViewHolder {

            ImageView img;
            TextView name, id;
            CheckBox status;

            public Vholder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.imgshowtemplate);
                name = itemView.findViewById(R.id.name_showtemplate);
                id = itemView.findViewById(R.id.idshowtemplate);
                status = itemView.findViewById(R.id.check_showtemplate);
            }
        }
    }
}