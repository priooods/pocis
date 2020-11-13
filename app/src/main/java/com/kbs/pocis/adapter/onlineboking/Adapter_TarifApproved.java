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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.detailboking.BookingDetails;
import com.kbs.pocis.model.Model_Bookings;

import java.lang.reflect.Method;
import java.util.List;

public class Adapter_TarifApproved extends RecyclerView.Adapter<Adapter_TarifApproved.VHolder> {

    Context context;
    List<Model_Bookings> model_bookings;

    public Adapter_TarifApproved(Context context, List<Model_Bookings> model_bookings) {
        this.context = context;
        this.model_bookings = model_bookings;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_all_booking, parent,false);
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

        if (holder.status.getText().toString().equals("verified")){
            holder.status.setText(R.string.verified);
            holder.status.setTextColor(Color.parseColor("#1A2CD1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#1A2CD1"));
            holder.garis.setBackgroundColor(Color.parseColor("#1A2CD1"));
        }

        holder.tap_toDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing data to another Screen
                //Default passing to Screen Details Booking
                Intent intent = new Intent(context, BookingDetails.class);
                intent.putExtra("from", "Tarif Approve");
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

        holder.dropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.titikdua);
                popupMenu.inflate(R.menu.menu_tarif_approve);

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
                            case R.id.reject:
                                ShowDialogReject(context);
                                break;
                            case  R.id.approve:
                                ShowDialogApprove(context);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_bookings.size();
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

    //Dialog form ketika approve tarif click
    private static void ShowDialogApprove (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_approve_tarif, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = view.findViewById(R.id.approve_formInput);

        Button btn_close = view.findViewById(R.id.btn_approveclose);
        Button btn_approve = view.findViewById(R.id.btn_approvetarif);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Approve Tariff Success")
                        .setCustomImage(R.drawable.success_img)
                        .show();
                dialogFragment.cancel();
            }
        });
        dialogFragment.show();
    }

    //Dialog form ketika reject tariff click
    private static void ShowDialogReject (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_reject_tarif, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = view.findViewById(R.id.reject_formInput);

        Button btn_close = view.findViewById(R.id.btn_rejectclose);
        Button btn_rejectTerif = view.findViewById(R.id.btn_rejecttarif);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_rejectTerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Reject Tariff Success")
                        .setCustomImage(R.drawable.success_img)
                        .showCancelButton(false)
                        .show();
                dialogFragment.cancel();
            }
        });
        dialogFragment.show();
    }
}
