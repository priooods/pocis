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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingShowTemp;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ShowTemplate extends Fragment {

    Button btnPrev, btnNext;
    RecyclerView listtemplate;
    ListTemplate adapter;
    ArrayList<Model_ShowTemplate> model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_template, container, false);

        btnPrev = view.findViewById(R.id.cust_add_form_prevBtn);
        btnNext = view.findViewById(R.id.cust_add_form_nextBtn);


        ShowListTemplate(view);

        btnNext.setOnClickListener(v -> {
            if (model != null){
                GoNextPage();
            } else {
                MDToast.makeText(requireContext(), " Please choose another information", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            }
        });
        btnPrev.setOnClickListener(v -> {
            if (model != null){
                BookingData.i.ShowBookUpdate(model);
            }
            requireActivity().onBackPressed();
        });

        return view;
    }

    public void ShowListTemplate(View view){
        Call<CallingShowTemp> call = UserData.i.getService().getShowTemplate(UserData.i.getToken(),Integer.parseInt(BookingData.i.customerId), BookingData.i.relatedVesel, BookingData.i.contract);
        call.enqueue(new Callback<CallingShowTemp>() {
            @Override
            public void onResponse(@NotNull Call<CallingShowTemp> call, @NotNull Response<CallingShowTemp> response) {
                CallingShowTemp data = response.body();
                if (CallingShowTemp.TreatResponse(getContext(),"tag", data)){
                    assert data != null;
                    model = data.data;
                    if (model == null){
                         MDToast.makeText(requireContext(),"Template No Data", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                    } else {
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
                            requireActivity().onBackPressed();
                        }
                        listtemplate = view.findViewById(R.id.recycle_showtemplate);
                        adapter = new ListTemplate(requireContext(), model);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                        listtemplate.setHasFixedSize(true);
                        listtemplate.setLayoutManager(layoutManager);
                        listtemplate.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingShowTemp> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: => " + t );
            }
        });
    }

    void GoNextPage(){
        if (getOneIsChecked()){
            BookingData.i.ShowBookUpdate(model);
            Log.i("TAG", "list_show_template: " + model);
            Fragment fragment = new SelectTemplate();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            MDToast.makeText(requireContext(), "Please Select Template Type", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
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
            Glide.with(context)
                    .load("http://cigading.ptkbs.co.id/pocis/img/template/"+ model.get(position).image_file)
                    .placeholder(R.color.colorGrey)
                    .error(R.drawable.icon_silang)
                    .override(200, 200)
                    .centerCrop()
                    .into(holder.img);
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