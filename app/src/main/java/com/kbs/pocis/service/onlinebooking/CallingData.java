package com.kbs.pocis.service.onlinebooking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.onlineboking.Model_Bookings;
import com.kbs.pocis.model.onlineboking.Model_TariffAprove;

import es.dmoral.toasty.Toasty;

public class CallingData {
    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    //@SerializedName("data")
    public Input data;

    public static class Input {
        @SerializedName("token")
        public String token;
        @SerializedName("current_page")
        public int current_page;
        @SerializedName("data")
        public Booking[] book;
        @SerializedName("from")
        public int from_page;
        @SerializedName("to")
        public int to_page;
        public int last_page, per_page, total;
    }

    public static class Booking {
        @SerializedName("id")
        public String booking_id;
        @SerializedName("no_booking")
        public String no_booking;
        public String no_contract;
        public String vessel_name;
        public String flag_related_vessel;
        public String flag_contract;
        public String customer_name;
        public String customer_code;
        @SerializedName(value="customer_type_name", alternate = "customer_type_code")
        public String customer_type_name;
        public String booking_date;
        @SerializedName(value="status_booking", alternate = "book_status")
        public String state_booking;
        @SerializedName(value="formatted_booking_date", alternate = "est_arrival")
        public String format_date1;
        @SerializedName(value="formatted_booking_time", alternate = "est_berthing")
        public String format_date2;
        public String contract_no;

        ///Fungsi ini untuk membuat Model_Bookings baru sesuai dengan atribut data class booking ini
        public Model_Bookings getModel() {
            return new Model_Bookings(
                    booking_id,
                    no_contract,
                    no_booking,
                    state_booking,
                    vessel_name,
                    customer_type_name,
                    customer_name,
                    flag_related_vessel,
                    flag_contract,
                    booking_date,
                    format_date1 + " " + format_date2
            );
        }
        ///Fungsi ini untuk membuat Model_Tariff baru sesuai dengan atribut data class booking ini
        public Model_TariffAprove getTariff() {
            return new Model_TariffAprove(
                    booking_id,
                    no_booking,
                    contract_no,
                    customer_name,
                    customer_code,
                    flag_contract,
                    state_booking,
                    customer_type_name,
                    flag_related_vessel,
                    vessel_name,
                    format_date1,
                    format_date2
            );
        }
    }

    //region Aksi untuk setiap perlakuan response
    public static boolean TreatResponse(Context context, String tag, @Nullable CallingData data) {
        if (data != null)
            return data.TreatResponse(context, tag);
        else
            Log.i(tag, "onResponseError: " + "POST " + tag + " gagal");
        return false;
    }

    public boolean TreatResponse(Context context, String tag) {
        Log.i(tag, "Error          -->  " + error);
        Log.i(tag, "Description       -->  " + desc);
        if (error.equals("0")) {
            Log.i(tag, "Success : " + tag + " : " + desc);
            return true;
        } else {
            Toasty.error(context, desc, Toast.LENGTH_SHORT, true).show();
            Log.e(tag, "Failed : \n Error " + error + " : " + desc);
            return false;
        }
    }
    //endregion
}
