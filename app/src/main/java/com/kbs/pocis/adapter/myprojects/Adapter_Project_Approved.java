package com.kbs.pocis.adapter.myprojects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.myprojects.Model_Project_Open;
import com.kbs.pocis.myproject.detail.Detail_MyProject;

import java.util.List;

public class Adapter_Project_Approved extends RecyclerView.Adapter<Adapter_Project_Approved.vHolder> {

    Context context;
    List<Model_Project_Open> model_project;

    public Adapter_Project_Approved(Context context, List<Model_Project_Open> model_project) {
        this.context = context;
        this.model_project = model_project;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project_approved, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.number.setText(model_project.get(position).getBooking_no());
        holder.vesel.setText(model_project.get(position).getVesel_name());
        holder.name.setText(model_project.get(position).getConsig_name());
        holder.ppj.setText(model_project.get(position).getPpj_nomer());
        holder.status.setText(model_project.get(position).getStatus());
        holder.gotoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_MyProject.class);
                intent.putExtra("get",1);
                intent.putExtra("form", " - Project Approval");
                intent.putExtra("boking_no", model_project.get(position).getBooking_no());
                intent.putExtra("status", model_project.get(position).getStatus());
                intent.putExtra("id", model_project.get(position).getId());
                intent.putExtra("schedule", model_project.get(position).getSchedule_code());
                intent.putExtra("temp_project", model_project.get(position).getTemp_proj());
                context.startActivity(intent);
            }
        });

        if (holder.status.getText().toString().equals("PREPARED")){
            holder.status.setTextColor(Color.parseColor("#00a1d1"));
        } else if (holder.status.getText().toString().equals("OPEN")){
            holder.status.setTextColor(Color.parseColor("#4BA459"));
        }
    }

    @Override
    public int getItemCount() {
        return model_project.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{
        TextView number, status, name, vesel, ppj;
        ConstraintLayout gotoo;
        public vHolder(@NonNull View itemView) {
            super(itemView);
            gotoo = itemView.findViewById(R.id.lay);
            number = itemView.findViewById(R.id.model_project_approved_nomer);
            status = itemView.findViewById(R.id.model_project_approved_status);
            name = itemView.findViewById(R.id.model_project_approved_cusname);
            vesel = itemView.findViewById(R.id.model_project_approved_veselname);
            ppj = itemView.findViewById(R.id.model_project_approved_ppjno);

        }
    }
}
