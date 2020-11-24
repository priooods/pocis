package com.kbs.pocis.adapter.detailboking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_DetailsService;

import java.util.List;

public class Adapter_DetailsService extends RecyclerView.Adapter<Adapter_DetailsService.VHolder> {

    Context context;
    List<Model_DetailsService> model_bookings;

    public Adapter_DetailsService(Context context, List<Model_DetailsService> model_bookings) {
        this.context = context;
        this.model_bookings = model_bookings;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_detail_service, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.include.setText(model_bookings.get(position).getName());
        holder.price.setText("IDR " + model_bookings.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return model_bookings.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder{

        TextView include, price;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            include = itemView.findViewById(R.id.booking_details_service_include);
            price = itemView.findViewById(R.id.booking_details_service_price);
        }
    }
}
