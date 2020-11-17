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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class SelectTemplate extends Fragment {

    boolean f003,g004,j043,t008 = true;
    boolean isSelectedAll;
    CardView boxesCheckAll;
    CheckBox checkAll, check_g1;
    Button prev, next;
    ArrayList<String> mdl = new ArrayList<>();

    RecyclerView recyclerView_FeePas;
    ListingFeePas listingFeePas;
    ArrayList<Model_ShowTemplate> model = new ArrayList<>();
//    ArrayList<String> idFeePass = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);
        recyclerView_FeePas = view.findViewById(R.id.list_template);


        model.add(new Model_ShowTemplate("f003", "Fee Pas Masuk Kendaraan", R.color.colorGrey,
                new ArrayList<Model_SelectTemplate>(Arrays.asList(
                    new Model_SelectTemplate("f003-001","Pas Masuk Kendaraan L300/Sejenisnya"),
                    new Model_SelectTemplate("f003-002","Pas Masuk Kendaraan Colt Diesel/Sejenisnya"),
                    new Model_SelectTemplate("f003-003","Pas Masuk Kendaraan Tronton/Sejenisnya"),
                    new Model_SelectTemplate("f003-004","Pas Masuk Kendaraan Tronton/Sejenisnya"),
                    new Model_SelectTemplate("f003-005","Fee Supply BBM Via Darat")
                ))
        ));
        model.add(new Model_ShowTemplate("g004", "Get Access Card", R.color.colorGrey,
                new ArrayList<Model_SelectTemplate>(Arrays.asList(
                        new Model_SelectTemplate("g004-g001","Pas Tes")
                ))
        ));
        model.add(new Model_ShowTemplate("j043", "Jasa Angukatan Kereta Api KS (PASURUAN)", R.color.colorGrey,
                new ArrayList<Model_SelectTemplate>(Arrays.asList(
                        new Model_SelectTemplate("j043-j042","Pas Tes 0"),
                        new Model_SelectTemplate("j045-j048","Pas Tes1 1")
                ))
        ));
        model.add(new Model_ShowTemplate("t008", "Train", R.color.colorGrey,
                new ArrayList<Model_SelectTemplate>(Arrays.asList(
                        new Model_SelectTemplate("t043-j042","Pas Tes 0"),
                        new Model_SelectTemplate("t045-j048","Pas Tes1 1"),
                        new Model_SelectTemplate("t045-j048","Pas Tes1 2")
                ))
        ));

//        model.add(new Model_ShowTemplate("f003", "Fee Pas Masuk Kendaraan", R.color.colorGrey));
//        model.add(new Model_ShowTemplate("g004", "Get Access Card", R.color.colorGrey));
//        model.add(new Model_ShowTemplate("j043", "Jasa Angukatan Kereta Api KS (PASURUAN)", R.color.colorGrey));
//        model.add(new Model_ShowTemplate("t008", "Train", R.color.colorGrey));

        int i = 0;
        if (BookingData.isExist()){
            // Load Data
            for(BookingData.BookTemplate temp : BookingData.i.template){
                Log.i("showTemplate","on BookTemplate = "+temp.code+" "+temp.name);
                for(;i<model.size();i++){
                    if (temp.code == model.get(i).getId()){
                        i++;
                        int j=0;
                        ArrayList<Model_SelectTemplate> sel = model.get(i).list;
                        for (Model_SelectTemplate mod : model.get(i).list) {
                            for (; j < sel.size(); j++) {
                                if (mod.getId() == sel.get(j).getId()) {
                                    Log.i("showTemplate","Check True for SubList "+mod.getId()+" "+mod.getName());
                                    sel.get(j).setChecked(true);
                                    break;
                                }
                            }
                        }
                        break;
                    }else{
                        Log.i( "showTemplate" , "Model Remove "+model.get(i).getId()+" "+model.get(i).getName()+" List ="+model.get(i).list.size());
                        model.remove(i);
                        i--;
                    }
                }
            }
            for(;i<model.size();i++) {
                Log.i( "showTemplate" , "Model Remove "+model.get(i).getId()+" "+model.get(i).getName()+" List ="+model.get(i).list.size());
                model.remove(i);
                i--;
            }
        }else{
            // Error Back to CustomerAddForm
        }
        for(Model_ShowTemplate temp : model){
            Log.i( "showTemplate" , "Model Exist "+temp.getId()+" "+temp.getName()+" List ="+(temp.list!=null?temp.list.size():"NULL"));
        }
        ///TODO to Prio, MODEL KE LIST NGGA JADI CHECKLIST PADAHAL VALUE NYA TRUE
        listingFeePas = new ListingFeePas(getContext(), model);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView_FeePas.setLayoutManager(layoutManager);
        recyclerView_FeePas.setAdapter(listingFeePas);
        //Update
        //BookingData.BookTemplate = setBookTemplate(models);

        //View card Template
        //card_f003 = view.findViewById(R.id.card_f003);
        //card_g004 = view.findViewById(R.id.card_g004);
        boxesCheckAll = view.findViewById(R.id.e);

        //CheckButton
        checkAll = view.findViewById(R.id.select_template_checkAll);
        //check_g1 = view.findViewById(R.id.check_g004_1);

        //Button go & backn
        prev = view.findViewById(R.id.select_template_prevBtn);
        next = view.findViewById(R.id.select_template_nextBtn);

        //ShowLayout();
        //SelectedAll(checkAll);
        //TemplateFeePass(view);
        FunctionButton();

        return view;
    }

    /*
    public void ShowLayout(){
        if (f003){
            card_f003.setVisibility(View.VISIBLE);
        }
        if (g004){
            card_g004.setVisibility(View.VISIBLE);
        }
    }*/

    public void FunctionButton(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Model_ShowTemplate data : model){
                    if (!data.OneChecked())
                        Log.i("selTemplate","no one is Checked");
                        //return;
                }
                GoToUpload();
            }
        });
    }
/*
    void ShowListing(){
        for (Model_SelectTemplate modelSelectTemplate : listingFeePas.checkbokListClick){
            idFeePass.add(modelSelectTemplate.getId());
        }
        ErrorBox(f003, g004);
        Log.i(TAG, "onClick: FeePass" + idFeePass);
    }*/

    public void GoToUpload(){
        //TODO to prio, oi ini kn udah selesai sebenarnya bisa diload, cmn dari UI gak keubah value di modelnya,
        // INI DIBAWAH HASIL NGECEK, semua value jdi false padahal udah di checklist jjkjkjk
        for(Model_ShowTemplate mod : model){
            for(Model_SelectTemplate sel : mod.list){
                Log.i("out",sel.getId()+" "+sel.getName()+" - "+sel.isChecked());
                sel.setChecked(true);
            }
        }
        BookingData.i.SetBookTemplate(model);
        Fragment fragment = new UploadDocument();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    //TODO GUA AMBIL MINUM DULU

    //Fungsi kalau Checkbox setiap Template tidak dipilih User
//    public void ErrorBox(boolean feepas, boolean card){
//        //Error untuk FeePass Kalau CheckBoxnya kosong semua
//        if (feepas && card){
//            if (listingFeePas.checkbokListClick.size() == 0){
//                Toasty.error(getContext(), "Please Selected Fee Pass", Toast.LENGTH_LONG).show();
//            }
//            if (!check_g1.isChecked()){
//                Toasty.error(getContext(), "Please Selected Access Card", Toast.LENGTH_LONG).show();
//            }
//        }
//        else if (listingFeePas.checkbokListClick.size() > 0){
//            GoToUpload();
//        }
//        else if (check_g1.isChecked()){
//            GoToUpload();
//        }
//        else {
//            Toasty.error(getContext(), "Please Selected Template", Toast.LENGTH_LONG).show();
//        }
//
//        if (listingFeePas.checkbokListClick.size() > 0 && check_g1.isChecked()){
//            GoToUpload();
//        }
//    }

    //Fungsi untuk CheckBox selected All
//    public void SelectedAll(final CheckBox checkbox){
//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkbox.isChecked()){
//                    listingFeePas.selectAll();
//                    listingFeePas.notifyDataSetChanged();
//                    check_g1.setChecked(true);
//                } else {
//                    listingFeePas.unselectall();
//                    listingFeePas.notifyDataSetChanged();
//                    check_g1.setChecked(false);
//                }
//            }
//        });
//    }

    //Fungsi untuk menampilkan List CheckBox Fee Pass Masuk
    public void TemplateFeePass(View view){
//        model_selectTemplates = new ArrayList<>();
//        model_selectTemplates.add(new Model_SelectTemplate("f003-001","Pas Masuk Kendaraan L300/Sejenisnya"));
//        model_selectTemplates.add(new Model_SelectTemplate("f003-002","Pas Masuk Kendaraan Colt Diesel/Sejenisnya"));
//        model_selectTemplates.add(new Model_SelectTemplate("f003-003","Pas Masuk Kendaraan Tronton/Sejenisnya"));
//        model_selectTemplates.add(new Model_SelectTemplate("f003-004","Pas Masuk Kendaraan Tronton/Sejenisnya"));
//        model_selectTemplates.add(new Model_SelectTemplate("f003-005","Fee Supply BBM Via Darat"));

    }

    // ini untuk adapter recycler Fee Pass Masuk Kendaraan
    // Cara kerjanya sama kaya di show Template
    public static class ListingFeePas extends RecyclerView.Adapter<ListingFeePas.vHolder>{
        Context context;
        List<Model_ShowTemplate> model;
//        ArrayList<Model_SelectTemplate> checkbokListClick = new ArrayList<>();

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
            holder.img.setBackgroundResource(model.get(position).getImg());
            holder.idtitle.setText(model.get(position).getId());
            holder.nametitle.setText(model.get(position).getName());

            ArrayList<Model_SelectTemplate> templatesAnak = model.get(position).list;
            AdapterTemplateAnak templateAnak = new AdapterTemplateAnak(context, templatesAnak);
            holder.listRecycle.setHasFixedSize(true);
            holder.listRecycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.listRecycle.setAdapter(templateAnak);
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder {
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


    public static class AdapterTemplateAnak extends RecyclerView.Adapter<AdapterTemplateAnak.VHolder>{
        Context context;
        List<Model_SelectTemplate> selectTemplates;
        boolean isSelectedAll;

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
            holder.name.setText(selectTemplates.get(position).getName());
            holder.id.setText(selectTemplates.get(position).getId());
            holder.checkBox.setChecked(selectTemplates.get(position).isChecked());
            if (!isSelectedAll){
                holder.checkBox.setChecked(false);
            } else {
                holder.checkBox.setChecked(true);
            }
        }

        @Override
        public int getItemCount() {
            return selectTemplates.size();
        }

        public static class VHolder extends RecyclerView.ViewHolder{
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