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
        holder.startDate.setText(model_project_services.get(position).getStartDate());
        holder.endDate.setText(model_project_services.get(position).getEndDate());
        holder.total.setText(model_project_services.get(position).getTotal());
        holder.calculate.setText(model_project_services.get(position).getCalculation());
        holder.totalDp.setText(model_project_services.get(position).getTotalDp());
        holder.location.setText(model_project_services.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return model_project_services.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{

        TextView name, startDate, endDate, total, calculate, totalDp, location;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.project_service_name);
            startDate = itemView.findViewById(R.id.project_service_startdate);
            endDate = itemView.findViewById(R.id.project_service_Enddate);
            total = itemView.findViewById(R.id.project_service_total);
            calculate = itemView.findViewById(R.id.project_service_calculation);
            totalDp = itemView.findViewById(R.id.project_service_totalDP);
            location = itemView.findViewById(R.id.project_service_location);
        }
    }

}
