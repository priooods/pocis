package com.kbs.pocis.adapter.detailMonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Monitoring;

import java.util.List;

public class Adapter_Monitoring_Detail extends RecyclerView.Adapter<Adapter_Monitoring_Detail.vHolder> {

    List<Model_Monitoring> model_monitorings;
    Context context;
    int type;

    public Adapter_Monitoring_Detail(List<Model_Monitoring> model_monitorings, Context context, int type) {
        this.model_monitorings = model_monitorings;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_detail_monitoring, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        switch (type){
            case 0:
                holder.line_contact.setVisibility(View.VISIBLE);
                holder.contact_name.setText(model_monitorings.get(position).contact_name);
                holder.contact_email.setText(model_monitorings.get(position).contact_email);
                holder.contact_status.setText(model_monitorings.get(position).contact_status);
                holder.contact_jabatan.setText(model_monitorings.get(position).contact_jabatan);
                holder.contact_no.setText(model_monitorings.get(position).contact_nomer);
                break;
            case 1:
                holder.line_truck.setVisibility(View.VISIBLE);
                holder.truck_code.setText(model_monitorings.get(position).code);
                holder.truck_date.setText(model_monitorings.get(position).date);
                holder.item4_truck.setText(model_monitorings.get(position).pos);
                break;
            case 2:
                holder.line_monitoring.setVisibility(View.VISIBLE);
                holder.item_pcs0.setText(model_monitorings.get(position).pcs);
                holder.item_tonnage0.setText(model_monitorings.get(position).tonnage);
                holder.item_ritase.setText(model_monitorings.get(position).ritase);
                break;
            case 3:
                holder.line_stowage.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.line_summary.setVisibility(View.VISIBLE);
                holder.item3_item.setText(model_monitorings.get(position).hacth_no);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return model_monitorings.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{

        LinearLayout line_summary,line_contact,line_truck,line_monitoring,line_stowage;
        View l_contact,ln_truck,ln_monitoring,ln_sumary;
        //for Contact;
        TextView contact_name,contact_status,contact_jabatan,contact_no,contact_email;
        //for Truck;
        TextView truck_code,truck_ppj,truck_date,item1_truck,item2_truck,item3_truck,item4_truck;
        //for Monitoring;
        TextView monitoring_code,monitoring_date,item_pcs0,item_tonnage0,item_ritase,item_pcs1,item_tonnage1,ritase;
        //for Summary;
        TextView item3_item;


        public vHolder(@NonNull View itemView) {
            super(itemView);
            //model summary
            item3_item = itemView.findViewById(R.id.item3_item);
            //model truck
            monitoring_code = itemView.findViewById(R.id.monitoring_code);
            monitoring_date = itemView.findViewById(R.id.monitoring_date);
            item_pcs0 = itemView.findViewById(R.id.item_pcs0);
            item_tonnage0 = itemView.findViewById(R.id.item_tonnage0);
            item_ritase = itemView.findViewById(R.id.item_ritase);
            item_pcs1 = itemView.findViewById(R.id.item_pcs1);
            item_tonnage1 = itemView.findViewById(R.id.item_tonnage1);
            ritase = itemView.findViewById(R.id.ritase);

            //model truck
            truck_code = itemView.findViewById(R.id.truck_code);
            truck_ppj = itemView.findViewById(R.id.truck_ppj);
            truck_date = itemView.findViewById(R.id.truck_date);
            item1_truck = itemView.findViewById(R.id.item1_truck);
            item2_truck = itemView.findViewById(R.id.item2_truck);
            item3_truck = itemView.findViewById(R.id.item3_truck);
            item4_truck = itemView.findViewById(R.id.item4_truck);

            //layout
            line_contact = itemView.findViewById(R.id.model_contact);
            line_summary = itemView.findViewById(R.id.model_sumary);
            line_truck = itemView.findViewById(R.id.model_truck);
            line_monitoring = itemView.findViewById(R.id.model_monitoring);
            line_stowage = itemView.findViewById(R.id.model_stowage);

            //garis view
            l_contact = itemView.findViewById(R.id.ln_contact);
            ln_truck = itemView.findViewById(R.id.ln_truck);
            ln_monitoring = itemView.findViewById(R.id.ln_monitoring);
            ln_sumary = itemView.findViewById(R.id.ln_sumary);

            //model contact
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_status = itemView.findViewById(R.id.contact_status);
            contact_jabatan = itemView.findViewById(R.id.contact_jabatan);
            contact_no = itemView.findViewById(R.id.contact_no);
            contact_email = itemView.findViewById(R.id.contact_email);
        }
    }
}
