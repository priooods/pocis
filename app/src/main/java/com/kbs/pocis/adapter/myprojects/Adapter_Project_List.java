package com.kbs.pocis.adapter.myprojects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.myprojects.Model_Project_Open;
import com.kbs.pocis.myproject.detail.Detail_MyProject;

import java.util.List;

public class Adapter_Project_List extends RecyclerView.Adapter<Adapter_Project_List.vHolder> {

    Context context;
    List<Model_Project_Open> model_project_opens;

    public Adapter_Project_List(Context context, List<Model_Project_Open> model_project_opens) {
        this.context = context;
        this.model_project_opens = model_project_opens;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project_list, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.date.setText(model_project_opens.get(position).getDate_issue());
        holder.name_vesel.setText(model_project_opens.get(position).getVesel_name());
        holder.temp_proj.setText(model_project_opens.get(position).getTemp_proj());
        holder.name_consigne.setText(model_project_opens.get(position).getConsig_name());
        holder.start_date.setText(model_project_opens.get(position).getStart_date());
        holder.tonage.setText(model_project_opens.get(position).getTonage());
        holder.code_schedule.setText(model_project_opens.get(position).getSchedule_code());
        holder.no_booking.setText(model_project_opens.get(position).getBooking_no());
        holder.id_project_status.setText(model_project_opens.get(position).getStatus());
        holder.ppj_project_open.setText(model_project_opens.get(position).getPpj_nomer());
        holder.myproject_billpayment.setText(model_project_opens.get(position).getBill_payment());
        holder.myproject_vaNumber.setText(model_project_opens.get(position).getNumber_bot());
        holder.layout_project_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_MyProject.class);
                intent.putExtra("get",2);
                intent.putExtra("form", " - Project Lists");
                intent.putExtra("id", model_project_opens.get(position).getId());
                intent.putExtra("ppj", model_project_opens.get(position).getPpj_nomer());
                intent.putExtra("status", model_project_opens.get(position).getStatus());
                intent.putExtra("temp_project", model_project_opens.get(position).getTemp_proj());
                intent.putExtra("date_isue", model_project_opens.get(position).getDate_issue());
                context.startActivity(intent);
            }
        });

        if (holder.id_project_status.getText().toString().equals("PREPARED")){
            holder.id_project_status.setTextColor(Color.parseColor("#00a1d1"));
        } else if (holder.id_project_status.getText().toString().equals("OPEN")){
            holder.id_project_status.setTextColor(Color.parseColor("#4BA459"));
        }
    }

    @Override
    public int getItemCount() {
        return model_project_opens.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{

        TextView date, no_booking, temp_proj, code_schedule, tonage, start_date, name_consigne, name_vesel,
                ppj_project_open,myproject_vaNumber,myproject_billpayment,id_project_status;
        RelativeLayout layout_project_open;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            temp_proj = itemView.findViewById(R.id.tempProj_project_open);
            no_booking = itemView.findViewById(R.id.myproject_no_booking);
            date = itemView.findViewById(R.id.date_project_open);
            code_schedule = itemView.findViewById(R.id.myproject_code_schedule);
            tonage = itemView.findViewById(R.id.myproject_tonage);
            start_date = itemView.findViewById(R.id.myproject_start_date);
            name_consigne = itemView.findViewById(R.id.myproject_name_consigne);
            name_vesel = itemView.findViewById(R.id.myproject_name_vesel);
            layout_project_open = itemView.findViewById(R.id.layout_project_open);
            id_project_status = itemView.findViewById(R.id.id_project_status);
            myproject_billpayment = itemView.findViewById(R.id.myproject_billpayment);
            myproject_vaNumber = itemView.findViewById(R.id.myproject_vaNumber);
            ppj_project_open = itemView.findViewById(R.id.ppj_project_open);
            //id_project_status
            //myproject_billpayment
            //myproject_vaNumber
            //ppj_project_open
        }
    }

}
