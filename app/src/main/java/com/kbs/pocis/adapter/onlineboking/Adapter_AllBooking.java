package com.kbs.pocis.adapter.onlineboking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.detailboking.BookingDetails;
import com.kbs.pocis.model.Model_Bookings;

import java.util.List;

public class Adapter_AllBooking extends RecyclerView.Adapter<Adapter_AllBooking.VHolder> {

    Context context;
    List<Model_Bookings> model_bookings;

    public Adapter_AllBooking(Context context, List<Model_Bookings> model_bookings) {
        this.context = context;
        this.model_bookings = model_bookings;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_all_booking, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder holder, final int position) {
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
        holder.titikdua.setVisibility(View.GONE);

        if (holder.status.getText().toString().equals("approved")){
            holder.status.setText(R.string.approved);
            holder.status.setTextColor(Color.parseColor("#4BA459"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#4BA459"));
            holder.garis.setBackgroundColor(Color.parseColor("#4BA459"));
        } else if (holder.status.getText().toString().equals("cancelled")){
            holder.status.setText(R.string.cancelled);
            holder.status.setTextColor(Color.parseColor("#D41111"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#D41111"));
            holder.garis.setBackgroundColor(Color.parseColor("#D41111"));
        } else  if (holder.status.getText().toString().equals("verified")){
            holder.status.setText(R.string.verified);
            holder.status.setTextColor(Color.parseColor("#1A2CD1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#1A2CD1"));
            holder.garis.setBackgroundColor(Color.parseColor("#1A2CD1"));
        } else {
            holder.status.setText(R.string.booking);
            holder.status.setTextColor(Color.parseColor("#00a1d1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#00a1d1"));
            holder.garis.setBackgroundColor(Color.parseColor("#00a1d1"));
        }

        holder.tap_toDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing data to another Screen
                //Default passing to Screen Details Booking
                Intent intent = new Intent(context, BookingDetails.class);
                intent.putExtra("from", "All Bookings");
                intent.putExtra("nomer", model_bookings.get(position).getNomerBook());
                intent.putExtra("status", model_bookings.get(position).getStatusBook());
                intent.putExtra("contract", model_bookings.get(position).getContractNo());
                intent.putExtra("nama", model_bookings.get(position).getCustomerName());
                intent.putExtra("vesel", model_bookings.get(position).getVesselName());
                intent.putExtra("type", model_bookings.get(position).getCustomerType());
                intent.putExtra("flagvesel", model_bookings.get(position).getFlagVessel());
                intent.putExtra("date", model_bookings.get(position).getBookingDate());
                intent.putExtra("flagcontract", model_bookings.get(position).getFlagContract());
                intent.putExtra("time", model_bookings.get(position).getBookingTime());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model_bookings.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder {

        LinearLayout bg_color, garis;
        ConstraintLayout tap_toDetails;
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

            tap_toDetails = itemView.findViewById(R.id.go_detail_booking);
        }
    }
}
