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
import android.widget.TextView;
import android.widget.Toast;

import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class SelectTemplate extends Fragment {

    boolean f003,g004,j043,t008 = true;
    boolean isSelectedAll;
    CardView card_f003, card_g004, boxesCheckAll;
    CheckBox checkAll, check_g1;
    Button prev, next;
    ArrayList<String> mdl = new ArrayList<>();

    RecyclerView recyclerView_FeePas;
    ListingFeePas listingFeePas;
    List<Model_SelectTemplate> model_selectTemplates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);

        Bundle bundle = getArguments();
        mdl = (ArrayList<String>) bundle.getSerializable("idshowtemplate");
        for (int i=0; i<mdl.size(); i++){
            f003 = mdl.contains("f003");
            g004 = mdl.contains("g004");
            j043 = mdl.contains("j043");
            t008 = mdl.contains("t008");
        }

        //View card Template
        card_f003 = view.findViewById(R.id.card_f003);
        card_g004 = view.findViewById(R.id.card_g004);
        boxesCheckAll = view.findViewById(R.id.e);

        //CheckButton
        checkAll = view.findViewById(R.id.select_template_checkAll);
        check_g1 = view.findViewById(R.id.check_g004_1);

        //Button go & backn
        prev = view.findViewById(R.id.select_template_prevBtn);
        next = view.findViewById(R.id.select_template_nextBtn);

        ShowLayout();
        SelectedAll(checkAll);
        TemplateFeePass(view);
        FunctionButton();

        return view;
    }

    public void ShowLayout(){
        if (f003){
            card_f003.setVisibility(View.VISIBLE);
        }
        if (g004){
            card_g004.setVisibility(View.VISIBLE);
        }
    }

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
                ArrayList<String> idFeePass = new ArrayList<String>();
                for (Model_SelectTemplate modelSelectTemplate : listingFeePas.checkbokListClick){
                    idFeePass.add(modelSelectTemplate.getId());
                }
                ErrorBox(f003, g004);
                Log.i(TAG, "onClick: FeePass" + idFeePass);

            }
        });
    }

    public void GoToUpload(){
        Fragment fragment = new UploadDocument();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Fungsi kalau Checkbox setiap Template tidak dipilih User
    public void ErrorBox(boolean feepas, boolean card){
        //Error untuk FeePass Kalau CheckBoxnya kosong semua
        if (feepas && card){
            if (listingFeePas.checkbokListClick.size() == 0){
                Toasty.error(getContext(), "Please Selected Fee Pass", Toast.LENGTH_LONG).show();
            }
            if (!check_g1.isChecked()){
                Toasty.error(getContext(), "Please Selected Access Card", Toast.LENGTH_LONG).show();
            }
        } else if (listingFeePas.checkbokListClick.size() > 0){
            GoToUpload();
        } else if (check_g1.isChecked()){
            GoToUpload();
        }
        else {
            Toasty.error(getContext(), "Please Selected Template", Toast.LENGTH_LONG).show();
        }

        if (listingFeePas.checkbokListClick.size() > 0 && check_g1.isChecked()){
            GoToUpload();
        }
    }

    //Fungsi untuk CheckBox selected All
    public void SelectedAll(final CheckBox checkbox){
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()){
                    listingFeePas.selectAll();
                    listingFeePas.notifyDataSetChanged();
                    check_g1.setChecked(true);
                } else {
                    listingFeePas.unselectall();
                    listingFeePas.notifyDataSetChanged();
                    check_g1.setChecked(false);
                }
            }
        });
    }

    //Fungsi untuk menampilkan List CheckBox Fee Pass Masuk
    public void TemplateFeePass(View view){
        recyclerView_FeePas = view.findViewById(R.id.recycle_selecttemplate_f003);
        model_selectTemplates = new ArrayList<>();
        model_selectTemplates.add(new Model_SelectTemplate("f003-001","Pas Masuk Kendaraan L300/Sejenisnya"));
        model_selectTemplates.add(new Model_SelectTemplate("f003-002","Pas Masuk Kendaraan Colt Diesel/Sejenisnya"));
        model_selectTemplates.add(new Model_SelectTemplate("f003-003","Pas Masuk Kendaraan Tronton/Sejenisnya"));
        model_selectTemplates.add(new Model_SelectTemplate("f003-004","Pas Masuk Kendaraan Tronton/Sejenisnya"));
        model_selectTemplates.add(new Model_SelectTemplate("f003-005","Fee Supply BBM Via Darat"));
        listingFeePas = new ListingFeePas(getContext(), model_selectTemplates, isSelectedAll);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView_FeePas.setLayoutManager(layoutManager);
        recyclerView_FeePas.setAdapter(listingFeePas);

    }

    //ini untuk adapter recycler Fee Pass Masuk Kendaraan
    // Cara kerjanya sama kaya di show Template
    public static class ListingFeePas extends RecyclerView.Adapter<ListingFeePas.vHolder>{

        Context context;
        List<Model_SelectTemplate> model_selectTemplates;
        ArrayList<Model_SelectTemplate> checkbokListClick = new ArrayList<>();
        boolean isSelectedAll;

        public ListingFeePas(Context context, List<Model_SelectTemplate> model_selectTemplates, boolean isSelectedAll) {
            this.context = context;
            this.model_selectTemplates = model_selectTemplates;
            this.isSelectedAll = isSelectedAll;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_checkbox, parent,false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final vHolder holder, final int position) {
            holder.name.setText(model_selectTemplates.get(position).getName());
            holder.id.setText(model_selectTemplates.get(position).getId());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        checkbokListClick.add(model_selectTemplates.get(position));
                    } else {
                        checkbokListClick.remove(model_selectTemplates.get(position));
                    }
                }
            });

            if (!isSelectedAll){
                holder.checkBox.setChecked(false);
            } else  {
                holder.checkBox.setChecked(true);
            }
        }

        @Override
        public int getItemCount() {
            return model_selectTemplates.size();
        }

        public void selectAll(){
            isSelectedAll = true;
            notifyDataSetChanged();
        }

        public void unselectall(){
            isSelectedAll = false;
            notifyDataSetChanged();
        }

        public static class vHolder extends RecyclerView.ViewHolder {

            TextView id, name;
            CheckBox checkBox;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.idselecttemplate);
                name = itemView.findViewById(R.id.nameselecttemplate);
                checkBox = itemView.findViewById(R.id.modelcheck_selecttemplate);
            }

        }

    }
}