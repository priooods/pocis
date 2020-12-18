package com.kbs.pocis.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Monitoring;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.myproject.Detail_MyProject;

import java.util.List;

public class Adapter_Monitoring extends RecyclerView.Adapter<Adapter_Monitoring.vHolder> {

    Context context;
    List<Model_Monitoring> model_monitorings;
    int type;

    public Adapter_Monitoring(Context context, List<Model_Monitoring> model_monitorings, int type) {
        this.context = context;
        this.model_monitorings = model_monitorings;
        this.type = type;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.status.setTextColor(Color.parseColor("#7d7d7d"));
        holder.ln7.setVisibility(View.GONE);
        holder.ln8.setVisibility(View.GONE);
        holder.status.setTextSize(10);
        holder.number.setTextSize(10);
        holder.number_bot.setTextSize(10);
        holder.number_bot.setVisibility(View.VISIBLE);
        switch (type){
            case 0:
            case 1:
            case 2:
                holder.title_1.setText(R.string.voyage);
                holder.title_2.setText(R.string.jetty);
                holder.title_3.setText(R.string.est_betring);
                holder.title_4.setText(R.string.act_betring);
                holder.title_5.setText(R.string.est_depart);
                holder.title_6.setText(R.string.act_depart);

                holder.number.setText(model_monitorings.get(position).name);
                holder.number_bot.setText(model_monitorings.get(position).code);
                holder.item3.setText(model_monitorings.get(position).est_bethring);
                holder.item1.setText(model_monitorings.get(position).voyage);
                holder.item2.setText(model_monitorings.get(position).jetty);
                holder.item4.setText(model_monitorings.get(position).act_bethring);
                holder.item5.setText(model_monitorings.get(position).est_departure);
                holder.item6.setText(model_monitorings.get(position).act_departure);
                holder.status.setText(model_monitorings.get(position).status);
                break;
            case 3:
                holder.number_bot.setVisibility(View.GONE);
                holder.title_1.setText(R.string.est_arival);
                holder.title_2.setText(R.string.jetty);
                holder.title_3.setText(R.string.est_betring);
                holder.title_4.setText(R.string.voyage);
                holder.title_5.setText(R.string.est_depart);
                holder.title_6.setText(R.string.ship_line);


                holder.number.setText(model_monitorings.get(position).name);
                holder.item3.setText(model_monitorings.get(position).est_bethring);
                holder.item1.setText(model_monitorings.get(position).est_arival);
                holder.item2.setText(model_monitorings.get(position).jetty);
                holder.item4.setText(model_monitorings.get(position).voyage);
                holder.item5.setText(model_monitorings.get(position).est_departure);
                holder.item6.setText(model_monitorings.get(position).ship_line);
                holder.status.setText(model_monitorings.get(position).status);
                break;
        }


        switch (holder.status.getText().toString()){
            case "PLANNED":
                holder.status.setTextColor(Color.parseColor("#4BA459"));
                break;
            case "NOT PLANNED":
                holder.status.setTextColor(Color.parseColor("#D41111"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return model_monitorings.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{
        LinearLayout ln_top1, ln_top2,ln4,ln5,ln6,ln7,ln8;
        TextView number, number_bot, status, item_top1,item_top2, item1, item3, item2, item5, item4, item6,
                title_top1,title_top2,title_1,title_2,title_3,title_4,title_5, title_6;
        RelativeLayout gotoo;
        public vHolder(@NonNull View itemView) {
            super(itemView);
            number_bot = itemView.findViewById(R.id.model_number_bot);
            item_top1 = itemView.findViewById(R.id.item_top1);
            item_top2 = itemView.findViewById(R.id.item_top2);
            item1 = itemView.findViewById(R.id.item1);
            item3 = itemView.findViewById(R.id.item3);
            item2 = itemView.findViewById(R.id.item2);
            item5 = itemView.findViewById(R.id.item5);
            item4 = itemView.findViewById(R.id.item4);
            item6 = itemView.findViewById(R.id.item6);
            title_top1 = itemView.findViewById(R.id.title_top1);
            title_top2 = itemView.findViewById(R.id.title_top2);
            title_1 = itemView.findViewById(R.id.title1);
            title_2 = itemView.findViewById(R.id.title2);
            title_3 = itemView.findViewById(R.id.title3);
            title_4 = itemView.findViewById(R.id.title4);
            title_5 = itemView.findViewById(R.id.title5);
            title_6 = itemView.findViewById(R.id.title6);
            ln_top1 = itemView.findViewById(R.id.ln_top1);
            ln_top2 = itemView.findViewById(R.id.ln_top2);
            ln4 = itemView.findViewById(R.id.ln4);
            ln5 = itemView.findViewById(R.id.ln5);
            ln6 = itemView.findViewById(R.id.ln6);
            ln7 = itemView.findViewById(R.id.ln7);
            ln8 = itemView.findViewById(R.id.ln8);
            gotoo = itemView.findViewById(R.id.layout_project);
            number = itemView.findViewById(R.id.model_number);
            status = itemView.findViewById(R.id.model_status);
        }
    }
}
