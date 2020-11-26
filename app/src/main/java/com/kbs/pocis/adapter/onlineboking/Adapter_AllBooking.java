package com.kbs.pocis.adapter.onlineboking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.detailboking.BookingDetails;
import com.kbs.pocis.model.onlineboking.Model_Bookings;

import java.util.ArrayList;
import java.util.List;

public class Adapter_AllBooking extends RecyclerView.Adapter<Adapter_AllBooking.VHolder> implements Filterable {

    Context context;
    List<Model_Bookings> model_bookings;
    List<Model_Bookings> model;

    public Adapter_AllBooking(Context context, List<Model_Bookings> model_bookings) {
        this.context = context;
        this.model_bookings = model_bookings;
        this.model = model_bookings;
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

        if (holder.status.getText().toString().equals("APPROVED")){
            holder.status.setText(R.string.approved);
            holder.status.setTextColor(Color.parseColor("#4BA459"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#4BA459"));
            holder.garis.setBackgroundColor(Color.parseColor("#4BA459"));
        }
        else if (holder.status.getText().toString().equals("CANCELLED")){
            holder.status.setText(R.string.cancelled);
            holder.status.setTextColor(Color.parseColor("#D41111"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#D41111"));
            holder.garis.setBackgroundColor(Color.parseColor("#D41111"));
        }
        else if (holder.status.getText().toString().equals("VERIFIED")){
            holder.status.setText(R.string.verified);
            holder.status.setTextColor(Color.parseColor("#1A2CD1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#1A2CD1"));
            holder.garis.setBackgroundColor(Color.parseColor("#1A2CD1"));
        }
        else if (holder.status.getText().toString().equals("BOOKING")) {
            holder.status.setText(R.string.booking);
            holder.status.setTextColor(Color.parseColor("#00a1d1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#00a1d1"));
            holder.garis.setBackgroundColor(Color.parseColor("#00a1d1"));
        }
        else {
            holder.status.setText(model_bookings.get(position).getStatusBook());
            holder.status.setTextColor(Color.parseColor("#1A2CD1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#1A2CD1"));
            holder.garis.setBackgroundColor(Color.parseColor("#1A2CD1"));
        }

        holder.tap_toDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingDetails.class);
                intent.putExtra("from", "All Bookings");
                intent.putExtra("id", model_bookings.get(position).getBookingId());
                intent.putExtra("status", model_bookings.get(position).getStatusBook());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (model != null){
            return model_bookings.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()){
                    model_bookings = model;
                } else {
                    List<Model_Bookings> bookings = new ArrayList<>();
                    for (Model_Bookings modelBookings : model){
                        if (modelBookings.getNomerBook().toLowerCase().contains(key.toLowerCase()) ||
                                modelBookings.getVesselName().toLowerCase().contains(key.toLowerCase())){
                            bookings.add(modelBookings);
                        }
                    }

                    model_bookings = bookings;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = model_bookings;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                model_bookings = (List<Model_Bookings>)results.values;
                notifyDataSetChanged();
            }
        };
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
