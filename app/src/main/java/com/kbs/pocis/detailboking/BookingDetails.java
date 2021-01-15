package com.kbs.pocis.detailboking;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.ViewpagerDefault;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetails extends AppCompatActivity {

    String from, status, nomer, id;
    TextView topfrom, nomerBooking, statusBooking;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView btn_back;

    RelativeLayout layout_btn_bawah, layout_btn_verified, layout_ada, layout_kosong;
    Button cancel_booking, rejectTarif, approveTarif;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);

        //Ini untuk actionBar jadi white. Karena min sdk 16 jadi ga bisa di sett di style. harus lewat JAVA lngsung
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        //Ini untuk mengambil kembali data yang sudah dikirim dari adapter AllBookings atau Online Booking
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        status = intent.getStringExtra("status");
        nomer = intent.getStringExtra("nomer");
        id = intent.getStringExtra("id");

        topfrom = findViewById(R.id.booking_details_Textfrompage);
        nomerBooking = findViewById(R.id.booking_details_bookingNo);
        statusBooking = findViewById(R.id.booking_details_status);
        tabLayout = findViewById(R.id.bookingdetail_tablayout);
        viewPager = findViewById(R.id.bookingdetail_viewpager);
        btn_back = findViewById(R.id.btn_back_detail_booking);
        layout_btn_bawah = findViewById(R.id.bookingdetail_layoutBawah);
        layout_btn_verified = findViewById(R.id.ltr_btn_verified);
        cancel_booking = findViewById(R.id.btn_bokingdetail_cancelBooking);
        approveTarif = findViewById(R.id.btn_approve);
        rejectTarif = findViewById(R.id.btn_reject);
        layout_ada = findViewById(R.id.detail_ada);
        layout_kosong = findViewById(R.id.detail_kosong);

        DataGet();

        //Ini untuk mengatur setiap layout yang akan di tampilkan
        // pada tab detail booking

        topfrom.setText(from);
        nomerBooking.setText(nomer);
        statusBooking.setText(status);


        btn_back.setOnClickListener(v -> this.onBackPressed());

        //Kondisi untuk mensetting color text pada status yang berbeda
        KondisiStatus(status, statusBooking);
        KondisiButtonBawah(status, layout_btn_bawah, layout_btn_verified
                ,cancel_booking, rejectTarif, approveTarif, this);
    }


    public void DataGet(){
        UserData user = UserData.i;
        UserService service = user.getService();
        Intent intent = getIntent();
        String BookingId = intent.getStringExtra("id");
        Log.d(ContentValues.TAG, "id booking: " + BookingId);
        if (service == null) {
            Log.e("booking_detail","ERROR SERVICE");
        }
        assert service != null;
        Call<CallingDetail> call = service.getBookingDetail(user.getToken(),String.valueOf(BookingId));
        if (call == null) {
            Log.i("all_boking","DetailData Post Method is Bad!");
        }
        assert call != null;
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail respone =  response.body();
                assert respone != null;
                if (Calling.TreatResponse(BookingDetails.this, "detail_booking", respone)) {
                    Log.i("detail_booking",respone.data.Information.get(0).toString());
                    Model_Project.Information = respone.data.Information;
                    Model_Project.Service = respone.data.Service;
                    Model_Project.Commodity = respone.data.Commodity;
                    Model_Project.Documents = respone.data.Documents;
                    ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
                    viewpagerDefault.Addfragment(new BookingDetails_Information(),"Information");
                    viewpagerDefault.Addfragment(new BookingDetails_Service(),"Service");
                    viewpagerDefault.Addfragment(new BookingDetails_Commodity(),"Commodity");
                    viewPager.setAdapter(viewpagerDefault);
                    tabLayout.setupWithViewPager(viewPager);
                } else {
                    layout_kosong.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.e("detail_booking", "on Failure called!"+ t);
            }
        });
    }

    //Status booking dari setiap list nya disini setting nya
    private void KondisiStatus (String statused, TextView textView){
        switch (statused){
            case "APPROVED":
                textView.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
            case "CANCELED":
                textView.setTextColor(getResources().getColor(R.color.colorRed));
                break;
            case "BOOKING":
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "VERIFIED":
                textView.setTextColor(getResources().getColor(R.color.colorVerified));
                break;
        }
    }

    //Ini untuk kondisi button pada saat status yang berbeda
    private void KondisiButtonBawah(String statused, RelativeLayout layout, RelativeLayout layoutverif,
                                           Button cancel, Button reject, Button approve, final Activity context){
        //if (statused.)
        if (statused.equals("BOOKING")){
            layout.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(v -> ShowDialogCancell(context));
        } else if (statused.equals("VERIFIED")){
            layout.setVisibility(View.VISIBLE);
            reject.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            layoutverif.setVisibility(View.VISIBLE);

            reject.setOnClickListener(v -> ShowDialogReject(context));

            approve.setOnClickListener(v -> ShowDialogApprove(context));
        }
    }

    //Dialog form ketika cancelbutton click
    private void ShowDialogCancell (final Context context){
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(R.layout.dialog_cancelled);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = dialogFragment.findViewById(R.id.canceled_formInput);

        Button btn_close = dialogFragment.findViewById(R.id.btn_cancelclose);
        Button btn_cancelBoking = dialogFragment.findViewById(R.id.btn_cancelbookinggo);

        btn_close.setOnClickListener(v1 -> dialogFragment.cancel());
        btn_cancelBoking.setOnClickListener(v12 -> CallingApiCancelBooking(input_alasan, context, dialogFragment));
        dialogFragment.show();
    }

    //Dialog form ketika approve tarif click
    private void ShowDialogApprove (final Context context){
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(R.layout.dialog_approve_tarif);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = dialogFragment.findViewById(R.id.approve_formInput);
        input_alasan.setVisibility(View.GONE);

        Button btn_close = dialogFragment.findViewById(R.id.btn_approveclose);
        Button btn_approve = dialogFragment.findViewById(R.id.btn_approvetarif);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_approve.setOnClickListener(v -> CallingApiApproveTariff(input_alasan, context, dialogFragment));
        dialogFragment.show();
    }

    //Dialog form ketika reject tariff click
    private void ShowDialogReject (final Context context){
        Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(R.layout.dialog_reject_tarif);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputEditText input_alasan = dialogFragment.findViewById(R.id.reject_formInput);

        Button btn_close = dialogFragment.findViewById(R.id.btn_rejectclose);
        Button btn_rejectTerif = dialogFragment.findViewById(R.id.btn_rejecttarif);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_rejectTerif.setOnClickListener(v -> CallingApiRejectTariff(input_alasan, context, dialogFragment));
        dialogFragment.show();
    }

    private void CallingApiCancelBooking(TextInputEditText remark, Context context, Dialog dialog){
        Intent intent = getIntent();
        String BookingId = intent.getStringExtra("id");
        Call<CallingDetail> call = UserData.i.getService().cancelBooking(UserData.i.getToken(), BookingId, Objects.requireNonNull(remark.getText()).toString());
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
                        Model_Project.Code = 0;
                        sweetAlertDialog.dismiss();
                        dialog.dismiss();
                        ((AppCompatActivity)context).onBackPressed();
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


    private void CallingApiRejectTariff(TextInputEditText remark, Context context, Dialog dialog){
        Intent intent = getIntent();
        String BookingId = intent.getStringExtra("id");
        Call<CallingDetail> call = UserData.i.getService().rejectTariff(UserData.i.getToken(), BookingId, Objects.requireNonNull(remark.getText()).toString());
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
                        Model_Project.Code = 1;
                        sweetAlertDialog.dismiss();
                        dialog.dismiss();
                        ((AppCompatActivity)context).onBackPressed();
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

    private void CallingApiApproveTariff(TextInputEditText remark, Context context, Dialog dialog){
        Intent intent = getIntent();
        String BookingId = intent.getStringExtra("id");
        Call<CallingDetail> call = UserData.i.getService().approveTariff(UserData.i.getToken(), BookingId, Objects.requireNonNull(remark.getText()).toString());
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
                        Model_Project.Code = 1;
                        sweetAlertDialog.dismiss();
                        dialog.dismiss();
                        ((AppCompatActivity)context).onBackPressed();
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
