package com.kbs.pocis.adapter.detailboking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_DetailsCommodity;

import java.util.List;

public class Adapter_DetailCommodity extends RecyclerView.Adapter<Adapter_DetailCommodity.VHolder> {

    Context context;
    List<Model_DetailsCommodity> model_detailsCommodities;

    public Adapter_DetailCommodity(Context context, List<Model_DetailsCommodity> model_detailsCommodities) {
        this.context = context;
        this.model_detailsCommodities = model_detailsCommodities;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_detail_commodity, parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.name.setText(model_detailsCommodities.get(position).getCommodityName());
        holder.type.setText(model_detailsCommodities.get(position).getCommodityType());

        holder.tonage.setText((model_detailsCommodities.get(position).tonage));


        holder.packageNo.setText(model_detailsCommodities.get(position).packaging);
    }

    @Override
    public int getItemCount() {
        return model_detailsCommodities.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder{
        TextView packageNo, tonage, name, type;
        public VHolder(@NonNull View itemView) {
            super(itemView);

            packageNo = itemView.findViewById(R.id.detail_comodity_package);
            tonage = itemView.findViewById(R.id.detail_comodity_tonage);
            name = itemView.findViewById(R.id.detail_comodity_name);
            type = itemView.findViewById(R.id.detail_comodity_type);
        }
    }
}
