package com.kbs.pocis.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Complain;
import com.kbs.pocis.model.Model_Project;

import java.util.List;

public class Adapter_Complain extends RecyclerView.Adapter<Adapter_Complain.vHolder> {

    Context context;
    List<Model_Complain> model_complains;

    public Adapter_Complain(Context context, List<Model_Complain> model_complains) {
        this.context = context;
        this.model_complains = model_complains;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project,parent,false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.number.setTextSize(11);
        holder.status.setTextSize(11);
        holder.ln3.setVisibility(View.GONE);
        holder.ln4.setVisibility(View.GONE);
        holder.ln5.setVisibility(View.GONE);
        holder.ln6.setVisibility(View.GONE);
        holder.ln7.setVisibility(View.GONE);
        holder.ln8.setVisibility(View.GONE);
        holder.title1.setText("Complaint Date");
        holder.title2.setText("Person In Charge");
        holder.item1.setText(model_complains.get(position).dates);
        holder.item2.setText(model_complains.get(position).person);
        holder.number.setText(model_complains.get(position).title);
        holder.status.setText(model_complains.get(position).status);

        switch (holder.status.getText().toString()){
            case "CLOSED":
                holder.status.setTextColor(Color.parseColor("#7d7d7d"));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return model_complains.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{
        LinearLayout ln3,ln4,ln5,ln6,ln7,ln8;
        TextView title1,title2,item1,item2,number,status;
        public vHolder(@NonNull View itemView) {
            super(itemView);
            ln3 = itemView.findViewById(R.id.ln3);
            ln4 = itemView.findViewById(R.id.ln4);
            ln5 = itemView.findViewById(R.id.ln5);
            ln6 = itemView.findViewById(R.id.ln6);
            ln7 = itemView.findViewById(R.id.ln7);
            ln8 = itemView.findViewById(R.id.ln8);
            title1 = itemView.findViewById(R.id.title1);
            title2 = itemView.findViewById(R.id.title2);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            number = itemView.findViewById(R.id.model_number);
            status = itemView.findViewById(R.id.model_status);
        }
    }
}
