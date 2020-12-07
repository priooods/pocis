package com.kbs.pocis.createboking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingSelectTemp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SelectTemplate extends Fragment {

    CardView boxesCheckAll;
    CheckBox checkAll;
    Button prev, next;
    RecyclerView recyclerView_FeePas;
    ListingFeePas listingFeePas;
    ArrayList<Model_SelectTemplate> templatesAnak;
    ArrayList<Model_ShowTemplate> model;
    ArrayList<SelectTemplate.AdapterTemplateAnak.VHolder> button = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);
        recyclerView_FeePas = view.findViewById(R.id.list_template);
        boxesCheckAll = view.findViewById(R.id.e);
        checkAll = view.findViewById(R.id.select_template_checkAll);

//        model.add(new Model_ShowTemplate("F003", "Fee Pas Masuk Kendaraan", null,
//                new ArrayList<Model_SelectTemplate>(Arrays.asList(
//                    new Model_SelectTemplate("f003-001","Pas Masuk Kendaraan L300/Sejenisnya"),
//                    new Model_SelectTemplate("f003-002","Pas Masuk Kendaraan Colt Diesel/Sejenisnya"),
//                    new Model_SelectTemplate("f003-003","Pas Masuk Kendaraan Tronton/Sejenisnya"),
//                    new Model_SelectTemplate("f003-004","Pas Masuk Kendaraan Tronton/Sejenisnya"),
//                    new Model_SelectTemplate("f003-005","Fee Supply BBM Via Darat")
//                ))
//        ));
//        model.add(new Model_ShowTemplate("G004", "Get Access Card", null,
//                new ArrayList<Model_SelectTemplate>(Arrays.asList(
//                        new Model_SelectTemplate("g004-g001","Get Access Card")
//                ))
//        ));
//        model.add(new Model_ShowTemplate("j043", "Jasa Angukatan Kereta Api KS (PASURUAN)",null,
//                new ArrayList<Model_SelectTemplate>(Arrays.asList(
//                        new Model_SelectTemplate("j043-j042","Jasa Angukatan Kereta Api KS 0"),
//                        new Model_SelectTemplate("j045-j048","Jasa Angukatan Kereta Api KS 1")
//                ))
//        ));
//        model.add(new Model_ShowTemplate("t008", "Train", null,
//                new ArrayList<Model_SelectTemplate>(Arrays.asList(
//                        new Model_SelectTemplate("t043-j042","Train Test 0"),
//                        new Model_SelectTemplate("t045-j048","Train Test 1"),
//                        new Model_SelectTemplate("t045-j048","Train Test 2")
//                ))
//        ));
//region Filtering Checked
//        int i = 0;
//        if (BookingData.isExist()){
//            // Load Data
//            for(BookingData.BookTemplate temp : BookingData.i.template){
//                Log.i("showTemplate","on BookTemplate = "+temp.code+" "+temp.name);
//                for(;i<model.size();i++){
//                    if (temp.code == model.get(i).getId()){
//                        int j=0;
//                        ArrayList<Model_SelectTemplate> sel = model.get(i).list;
//                        for (Model_SelectTemplate mod : model.get(i).list) {
//                            for (; j < sel.size(); j++) {
//                                if (mod.getId() == sel.get(j).getId()) {
//                                    Log.i("showTemplate","Check True for SubList "+mod.getId()+" "+mod.getName());
//                                    //sel.get(j).setChecked(true);
//                                    break;
//                                }
//                            }
//                        }
//                        i++;
//                        break;
//                    }else{
//                        Log.i( "showTemplate" , "Model Remove "+model.get(i).getId()+" "+model.get(i).getName()+" List ="+model.get(i).list.size());
//                        model.remove(i);
//                        i--;
//                    }
//                }
//            }
//            for(;i<model.size();i++) {
//                Log.i( "showTemplate" , "Model Remove "+model.get(i).getId()+" "+model.get(i).getName()+" List ="+model.get(i).list.size());
//                model.remove(i);
//                i--;
//            }
//        }
//            else{
//            // Error Back to CustomerAddForm
//        }
// endregion

        //Button go & backn
        prev = view.findViewById(R.id.select_template_prevBtn);
        next = view.findViewById(R.id.select_template_nextBtn);

        FunctionButton();
        ListingData();
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(AdapterTemplateAnak.VHolder b : button){
                    b.checkBox.setChecked(isChecked);
                }
            }
        });
        return view;
    }


    public void ListingData () {
        if (BookingData.isExist() && BookingData.i.template!=null) {
            ArrayList<String> query = new ArrayList<>();
            for(BookingData.BookTemplate temp:BookingData.i.template){
                query.add(String.valueOf(temp.id));
            }
            Log.i("select_template",query.toString());
            Call<CallingSelectTemp> call = UserData.i.getService().getSelectTemplate(UserData.i.getToken(), query);
            call.enqueue(new Callback<CallingSelectTemp>() {
                @Override
                public void onResponse(Call<CallingSelectTemp> call, Response<CallingSelectTemp> response) {
                    if (Calling.TreatResponse(getContext(), "tag", response.body())) {
                        CallingSelectTemp.SelTemp data = response.body().data;
                        if (data == null || data.list == null) {
                            Toasty.error(getContext(), "List Kosong", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            //Log.i("model_select", "length request = " + data.list.size());
                            List<BookingData.BookTemplate.BookTempList> listcheck = null;
                            BookingData.BookTemplate temp = null;
                            Model_ShowTemplate stemp = null;
                            model = new ArrayList<>(BookingData.i.template.size());
                            for (BookingData.BookTemplate st : BookingData.i.template) {
                                model.add(st.getModel());
                            }
                            int f = 0;
                            for (Model_SelectTemplate mod : data.list) {
                                int header_id = Integer.parseInt(mod.header_id);
                                if (temp == null || temp.id != header_id) {
                                    for (int i = 0; i < model.size(); i++) {
                                        temp = BookingData.i.template.get(i);
                                        if (temp.id == header_id) {
                                            stemp = model.get(i);
                                            listcheck = temp.listCheck;
                                            if (listcheck != null) {
                                                for(BookingData.BookTemplate.BookTempList l:listcheck){
                                                    Log.i("checked!",l.code+" "+l.name+" "+l.id);
                                                }
                                            }else{
                                                Log.e("checked!",temp.id+"is null!");
                                            }
                                            //Log.i("model_select", "stemp = "+stemp.display_desc_header + " " + stemp.id + " " + stemp.code);
                                            break;
                                        } else
                                            stemp = null;
                                    }
                                }
                                if (stemp != null) {
                                    if (listcheck!=null) {
                                        Log.w("checked!","start check = "+listcheck.size());
                                        for (f = 0; f < listcheck.size(); f += 1) {
                                            Log.w("checked!","start check "+mod.id+"="+listcheck.get(f).id);
                                            if (mod.id == listcheck.get(f).id) {
                                                mod.checked = true;
                                                break;
                                            }
                                        }
                                    }
                                    stemp.list.add(mod);
                                    Log.i("model_select", mod.header_id + " " + mod.id + " " + mod.code + " " + mod.desc+" "+mod.checked);
                                }
                            }
                            for (Model_ShowTemplate st : model) {
                                Log.i("model_select",st.id+" = "+st.list.size());
                            }
                            listingFeePas = new ListingFeePas(getContext(), model);
//                            adapter = new ShowTemplate.ListTemplate(getContext(), model);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            recyclerView_FeePas.setLayoutManager(layoutManager);
                            recyclerView_FeePas.setAdapter(listingFeePas);
                        }
                    }
                }


                @Override
                public void onFailure(Call<CallingSelectTemp> call, Throwable t) {
                    Log.i(UploadDocument.FileUtils.TAG, "onFailure: => " + t);
                }
            });
        }
    }

    public void FunctionButton(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingData.i.SelectBookUpdate(model);
                getActivity().onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToUpload();
            }
        });
    }

    public void GoToUpload(){
        if (isOneChecked()) {
            for (Model_ShowTemplate mod : model) {
                for (Model_SelectTemplate sel : mod.list) {
                    Log.i("out", sel.header_id + " | " + sel.id + " " + sel.desc + " - " + sel.checked);
                }
            }
            BookingData.i.SelectBookUpdate(model);
            Fragment fragment = new UploadDocument();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Toasty.error(getContext(), "Anda harus memilih template dahulu", Toasty.LENGTH_SHORT,true).show();
        }
    }

    //Melihat status dari subType Template apakah tidak ada yg true di Type Template
    boolean isOneChecked(){
        boolean checked = false;
        for (Model_ShowTemplate mod : model) {
            checked = false;
            for (Model_SelectTemplate sel : mod.list) {
                if (sel.checked){
                    checked = true;
                    break;
                }
            }
            if (!checked){
                return false;
            }
        }
        return true;
    }

    //Ini untuk Listing ID dan NAME dari setiap type Template
    public class ListingFeePas extends RecyclerView.Adapter<ListingFeePas.vHolder>{
        Context context;
        List<Model_ShowTemplate> model;

        public ListingFeePas(Context context, List<Model_ShowTemplate> model_selectTemplates) {
            this.context = context;
            this.model = model_selectTemplates;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_checkbox, parent,false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final vHolder holder, final int position) {
            holder.idtitle.setText(model.get(position).code);
            holder.nametitle.setText(model.get(position).display_desc_header);

            //Untuk mempermudah pengembangan selanjutnya dari setiap pilihan sub Template.
            // maka disini dibuat kan list didalam list
            AdapterTemplateAnak templateAnak = new AdapterTemplateAnak(context, model.get(position).list);
            holder.listRecycle.setHasFixedSize(true);
            holder.listRecycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.listRecycle.setAdapter(templateAnak);
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public class vHolder extends RecyclerView.ViewHolder {
            ImageView img;
            RecyclerView listRecycle;
            TextView nametitle, idtitle;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                img = itemView.findViewById(R.id.imgshowtemplate);
                nametitle = itemView.findViewById(R.id.name_showtemplate);
                idtitle = itemView.findViewById(R.id.idshowtemplate);
                listRecycle = itemView.findViewById(R.id.list_anakselect);
            }
        }
    }

    //Ini untuk Listing ID dan NAME dari setiap subtype Template
    public  class AdapterTemplateAnak extends RecyclerView.Adapter<AdapterTemplateAnak.VHolder>{
        Context context;
        List<Model_SelectTemplate> selectTemplates;

        public AdapterTemplateAnak(Context context, List<Model_SelectTemplate> selectTemplates) {
            this.context = context;
            this.selectTemplates = selectTemplates;
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_select_template, parent,false);
            return new VHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder holder, int position) {
            holder.name.setText(selectTemplates.get(position).desc);
            holder.id.setText(selectTemplates.get(position).code);
            holder.checkBox.setChecked(selectTemplates.get(position).checked);
            button.add(holder);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.checkBox.setChecked(isChecked);
                    selectTemplates.get(position).checked = isChecked;
                }
            });

        }


        @Override
        public int getItemCount() {
            return selectTemplates.size();
        }

        public class VHolder extends RecyclerView.ViewHolder{
            TextView id, name;
            CheckBox checkBox;
            public VHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.idselecttemplate);
                name = itemView.findViewById(R.id.nameselecttemplate);
                checkBox = itemView.findViewById(R.id.modelcheck_selecttemplate);
            }
        }
    }

}