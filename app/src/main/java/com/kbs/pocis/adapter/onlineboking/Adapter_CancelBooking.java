package com.kbs.pocis.adapter.onlineboking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.detailboking.BookingDetails;
import com.kbs.pocis.model.onlineboking.Model_Bookings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Adapter_CancelBooking extends RecyclerView.Adapter<Adapter_CancelBooking.VHolder> implements Filterable {

    Context context;
    List<Model_Bookings> model_bookings;
    List<Model_Bookings> model;

    public Adapter_CancelBooking(Context context, List<Model_Bookings> model_bookings) {
        this.context = context;
        this.model_bookings = model_bookings;
        this.model = model_bookings;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_all_booking,parent,false);
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
        holder.titikdua.setVisibility(View.VISIBLE);

        if (holder.status.getText().toString().equals("BOOKING")){
            holder.status.setTextColor(Color.parseColor("#00a1d1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#00a1d1"));
            holder.garis.setBackgroundColor(Color.parseColor("#00a1d1"));
        }

        holder.dropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.titikdua);
                popupMenu.inflate(R.menu.menu_cancel_dropdown);

                //Ini untuk memunculkan PopUp dengan Icon yah
                try {
                    Method method = popupMenu.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                    method.setAccessible(true);
                    method.invoke(popupMenu.getMenu(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //ketika menu di popUp di click
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.cancel:
                                ShowDialogCancell(context);
                                break;
                            case  R.id.detail:
                                GoDetails(position);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        holder.tap_toDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoDetails(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  model_bookings.size();
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

    public static class VHolder extends RecyclerView.ViewHolder{

        LinearLayout bg_color, garis, dropdownMenu;
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
            dropdownMenu = itemView.findViewById(R.id.model_tapdropdown);
        }
    }

    public void GoDetails(int position){
        Intent intent = new Intent(context, BookingDetails.class);
        intent.putExtra("from", "Cancel Bookings");
        intent.putExtra("status", model_bookings.get(position).getStatusBook());
        intent.putExtra("id", model_bookings.get(position).getBookingId());
        intent.putExtra("nomer", model_bookings.get(position).getNomerBook());
        context.startActivity(intent);
    }

    //Dialog form ketika cancelbutton click
    private static void ShowDialogCancell (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_cancelled, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = view.findViewById(R.id.canceled_formInput);

        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        Button btn_cancelBoking = view.findViewById(R.id.btn_cancelbookinggo);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_cancelBoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Cancell Booking Success")
                        .setCustomImage(R.drawable.success_img)
                        .show();
                dialogFragment.cancel();
            }
        });
        dialogFragment.show();
    }

}
