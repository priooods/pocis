package com.kbs.pocis.adapter.myprojects;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Adapter_Project_Service extends RecyclerView.Adapter<Adapter_Project_Service.vHolder> {

    Context context;
    List<Model_Project> model_project_services;
    int type;

    public Adapter_Project_Service(Context context, List<Model_Project> model_project_services, int type) {
        this.context = context;
        this.model_project_services = model_project_services;
        this.type = type;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_service_project, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        switch (type){
            case 0: //for approval
                holder.title0.setVisibility(View.VISIBLE);
                holder.ln5.setVisibility(View.VISIBLE);
                holder.item0.setText(model_project_services.get(position).service_name);
                holder.item1.setText(model_project_services.get(position).tariff);
                holder.item2.setText(model_project_services.get(position).parameter_1);
                holder.item3.setText(model_project_services.get(position).parameter_2);
//                holder.item4.setText(model_project_services.get(position).total);
                holder.title5.setText(R.string.amount_dp_in_idr);
//                holder.item5.setText(model_project_services.get(position).total_dp);
                if (model_project_services.get(position).total != null ||
                        model_project_services.get(position).total_dp != null){
                    Log.i(TAG, "onBindViewHolder: => " + position);
                    setFormatIDR(model_project_services.get(position).total,holder.item4);
                    setFormatIDR(model_project_services.get(position).total_dp,holder.item5);
                }

                break;
            case 2: //for BAPJ Service
                holder.ln1.setVisibility(View.GONE);
                holder.item0.setText(model_project_services.get(position).service_name);
                holder.item2.setText(model_project_services.get(position).parameter_1);
                holder.item3.setText(model_project_services.get(position).parameter_2);
                holder.ln4.setVisibility(View.GONE);
                break;
            case 3:// for invoice
                holder.item0.setText(model_project_services.get(position).service_name);
                holder.item1.setText(model_project_services.get(position).tariff);
                holder.item2.setText(model_project_services.get(position).parameter_1);
                holder.item3.setText(model_project_services.get(position).parameter_2);
                if (model_project_services.get(position).amount_in_idr != null) {
                    DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                    DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
                    formatSymbols.setCurrencySymbol("IDR ");
                    formatSymbols.setMonetaryDecimalSeparator(',');
                    formatSymbols.setGroupingSeparator('.');
                    kursIndo.setDecimalFormatSymbols(formatSymbols);
                    holder.item4.setText(kursIndo.format(Integer.parseInt(model_project_services.get(position).amount_in_idr)));
                } else {
                    holder.item4.setText(null);
                }
                break;
            case 4:// for proforma
                holder.item0.setText(model_project_services.get(position).service_name);
                holder.item1.setText(model_project_services.get(position).tariff);
                holder.item2.setText(model_project_services.get(position).parameter_1);
                holder.item3.setText(model_project_services.get(position).parameter_2);
                if (model_project_services.get(position).total != null) {
                    DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                    DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
                    formatSymbols.setCurrencySymbol("IDR ");
                    formatSymbols.setMonetaryDecimalSeparator(',');
                    formatSymbols.setGroupingSeparator('.');
                    kursIndo.setDecimalFormatSymbols(formatSymbols);
                    holder.item4.setText(kursIndo.format(Integer.parseInt(model_project_services.get(position).total)));
                } else {
                    holder.item4.setText(null);
                }
                break;
            case 5: //for BAPJ Schedule
                holder.tpilot.setVisibility(View.GONE);
                holder.card1.setVisibility(View.GONE);
                holder.card2.setVisibility(View.VISIBLE);
                holder.lnt7.setVisibility(View.GONE);
                holder.t1.setText(R.string.actual_Arrival);
                holder.t2.setText(R.string.actual_Anchorage);
                holder.t3.setText(R.string.actual_Berthing);
                holder.t4.setText(R.string.actual_Start_Work);
                holder.t5.setText(R.string.actual_End_Work);
                holder.t6.setText(R.string.actual_Departure);
                holder.i1.setText(model_project_services.get(position).act_arrival);
                holder.i2.setText(model_project_services.get(position).act_anchorage);
                holder.i3.setText(model_project_services.get(position).act_berthing);
                holder.i4.setText(model_project_services.get(position).act_start_work);
                holder.i5.setText(model_project_services.get(position).act_end_work);
                holder.i6.setText(model_project_services.get(position).act_departure);
                break;
            case 6: //for BAPJ Piloting
                holder.card1.setVisibility(View.GONE);
                holder.card2.setVisibility(View.VISIBLE);
                holder.t1.setText(R.string.pilot_Name);
                holder.t2.setText(R.string.tugboat_Pilot);
                holder.t3.setText(R.string.tugboat_Towage);
                holder.t4.setText(R.string.pilot_On_Board);
                holder.t5.setText(R.string.start_Towing);
                holder.t6.setText(R.string.stop_Towing);
                holder.t7.setText(R.string.pilot_Off_Board);
                holder.tpilot.setText(Model_Project.Piloting.get(position).get(position).pilotage_job_type);
                holder.i1.setText(Model_Project.Piloting.get(position).get(0).pilot_name);
                holder.i2.setText(Model_Project.Piloting.get(position).get(0).tugboat_pilot);
                holder.i3.setText(Model_Project.Piloting.get(position).get(1).tugboat_towage);
                holder.i4.setText(Model_Project.Piloting.get(position).get(0).pilot_on_board);
                holder.i5.setText(Model_Project.Piloting.get(position).get(1).start_towing);
                holder.i6.setText(Model_Project.Piloting.get(position).get(1).stop_towing);
                holder.i7.setText(Model_Project.Piloting.get(position).get(0).pilot_off_board);
                break;
        }

    }

    public void setFormatIDR(String value, TextView textView){
        DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("IDR ");
        formatSymbols.setMonetaryDecimalSeparator(',');
        formatSymbols.setGroupingSeparator('.');
        kursIndo.setDecimalFormatSymbols(formatSymbols);
        try {
            textView.setText(kursIndo.format(Integer.parseInt(value)));
        } catch (Exception e){
            textView.setText(kursIndo.format(0));
        }
    }

    @Override
    public int getItemCount() {
        return model_project_services.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{

        TextView item0, item1,item2,item3,item4,item5,item6,item7,
                title0, title1,title2,title3,title4,title5,title6,title7, tpilot;

        TextView t1,t2,t3,t4,t5,t6,t7,i1,i2,i3,i4,i5,i6,i7;
        ConstraintLayout ln1,ln2,ln3,ln4,ln5,ln6,ln7,lnt7;
        LinearLayout ln0,card2;
        CardView card1;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            card1 = itemView.findViewById(R.id.card1);
            card2 = itemView.findViewById(R.id.card2);
            tpilot = itemView.findViewById(R.id.tpilot);


            item0 = itemView.findViewById(R.id.item0);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            item3 = itemView.findViewById(R.id.item3);
            item4 = itemView.findViewById(R.id.item4);
            item5 = itemView.findViewById(R.id.item5);
            item6 = itemView.findViewById(R.id.item6);
            item7 = itemView.findViewById(R.id.item7);

            title0 = itemView.findViewById(R.id.title0);
            title1 = itemView.findViewById(R.id.title1);
            title2 = itemView.findViewById(R.id.title2);
            title3 = itemView.findViewById(R.id.title3);
            title4 = itemView.findViewById(R.id.title4);
            title5 = itemView.findViewById(R.id.title5);
            title6 = itemView.findViewById(R.id.title6);
            title7 = itemView.findViewById(R.id.title7);

            ln0 = itemView.findViewById(R.id.ln0);
            ln1 = itemView.findViewById(R.id.ln1);
            ln2 = itemView.findViewById(R.id.ln2);
            ln3 = itemView.findViewById(R.id.ln3);
            ln4 = itemView.findViewById(R.id.ln4);
            ln5 = itemView.findViewById(R.id.ln5);
            ln6 = itemView.findViewById(R.id.ln6);
            ln7 = itemView.findViewById(R.id.ln7);
            lnt7 = itemView.findViewById(R.id.ln_time7);


            t1 = itemView.findViewById(R.id.title_time1);
            t2 = itemView.findViewById(R.id.title_time2);
            t3 = itemView.findViewById(R.id.title_time3);
            t4 = itemView.findViewById(R.id.title_time4);
            t5 = itemView.findViewById(R.id.title_time5);
            t6 = itemView.findViewById(R.id.title_time6);
            t7 = itemView.findViewById(R.id.title_time7);

            i1 = itemView.findViewById(R.id.item_time1);
            i2 = itemView.findViewById(R.id.item_time2);
            i3 = itemView.findViewById(R.id.item_time3);
            i4 = itemView.findViewById(R.id.item_time4);
            i5 = itemView.findViewById(R.id.item_time5);
            i6 = itemView.findViewById(R.id.item_time6);
            i7 = itemView.findViewById(R.id.item_time7);

        }
    }

}
