package com.kbs.pocis.adapter.myprojects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.myprojects.Model_Project_Open;

import java.util.List;

public class Adapter_Project_Open extends RecyclerView.Adapter<Adapter_Project_Open.vHolder> {

    Context context;
    List<Model_Project_Open> model_project_opens;

    public Adapter_Project_Open(Context context, List<Model_Project_Open> model_project_opens) {
        this.context = context;
        this.model_project_opens = model_project_opens;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project_open, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.id.setText(model_project_opens.get(position).getId());
        holder.date.setText(model_project_opens.get(position).getDate());
        holder.name_vesel.setText(model_project_opens.get(position).getName_vesel());
        holder.name_consigne.setText(model_project_opens.get(position).getName_consigne());
        holder.start_date.setText(model_project_opens.get(position).getStart_date());
        holder.tonage.setText(model_project_opens.get(position).getTonage());
        holder.code_schedule.setText(model_project_opens.get(position).getCode_schedule());
        holder.no_booking.setText(model_project_opens.get(position).getNo_booking());
    }

    @Override
    public int getItemCount() {
        return model_project_opens.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{

        TextView date, no_booking, id, code_schedule, tonage, start_date, name_consigne, name_vesel;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_project_open);
            no_booking = itemView.findViewById(R.id.myproject_no_booking);
            date = itemView.findViewById(R.id.date_project_open);
            code_schedule = itemView.findViewById(R.id.myproject_code_schedule);
            tonage = itemView.findViewById(R.id.myproject_tonage);
            start_date = itemView.findViewById(R.id.myproject_start_date);
            name_consigne = itemView.findViewById(R.id.myproject_name_consigne);
            name_vesel = itemView.findViewById(R.id.myproject_name_vesel);
        }
    }

}
