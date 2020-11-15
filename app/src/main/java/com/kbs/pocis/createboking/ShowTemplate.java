package com.kbs.pocis.createboking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.kbs.pocis.createboking.UploadDocument.FileUtils.TAG;

public class ShowTemplate extends Fragment {

    Button btnPrev, btnNext;
    RecyclerView listtemplate;
    ListTemplate adapter;
    ArrayList<String>  mdl = new ArrayList<>();
    List<Model_ShowTemplate> model = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_template, container, false);

        listtemplate = view.findViewById(R.id.recycle_showtemplate);
        btnPrev = view.findViewById(R.id.cust_add_form_prevBtn);
        btnNext = view.findViewById(R.id.cust_add_form_nextBtn);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        model.add(new Model_ShowTemplate("f003", "Fee Pas Masuk Kendaraan", R.color.colorGrey));
        model.add(new Model_ShowTemplate("g004", "Get Access Card", R.color.colorGrey));
        model.add(new Model_ShowTemplate("j043", "Jasa Angukatan Kereta Api KS (PASURUAN)", R.color.colorGrey));
        model.add(new Model_ShowTemplate("t008", "Train", R.color.colorGrey));

        int i = 0;
        if (BookingData.isExist()){
            // Load Data
            for(BookingData.BookTemplate temp : BookingData.i.template){
                for(;i<model.size();i++){
                    if (temp.code == model.get(i).getId()){
                        model.get(i).setCheck(true);
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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoNextPage();
            }
        });

        return view;
    }

    void GoNextPage(){
        /*
        for (Model_ShowTemplate models : adapter.checkbokListClick){
            mdl.add(models.getId());
            mdl.add(models.getName());
        }
         */
        if (getOneIsChecked()){//(adapter.checkbokListClick.size() > 0){
            //BookingData.i.Template = BookingData.getTemplate(models);
            BookingData.i.SetBookTemplate(model);
            Log.i("TAG", "lis: " + model);
            //Bundle arg = new Bundle();
            //arg.putSerializable("showtemplate",mdl);
            Fragment fragment = new SelectTemplate();
            //fragment.setArguments(arg);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            Toasty.error(getContext(), "Please Selected Template Type", Toast.LENGTH_SHORT, true).show();
        }

    }
    boolean getOneIsChecked() {
        for(Model_ShowTemplate m : model) {
            if (m.getCheck())
                return true;
        }
        return false;
    }

    public static class ListTemplate extends RecyclerView.Adapter<ListTemplate.Vholder>{

        Context context;
        List<Model_ShowTemplate> model;
//        ArrayList<Model_ShowTemplate> checkbokListClick = new ArrayList<>();

        public ListTemplate(Context context, List<Model_ShowTemplate> model) {
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
            holder.img.setBackgroundResource(model.get(position).getImg());
            holder.id.setText(model.get(position).getId());
            holder.name.setText(model.get(position).getName());
            holder.status.setChecked(model.get(position).getCheck());

//            holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
////                        checkbokListClick.add(model.get(position));
//                    } else {
//                        checkbokListClick.remove(model.get(position));
//                    }
//                }
//            });
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