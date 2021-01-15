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
import com.kbs.pocis.activity.OnlineBook;
import com.kbs.pocis.detailboking.BookingDetails;
import com.kbs.pocis.model.onlineboking.Model_Bookings;
import com.kbs.pocis.onlineboking.OnlineBooking;
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

import static android.content.ContentValues.TAG;

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
        else if (holder.status.getText().toString().equals("CANCELED")){
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
            holder.titikdua.setVisibility(View.VISIBLE);
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

        holder.dropdownMenu.setOnClickListener(v -> {
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
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.cancel:
                        ShowDialogCancell(context, model_bookings.get(position).getBookingId());
                        break;
                    case  R.id.detail:
                        GoDetails(position);
                        break;
                }
                return false;
            });
            popupMenu.show();
        });


        holder.tap_toDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingDetails.class);
            intent.putExtra("from", "All Bookings");
            intent.putExtra("nomer", model_bookings.get(position).getNomerBook());
            intent.putExtra("status", model_bookings.get(position).getStatusBook());
            intent.putExtra("id", model_bookings.get(position).getBookingId());
            Log.i(TAG, "id booking: " + model_bookings.get(position).getBookingId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return model_bookings.size();
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

        LinearLayout bg_color, garis,dropdownMenu;
        ConstraintLayout tap_toDetails;
        TextView nomerBooking, status, nomerContract, flagVessel, customerName,
            vesselName, customerType, bookingDate, bookingTime, flagContract, titikdua;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            dropdownMenu = itemView.findViewById(R.id.model_tapdropdown);
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

    public void GoDetails(int position){
        Intent intent = new Intent(context, BookingDetails.class);
        intent.putExtra("from", "Cancel Bookings");
        intent.putExtra("status", model_bookings.get(position).getStatusBook());
        intent.putExtra("id", model_bookings.get(position).getBookingId());
        intent.putExtra("nomer", model_bookings.get(position).getNomerBook());
        context.startActivity(intent);
    }

    //Dialog form ketika cancelbutton click
    public void ShowDialogCancell (final Context context, String bookingId){
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(R.layout.dialog_cancelled);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = dialogFragment.findViewById(R.id.canceled_formInput);

        Button btn_close = dialogFragment.findViewById(R.id.btn_cancelclose);
        Button btn_cancelBoking = dialogFragment.findViewById(R.id.btn_cancelbookinggo);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_cancelBoking.setOnClickListener(v -> CallingApiCancelBooking(input_alasan, context, dialogFragment,bookingId));
        dialogFragment.show();
    }


    public void CallingApiCancelBooking(TextInputEditText remark, Context context, Dialog dialog, String bookingId){
        Call<CallingDetail> call = UserData.i.getService().cancelBooking(UserData.i.getToken(), bookingId, Objects.requireNonNull(remark.getText()).toString());
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(context, "cancel_booking", data)){
                    assert data != null;
                    Log.i("cancel_booking", "onResponse: " + data.data.no_booking + " status : " + "berhasil");
                    SweetAlertDialog d = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    d.setTitleText("Cancel Booking Success");
                    d.setCancelable(false);
                    d.setCustomImage(R.drawable.success_img);
                    d.setConfirmButton("Back", sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        dialog.dismiss();
                        Fragment fragment = new OnlineBooking();
                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameOnline, fragment);
                        fragmentTransaction.commit();
                    });
                    d.showCancelButton(false);
                    d.show();
                } else {
                    Log.i("cancel_booking", "onResponse: " + "Cancel Booking Failure");
                    Toasty.error(context, "Oops... Server Error", Toasty.LENGTH_LONG,true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("cancel_booking", "onFailure: ", t);
            }
        });
    }
}
