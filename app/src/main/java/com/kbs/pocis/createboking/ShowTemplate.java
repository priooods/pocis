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
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CreateBok;
import com.kbs.pocis.service.createbooking.CreateTemp;
import com.kbs.pocis.service.onlinebooking.CallingData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kbs.pocis.createboking.UploadDocument.FileUtils.TAG;

public class ShowTemplate extends Fragment {

    Button btnPrev, btnNext;
    RecyclerView listtemplate;
    ListTemplate adapter;
    List<Model_ShowTemplate> model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_template, container, false);

        listtemplate = view.findViewById(R.id.recycle_showtemplate);
        btnPrev = view.findViewById(R.id.cust_add_form_prevBtn);
        btnNext = view.findViewById(R.id.cust_add_form_nextBtn);

        //TODO ini sebelumnya lu kan check list ketika back.
        // karena sebelumnya pake model manual jadi gua bingung update ketika model udah ganti dari API
        // jadi belum ke save ke singletoon nya. masih harus check lagi ketika back / next di show_template
//        int i = 0;
//        if (BookingData.isExist()){
//            Log.i("call","id = "+BookingData.i.customerId);
//            // Load Data
//            if (BookingData.i.template != null) {
//                for (BookingData.BookTemplate temp : BookingData.i.template) {
//                    for (; i < model.size(); i++) {
//                        if (temp.code == model.get(i).getId()) {
//                            model.get(i).setCheck(true);
//                            break;
//                        }
//                    }
//                }
//            }
//        }else{
//            // Error Back to CustomerAddForm
//        }
        ShowListTemplate();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null){
                    GoNextPage();
                } else {
                    Toast.makeText(getContext(), " Please back again and choose another information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (model != null){
                    BookingData.i.SetBookTemplate(model);
                }
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    void ShowListTemplate(){
        Call<Model_ShowTemplate> call = UserData.i.getService().getShowTemplate(UserData.i.getToken(),BookingData.i.customerId, BookingData.i.relatedVesel, BookingData.i.contract);
        call.enqueue(new Callback<Model_ShowTemplate>() {
            @Override
            public void onResponse(Call<Model_ShowTemplate> call, Response<Model_ShowTemplate> response) {
                Model_ShowTemplate data = response.body();
                if (data.TreatResponse(getContext(),"tag", data)){
                    if (data.data == null){
                         Toasty.error(getContext(),"List Kosong", Toasty.LENGTH_SHORT, true).show();
                    } else {
                        adapter = new ListTemplate(getContext(), data);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        listtemplate.setLayoutManager(layoutManager);
                        listtemplate.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_ShowTemplate> call, Throwable t) {
                Log.i(TAG, "onFailure: => " + t );
            }
        });
    }

    void GoNextPage(){
        if (getOneIsChecked()){
            BookingData.i.SetBookTemplate(model);
            Log.i("TAG", "lis: " + model);
            Fragment fragment = new SelectTemplate();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            Toasty.error(getContext(), "Please Selected Template Type", Toast.LENGTH_SHORT, true).show();
        }

    }

    //ini untuk check apakah checkbox kosong atau engga
    boolean getOneIsChecked() {
        for(Model_ShowTemplate m : model) {
            if (m.getCheck())
                return true;
        }
        return false;
    }

    //ini adapter untuk list Show Template
    public static class ListTemplate extends RecyclerView.Adapter<ListTemplate.Vholder>{

        Context context;
        Model_ShowTemplate model;
        ArrayList<Integer> integers = new ArrayList<>();

        public ListTemplate(Context context, Model_ShowTemplate model) {
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
            holder.id.setText(model.data.get(position).code);
            holder.name.setText(model.data.get(position).display_desc_header);
            holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        integers.add(model.data.get(position).id);
                        Log.i(TAG, "onCheckedChanged: => " + integers);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return model.data.size();
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