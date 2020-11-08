package com.kbs.pocis.createboking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.item.ItemClickListener;
import com.kbs.pocis.model.others.Model_SelectTemplate;
import com.kbs.pocis.model.others.Model_ShowTemplate;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SelectTemplate extends Fragment {

    boolean f003,g004,j043,t008 = true;
    CardView card_f003, card_g004, boxesCheckAll;
    CheckBox checkAll, check_f1, check_f2, check_f3, check_f4,
            check_f5, check_g1;
    Button prev, next;
    ArrayList<String> mdl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);

        Bundle bundle = getArguments();
        mdl = (ArrayList<String>) bundle.getSerializable("idshowtemplate");

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
        SelectedBox();
        FunctionButton();

        return view;
    }

    public void ShowLayout(){
        for (int i=0; i<mdl.size(); i++){
            f003 = mdl.contains("f003");
            g004 = mdl.contains("g004");
            j043 = mdl.contains("j043");
            t008 = mdl.contains("t008");
        }

        if (f003){
            card_f003.setVisibility(View.VISIBLE);
        }
        if (g004){
            card_g004.setVisibility(View.VISIBLE);
        }
    }

    public void SelectedBox(){
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    check_f1.setChecked(true);
                    check_f2.setChecked(true);
                    check_f3.setChecked(true);
                    check_f4.setChecked(true);
                    check_f5.setChecked(true);
                    check_g1.setChecked(true);
                } else {
                    check_f1.setChecked(false);
                    check_f2.setChecked(false);
                    check_f3.setChecked(false);
                    check_f4.setChecked(false);
                    check_f5.setChecked(false);
                    check_g1.setChecked(false);
                }
            }
        });
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
                Fragment fragment = new UploadDocument();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    //ini untuk adapter recycler Fee Pass Masuk Kendaraan
    public static class ListingFeePas extends RecyclerView.Adapter<ListingFeePas.vHolder>{

        Context context;
        List<Model_SelectTemplate> model_selectTemplates;
        ArrayList<Model_SelectTemplate> checkbokListClick = new ArrayList<>();

        public ListingFeePas(Context context, List<Model_SelectTemplate> model_selectTemplates) {
            this.context = context;
            this.model_selectTemplates = model_selectTemplates;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_checkbox, parent,false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.name.setText(model_selectTemplates.get(position).getName());
            holder.id.setText(model_selectTemplates.get(position).getId());
            holder.SetItemClicked(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int posisi) {
                    CheckBox box = (CheckBox)v;
                    if (box.isChecked()){
                        checkbokListClick.add(model_selectTemplates.get(posisi));
                    } else if (!box.isChecked()){
                        checkbokListClick.remove(model_selectTemplates.get(posisi));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return model_selectTemplates.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView id, name;
            CheckBox checkBox;
            ItemClickListener itemClickListener;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.idselecttemplate);
                name = itemView.findViewById(R.id.nameselecttemplate);
                checkBox = itemView.findViewById(R.id.modelcheck_selecttemplate);
                checkBox.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }

            public void SetItemClicked(ItemClickListener id)
            {
                itemClickListener = id;
            }
        }
    }
}