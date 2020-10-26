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
import com.kbs.pocis.model.Model_Bookings;

import java.util.List;

public class Adapter_CancelBooking extends RecyclerView.Adapter<Adapter_CancelBooking.VHolder> {

    Context context;
    List<Model_Bookings> model_bookings;

    public Adapter_CancelBooking(Context context, List<Model_Bookings> model_bookings) {
        this.context = context;
        this.model_bookings = model_bookings;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_all_booking,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.nomerBooking.setText(model_bookings.get(position).getNomerBook());
        holder.nomerContract.setText(model_bookings.get(position).getContractNo());
        holder.customerName.setText(model_bookings.get(position).getCustomerName());
        holder.vesselName.setText(model_bookings.get(position).getVesselName());
        holder.customerType.setText(model_bookings.get(position).getCustomerType());
        holder.flagVessel.setText(model_bookings.get(position).getFlagVessel());
        holder.bookingDate.setText(model_bookings.get(position).getBookingDate());
        holder.flagContract.setText(model_bookings.get(position).getFlagContract());
        holder.bookingTime.setText(model_bookings.get(position).getBookingTime());
        holder.status.setText(model_bookings.get(position).getStatusBook());

        if (holder.status.getText().toString().equals("booking")){
            holder.status.setTextColor(Color.parseColor("#00a1d1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#00a1d1"));
            holder.garis.setBackgroundColor(Color.parseColor("#00a1d1"));
        }
    }

    @Override
    public int getItemCount() {
        return model_bookings.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder{

        LinearLayout bg_color, garis;
        TextView nomerBooking, status, nomerContract, flagVessel, customerName,
                vesselName, customerType, bookingDate, bookingTime, flagContract, titikdua;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            bg_color = itemView.findViewById(R.id.bg_card_all_booking);
            garis = itemView.findViewById(R.id.booking_garis);
            nomerBooking = itemView.findViewById(R.id.booking_nomerBooking);
            status = itemView.findViewById(R.id.booking_statusBooking);
            nomerContract = itemView.findViewById(R.id.booking_contractNo);
            flagVessel = itemView.findViewById(R.id.booking_flagvessel);
            customerName = itemView.findViewById(R.id.booking_customerName);
            vesselName = itemView.findViewById(R.id.booking_vesselName);
            customerType = itemView.findViewById(R.id.booking_customerType);
            bookingDate = itemView.findViewById(R.id.booking_bookingDate);
            bookingTime = itemView.findViewById(R.id.booking_bookingTime);
            flagContract = itemView.findViewById(R.id.booking_flagContract);
            titikdua = itemView.findViewById(R.id.booking_titikdua);
        }
    }
}
