package com.kbs.pocis.adapter.myprojects;

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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

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
            case 3:// for invoice
                holder.item0.setText(model_project_services.get(position).service_name);
                holder.item1.setText(model_project_services.get(position).tariff);
                holder.item2.setText(model_project_services.get(position).parameter_1);
                holder.item3.setText(model_project_services.get(position).parameter_2);
                holder.item4.setText(model_project_services.get(position).amount_in_idr);
                setFormatIDR(model_project_services.get(position).amount_in_idr,holder.item4);
                break;
            case 4:// for proforma
                holder.item0.setText(model_project_services.get(position).service_name);
                holder.item1.setText(model_project_services.get(position).tariff);
                holder.item2.setText(model_project_services.get(position).parameter_1);
                holder.item3.setText(model_project_services.get(position).parameter_2);
                holder.item4.setText(model_project_services.get(position).total);
                setFormatIDR(model_project_services.get(position).total,holder.item4);
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
        textView.setText(kursIndo.format(Integer.valueOf(value)));
    }

    @Override
    public int getItemCount() {
        return model_project_services.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{

        TextView item0, item1,item2,item3,item4,item5,item6,item7,
                title0, title1,title2,title3,title4,title5,title6,title7;
        ConstraintLayout ln1,ln2,ln3,ln4,ln5,ln6,ln7;
        LinearLayout ln0;

        public vHolder(@NonNull View itemView) {
            super(itemView);

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

        }
    }

}
