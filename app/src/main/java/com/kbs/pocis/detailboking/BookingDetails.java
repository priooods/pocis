package com.kbs.pocis.detailboking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.BookingList;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.DetailData;

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

        DataGet(this);

        //Ini untuk mengatur setiap layout yang akan di tampilkan
        // pada tab detail booking

        topfrom.setText(from);
        nomerBooking.setText(nomer);
        statusBooking.setText(status);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingDetails.super.onBackPressed();
            }
        });

        //Kondisi untuk mensetting color text pada status yang berbeda
        KondisiStatus(status, statusBooking,this);
        KondisiButtonBawah(status, layout_btn_bawah, layout_btn_verified
                ,cancel_booking, rejectTarif, approveTarif, this);
    }


    public void DataGet(Activity activity){
        UserData user = UserData.i;
        UserService service = user.getService();
        Intent intent = getIntent();
        String BookingId = intent.getStringExtra("id");
        Log.d("TAG", "DataGet: " + BookingId);
        if (service == null) {
            Log.e("booking_detail","ERROR SERVICE");
        }
        Call<DetailData> call = service.getBookingDetail(user.getToken(),String.valueOf(BookingId));
        if (call == null) {
            Log.i("all_boking","DetailData Post Method is Bad!");
        }
        call.enqueue(new Callback<DetailData>() {
            @Override
            public void onResponse(Call<DetailData> call, Response<DetailData> response) {
                DetailData respone = (DetailData) response.body();

                if (DetailData.TreatResponse(activity, "detail_booking", respone)) {
                    Log.i("detail_booking",respone.readString());
                    BookingDetailData.i = respone.data;
                    ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
                    viewpagerDefault.Addfragment(new BookingDetails_Information(),"Information");
                    viewpagerDefault.Addfragment(new BookingDetails_Service(),"Service");
                    viewpagerDefault.Addfragment(new BookingDetails_Commodity(),"Commodity");
                    viewPager.setAdapter(viewpagerDefault);
                    tabLayout.setupWithViewPager(viewPager);
                    return;
                } else if (Integer.parseInt(BookingId)-1<BookingList.getI().data_list.size()){
                    Log.e("detail_booking", "Failed on API : \n Error " + (respone!=null?respone.error:"NULL?") + " : " + (respone!=null?respone.desc:"NULL?"));
                    Log.i("detail_booking","Show Detail by offline id="+BookingId);
                    BookingData.i = BookingList.getI().data_list.get(Integer.parseInt(BookingId)-1);
                    ViewpagerDefault viewpagerDefault = new ViewpagerDefault(getSupportFragmentManager());
                    viewpagerDefault.Addfragment(new BookingDetails_Information(),"Information");
                    viewpagerDefault.Addfragment(new BookingDetails_Service(),"Service");
                    viewpagerDefault.Addfragment(new BookingDetails_Commodity(),"Commodity");
                    viewPager.setAdapter(viewpagerDefault);
                    tabLayout.setupWithViewPager(viewPager);
                }
                else {
                    layout_kosong.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<DetailData> call, Throwable t) {
                Log.e("detail_booking", "on Failure called!"+ t);
            }
        });
    }

    //Status booking dari setiap list nya disini setting nya
    private static void KondisiStatus (String statused, TextView textView, Activity activity){
        if (statused.equals("APPROVED")){
            textView.setTextColor(activity.getResources().getColor(R.color.colorGreen));
        } else if (statused.equals("CANCELED")){
            textView.setTextColor(activity.getResources().getColor(R.color.colorRed));
        } else if (statused.equals("BOOKING")){
            textView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        } else {
            textView.setTextColor(activity.getResources().getColor(R.color.colorVerified));
        }
    }

    //Ini untuk kondisi button pada saat status yang berbeda
    private static void KondisiButtonBawah(String statused, RelativeLayout layout, RelativeLayout layoutverif,
                                           Button cancel, Button reject, Button approve, final Activity context){
        //if (statused.)
        if (statused.equals("BOOKING")){
            layout.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogCancell(context);
                }
            });
        } else if (statused.equals("VERIFIED")){
            layout.setVisibility(View.VISIBLE);
            reject.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            layoutverif.setVisibility(View.VISIBLE);

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogReject(context);
                }
            });

            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogApprove(context);
                }
            });
        }
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
