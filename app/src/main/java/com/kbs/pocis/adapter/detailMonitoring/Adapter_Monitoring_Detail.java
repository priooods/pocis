package com.kbs.pocis.adapter.detailMonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;

import java.util.List;

public class Adapter_Monitoring_Detail extends RecyclerView.Adapter<Adapter_Monitoring_Detail.vHolder> {

    List<Model_Project> model_monitorings;
    Context context;
    int type;

    public Adapter_Monitoring_Detail(List<Model_Project> model_monitorings, Context context, int type) {
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
            case 0: // Label List Contact Agent
                holder.line_contact.setVisibility(View.VISIBLE);
                holder.contact_name.setText(model_monitorings.get(position).contact);
                holder.contact_email.setText(model_monitorings.get(position).contact_email);
                holder.contact_status.setText(R.string.agent);
                holder.contact_jabatan.setText(model_monitorings.get(position).agent_name);
                holder.contact_no.setText(model_monitorings.get(position).contact_hp);
                break;
            case 1: // Label List Contact PBM
                holder.line_contact.setVisibility(View.VISIBLE);
                holder.contact_name.setText(model_monitorings.get(position).contact);
                holder.contact_email.setText(model_monitorings.get(position).contact_email);
                holder.contact_status.setText(R.string.pbm);
                holder.contact_jabatan.setText(model_monitorings.get(position).agent_name);
                holder.contact_no.setText(model_monitorings.get(position).contact_hp);
                break;
            case 3: // Model Actual Truck Monitoring
                holder.line_truck.setVisibility(View.VISIBLE);
                holder.truck_code.setText(model_monitorings.get(position).nopol);
                holder.truck_date.setText(model_monitorings.get(position).created);
                holder.truck_ppj.setText(model_monitorings.get(position).project_no);
                holder.item1_truck.setText(model_monitorings.get(position).vessel_name);
                holder.item2_truck.setText(model_monitorings.get(position).voyage_no);
                holder.item3_truck.setText(model_monitorings.get(position).before_pos);
                holder.item4_truck.setText(model_monitorings.get(position).pos);
                break;
            case 2:
                holder.line_monitoring.setVisibility(View.VISIBLE);
//                holder.item_pcs0.setText(model_monitorings.get(position).pcs);
                holder.item_tonnage0.setText(model_monitorings.get(position).tonnage);
                holder.item_ritase.setText(model_monitorings.get(position).ritase);
                break;
            case 4: //for list vessel in progress
                holder.ln_actual_vessel.setVisibility(View.VISIBLE);
                holder.item1_actual_vesel.setText(model_monitorings.get(position).bl);
                holder.item2_actual_vesel.setText(model_monitorings.get(position).unit_qty);
                holder.item3_actual_vesel.setText(model_monitorings.get(position).actual);
                holder.item4_actual_vesel.setText(model_monitorings.get(position).prosentase);
                holder.actual_vessel_name.setText(model_monitorings.get(position).consignee_name);
                break;
            case 5: //for list item summary
                holder.line_summary.setVisibility(View.VISIBLE);
                holder.item3_item_name.setText(model_monitorings.get(position).commodity_name);
                holder.item1_item.setText(model_monitorings.get(position).hatch_no);
                holder.item2_item.setText(model_monitorings.get(position).cargo_type);
                holder.item3_item.setText(model_monitorings.get(position).actual_progress);
                holder.item4_item.setText(model_monitorings.get(position).hatch_side);
                holder.item5_item.setText(model_monitorings.get(position).status_equipment);
                holder.item6_item.setText(model_monitorings.get(position).hatch_position);
                holder.item7_item.setText(model_monitorings.get(position).equipment_name);
                break;
            case 6: //ship side
                holder.ln_ship.setVisibility(View.VISIBLE);
                holder.layoutdasar.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
                ));
                holder.title_ship.setText(model_monitorings.get(position).hatch_no);
                holder.value_ship.setText(model_monitorings.get(position).tonnage_actual);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return model_monitorings.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layoutdasar;
        LinearLayout line_summary,line_contact,line_truck,line_monitoring,ln_actual_vessel, ln_ship;
        View l_contact,ln_truck,ln_monitoring,ln_sumary;
        //for Contact;
        TextView contact_name,contact_status,contact_jabatan,contact_no,contact_email;
        //for Truck;
        TextView truck_code,truck_ppj,truck_date,item1_truck,item2_truck,item3_truck,item4_truck;
        //for Monitoring;
        TextView monitoring_code,monitoring_date,item_pcs0,item_tonnage0,item_ritase,item_pcs1,item_tonnage1,ritase;
        //for Summary;
        TextView item3_item_name, item3_item,item1_item,item2_item,item4_item,item5_item,item6_item,item7_item;
        //for actual vess progress
        TextView item1_actual_vesel,item2_actual_vesel,item3_actual_vesel,item4_actual_vesel,actual_vessel_name;
        //for ship stowage
        TextView title_ship,value_ship;

        public vHolder(@NonNull View itemView) {
            super(itemView);
            layoutdasar = itemView.findViewById(R.id.layout_dasar);
            //model shipp
            title_ship = itemView.findViewById(R.id.title_kapal);
            value_ship = itemView.findViewById(R.id.value_kapal);
            ln_ship = itemView.findViewById(R.id.ln_kapal);
            //model actual vessel in progress
            actual_vessel_name = itemView.findViewById(R.id.actual_vessel_name);
            item1_actual_vesel = itemView.findViewById(R.id.item1_actual_vesel);
            item2_actual_vesel = itemView.findViewById(R.id.item2_actual_vesel);
            item3_actual_vesel = itemView.findViewById(R.id.item3_actual_vesel);
            item4_actual_vesel = itemView.findViewById(R.id.item4_actual_vesel);
            //model summary
            item3_item_name = itemView.findViewById(R.id.sumary_name);
            item1_item = itemView.findViewById(R.id.item1_item);
            item2_item = itemView.findViewById(R.id.item2_item);
            item3_item = itemView.findViewById(R.id.item3_item);
            item4_item = itemView.findViewById(R.id.item4_item);
            item5_item = itemView.findViewById(R.id.item5_item);
            item6_item = itemView.findViewById(R.id.item6_item);
            item7_item = itemView.findViewById(R.id.item7_item);
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

            //garis view
            l_contact = itemView.findViewById(R.id.ln_contact);
            ln_truck = itemView.findViewById(R.id.ln_truck);
            ln_monitoring = itemView.findViewById(R.id.ln_monitoring);
            ln_sumary = itemView.findViewById(R.id.ln_sumary);//ln_actual_vessel
            ln_actual_vessel = itemView.findViewById(R.id.ln_actual_vessel);
            //model contact
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_status = itemView.findViewById(R.id.contact_status);
            contact_jabatan = itemView.findViewById(R.id.contact_jabatan);
            contact_no = itemView.findViewById(R.id.contact_no);
            contact_email = itemView.findViewById(R.id.contact_email);
        }
    }
}
