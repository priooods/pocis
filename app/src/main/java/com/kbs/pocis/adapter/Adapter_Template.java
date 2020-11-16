package com.kbs.pocis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;

import java.util.List;

public class Adapter_Template extends RecyclerView.Adapter {

    Context context;
    List<Model_ShowTemplate> model;
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;

    public Adapter_Template(Context context, List<Model_ShowTemplate> model) {
        this.context = context;
        this.model = model;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_1:
                view = LayoutInflater.from(context).inflate(R.layout.model_showtemplate, parent, false);
                return new ItemShowTemplate(view);
            case TYPE_2:
                view = LayoutInflater.from(context).inflate(R.layout.model_checkbox, parent, false);
                return new ItemSelectTemplate(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //return model.get(position);
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ItemShowTemplate extends RecyclerView.ViewHolder {

        //  komponen
        TextView textViewItemPertama;

        public ItemShowTemplate(View itemView) {
            super(itemView);
            textViewItemPertama = (TextView) itemView.findViewById(R.id.name_showtemplate);
        }
    }

    class ItemSelectTemplate extends RecyclerView.ViewHolder {

        TextView id, name;
        CheckBox checkBox;

        public ItemSelectTemplate(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idselecttemplate);
            name = itemView.findViewById(R.id.nameselecttemplate);
            checkBox = itemView.findViewById(R.id.modelcheck_selecttemplate);
        }
    }
}
