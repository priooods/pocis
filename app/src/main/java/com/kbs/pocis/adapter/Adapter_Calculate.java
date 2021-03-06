package com.kbs.pocis.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Complain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class Adapter_Calculate extends RecyclerView.Adapter<Adapter_Calculate.vHolder> {

    Context context;
    List<Model_Complain> model_complains;
    int status;

    public Adapter_Calculate(Context context, List<Model_Complain> model_complains, int status) {
        this.context = context;
        this.model_complains = model_complains;
        this.status = status;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_tarif_detail,parent,false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        switch (status){
            case 0:
                holder.ln_ship.setVisibility(View.GONE);
                holder.ln_good.setVisibility(View.VISIBLE);
                holder.good1.setText(model_complains.get(position).service_name);
                holder.good2.setText(model_complains.get(position).parameter);
                if (model_complains.get(position).tariff != null){
                    try {
                        String str = new DecimalFormat("#,###,###.##").format(Double.parseDouble(model_complains.get(position).tariff));
                        holder.good3.setText(str);
                    } catch (Exception e){
                        holder.good3.setText(null);
                    }
                }
                break;
            case 1:
                holder.ln_good.setVisibility(View.GONE);
                holder.ln_ship.setVisibility(View.VISIBLE);
                holder.ship_title.setText(model_complains.get(position).service_name);
                holder.ship1.setText(model_complains.get(position).parameter);
                if (model_complains.get(position).tarif_internasional != null){
                    try {
                        String str = new DecimalFormat("#,###,###.##").format(Double.parseDouble(model_complains.get(position).tarif_internasional));
                        holder.ship3.setText(str);
                    } catch (Exception e){
                        holder.ship3.setText(null);
                    }
                }
                if (model_complains.get(position).tarif_domestik != null) {
                    try {
                        String str = new DecimalFormat("#,###.##").format(Double.parseDouble(model_complains.get(position).tarif_domestik));
                        holder.ship2.setText(str);
                    } catch (Exception e){
                        holder.ship2.setText(null);
                    }
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return model_complains.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{

        TextView good1,good2,good3,ship1,ship2,ship3,ship_title;
        ConstraintLayout ln_good,ln_ship;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            good1 = itemView.findViewById(R.id.good_item1);
            good2 = itemView.findViewById(R.id.good_item2);
            good3 = itemView.findViewById(R.id.good_item3);
            ship1 = itemView.findViewById(R.id.ship_item1);
            ship2 = itemView.findViewById(R.id.ship_item2);
            ship3 = itemView.findViewById(R.id.ship_item3);
            ship_title = itemView.findViewById(R.id.ship_top_title);
            ln_good = itemView.findViewById(R.id.good);
            ln_ship = itemView.findViewById(R.id.ship);

        }
    }
}
