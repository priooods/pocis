package com.kbs.pocis.adapter.onlineboking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.detailboking.BookingDetails;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.model.onlineboking.Model_TariffAprove;
import com.kbs.pocis.onlineboking.TarifApprove;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_TarifApproved extends RecyclerView.Adapter<Adapter_TarifApproved.VHolder> implements Filterable {

    Context context;
    List<Model_TariffAprove> model_tariffAproves;
    List<Model_TariffAprove> modelfilter;

    public Adapter_TarifApproved(Context context, List<Model_TariffAprove> model_bookings) {
        this.context = context;
        this.model_tariffAproves = model_bookings;
        this.modelfilter = model_bookings;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_all_booking, parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder holder, final int position) {
        holder.nomerBooking.setText(model_tariffAproves.get(position).getNomer_boking());
        holder.nomerContract.setText(model_tariffAproves.get(position).getContractNo());
        holder.customerName.setText(model_tariffAproves.get(position).getCustomerName());
        holder.vesselName.setText(model_tariffAproves.get(position).getVessel_Name());
        holder.customerType.setText(model_tariffAproves.get(position).getCustomerType_code());
        holder.flagVessel.setText(model_tariffAproves.get(position).getFlagRelated_vessel());
        holder.bookingDate.setText(model_tariffAproves.get(position).getEst_arival());
        holder.flagContract.setText(model_tariffAproves.get(position).getFlag_contract());
        holder.bookingTime.setText(model_tariffAproves.get(position).getEst_berthing());
        holder.status.setText(model_tariffAproves.get(position).getBookingStatus());

        if (holder.status.getText().toString().equals("VERIFIED")){
            holder.status.setText(R.string.verified);
            holder.status.setTextColor(Color.parseColor("#1A2CD1"));
            holder.bg_color.setBackgroundColor(Color.parseColor("#1A2CD1"));
            holder.garis.setBackgroundColor(Color.parseColor("#1A2CD1"));
        }

        holder.tap_toDetails.setOnClickListener(v -> {
            Model_Project.Check = 1;
            Intent intent = new Intent(context, BookingDetails.class);
            intent.putExtra("from", "Tarif Approve");
            intent.putExtra("id", model_tariffAproves.get(position).getBookingId());
            intent.putExtra("nomer", model_tariffAproves.get(position).getNomer_boking());
            intent.putExtra("status", model_tariffAproves.get(position).getBookingStatus());

            context.startActivity(intent);
        });

        holder.dropdownMenu.setOnClickListener(v -> {
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
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.reject:
                        ShowDialogReject(context, model_tariffAproves.get(position).getBookingId());
                        break;
                    case  R.id.approve:
                        ShowDialogApprove(context, model_tariffAproves.get(position).getBookingId());
                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return model_tariffAproves.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()){
                    model_tariffAproves = modelfilter;
                } else {
                    List<Model_TariffAprove> bookings = new ArrayList<>();
                    for (Model_TariffAprove modelBookings : modelfilter){
                        if (modelBookings.getNomer_boking().toLowerCase().contains(key.toLowerCase()) ||
                                modelBookings.getVessel_Name().toLowerCase().contains(key.toLowerCase())){
                            bookings.add(modelBookings);
                        }
                    }

                    model_tariffAproves = bookings;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = model_tariffAproves;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                model_tariffAproves = (List<Model_TariffAprove>)results.values;
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

    //Dialog form ketika approve tarif click
    public void ShowDialogApprove (final Context context, String bookingId){
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(R.layout.dialog_approve_tarif);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = dialogFragment.findViewById(R.id.approve_formInput);
        input_alasan.setVisibility(View.GONE);
        Button btn_close = dialogFragment.findViewById(R.id.btn_approveclose);
        Button btn_approve = dialogFragment.findViewById(R.id.btn_approvetarif);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_approve.setOnClickListener(v -> CallingApiApproveTariff(input_alasan, context, dialogFragment, bookingId));
        dialogFragment.show();
    }

    //Dialog form ketika reject tariff click
    public void ShowDialogReject (final Context context, String bookingId){
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(R.layout.dialog_reject_tarif);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = dialogFragment.findViewById(R.id.reject_formInput);

        Button btn_close = dialogFragment.findViewById(R.id.btn_rejectclose);
        Button btn_rejectTerif = dialogFragment.findViewById(R.id.btn_rejecttarif);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_rejectTerif.setOnClickListener(v -> CallingApiRejectTariff(input_alasan, context, dialogFragment, bookingId));
        dialogFragment.show();
    }

    public void CallingApiRejectTariff(TextInputEditText remark, Context context, Dialog dialog, String bookingId){
        Call<CallingDetail> call = UserData.i.getService().rejectTariff(UserData.i.getToken(), bookingId, Objects.requireNonNull(remark.getText()).toString());
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(context, "reject_tariff", data)){
                    assert data != null;
                    Log.i("reject_tariff", "onResponse: " + data.data.no_booking + " status : " + "berhasil");
                    SweetAlertDialog d = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    d.setTitleText("Reject Tariff Success");
                    d.setCancelable(false);
                    d.setCustomImage(R.drawable.success_img);
                    d.setConfirmButton("Back", sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        dialog.dismiss();
                        Fragment fragment = new TarifApprove();
                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameOnline, fragment);
                        fragmentTransaction.commit();
                    });
                    d.showCancelButton(false);
                    d.show();
                } else {
                    Log.i("reject_tariff", "onResponse: " + "Reject Tariff Failure");
                    Toasty.error(context, "Oops... Server Error", Toasty.LENGTH_LONG,true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("reject_tariff", "onFailure: ", t);
            }
        });
    }


    public void CallingApiApproveTariff(TextInputEditText remark, Context context, Dialog dialog, String bookingId){
        Call<CallingDetail> call = UserData.i.getService().approveTariff(UserData.i.getToken(), bookingId, Objects.requireNonNull(remark.getText()).toString());
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(context, "approve_tariff", data)){
                    assert data != null;
                    Log.i("approve_tariff", "onResponse: " + data.data.no_booking + " status : " + "berhasil");
                    SweetAlertDialog d = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    d.setTitleText("Approve Tariff Success");
                    d.setCancelable(false);
                    d.setCustomImage(R.drawable.success_img);
                    d.setConfirmButton("Back", sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        dialog.dismiss();
                        Fragment fragment = new TarifApprove();
                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameOnline, fragment);
                        fragmentTransaction.commit();
                    });
                    d.showCancelButton(false);
                    d.show();
                } else {
                    Log.i("approve_tariff", "onResponse: " + "Approve Tariff Failure");
                    Toasty.error(context, "Oops... Server Error", Toasty.LENGTH_LONG,true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("approve_tariff", "onFailure: ", t);
            }
        });
    }
}
