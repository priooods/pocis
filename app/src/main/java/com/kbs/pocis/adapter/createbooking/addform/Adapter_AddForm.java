package com.kbs.pocis.adapter.createbooking.addform;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.createboking.CustomerAddForm;
import com.kbs.pocis.model.createboking.Model_AddForm;
import com.kbs.pocis.service.createbooking.CreateBok;
import com.kbs.pocis.service.createbooking.DataCalling;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Adapter_AddForm extends RecyclerView.Adapter<Adapter_AddForm.VHolder> {

    Context context;
    List<DataCalling> model_addForms;
    TextView name;
    CustomerAddForm data;
    CheckBox lastchecked;

    public Adapter_AddForm(Context context, List<DataCalling> model_addForms, TextView nm, CustomerAddForm data) {
        this.context = context;
        this.model_addForms = model_addForms;
        this.name = nm;
        this.data = data;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_addform_sub, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.name.setText(model_addForms.get(position).name);
        if (data.data == model_addForms.get(position)) {
            holder.checkBox.setChecked(true);
            lastchecked = holder.checkBox;
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ChangeChecked(holder.checkBox);
                    data.data = model_addForms.get(position);
                    name.setText(data.data.name);
                    Log.i(TAG, "onBindViewHolder11: = " + data.data.name + " id = " + data.data.id);
                }else{
                    data.data = null;
                }
            }
        });
    }
    public void ChangeChecked(CheckBox now_check){
        if (lastchecked!=null) {
            lastchecked.setChecked(false);
        }
        now_check.setChecked(true);
        lastchecked = now_check;
    }
    public DataCalling getData (){
        return data.data;
    }

    @Override
    public int getItemCount() {
        return model_addForms.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder{

        TextView name;
        CheckBox checkBox;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.check_subtitle_customeradd);
            name = itemView.findViewById(R.id.custumeraddform_subtitle);
        }
    }

}


