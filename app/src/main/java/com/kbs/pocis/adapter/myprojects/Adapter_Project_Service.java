package com.kbs.pocis.adapter.myprojects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.myprojects.Model_Project_Service;

import java.util.List;

public class Adapter_Project_Service extends RecyclerView.Adapter<Adapter_Project_Service.vHolder> {

    Context context;
    List<Model_Project_Service> model_project_services;

    public Adapter_Project_Service(Context context, List<Model_Project_Service> model_project_services) {
        this.context = context;
        this.model_project_services = model_project_services;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_service_project, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.name.setText(model_project_services.get(position).getName());
        holder.tarif.setText(model_project_services.get(position).getTarif());
        holder.param1.setText(model_project_services.get(position).getParam1());
        holder.param2.setText(model_project_services.get(position).getParam2());
        holder.amountDP.setText(model_project_services.get(position).getAmountDP());
        holder.amount.setText(model_project_services.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return model_project_services.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{

        TextView name, tarif, param1, param2, amountDP, amount;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.project_service_name);
            tarif = itemView.findViewById(R.id.project_service_tarif);
            param1 = itemView.findViewById(R.id.project_service_parameter1);
            param2 = itemView.findViewById(R.id.project_service_parameter2);
            amount = itemView.findViewById(R.id.project_service_amountIDR);
            amountDP = itemView.findViewById(R.id.project_service_DPinIdr);

        }
    }

}
