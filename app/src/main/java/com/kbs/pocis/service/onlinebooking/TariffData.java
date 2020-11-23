package com.kbs.pocis.service.onlinebooking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.onlineboking.Model_TariffAprove;

import es.dmoral.toasty.Toasty;

public class TariffData {
    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    //@SerializedName("data")
    public Tariff data;

    public static class Tariff {
        @SerializedName("token")
        public String token;
        @SerializedName("current_page")
        public int current_page;
        @SerializedName("data")
        public Tariff_App[] tar;
        @SerializedName("from")
        public int from_page;
        @SerializedName("to")
        public int to_page;
        public int last_page, per_page, total;
    }

    public static class Tariff_App {
        @SerializedName("id")
        public String bookingId;
        @SerializedName("no_booking")
        public String nomer_boking;
        public String customer_type_code;
        public String flag_related_vessel;
        public String flag_contract;
        public String customer_code;
        public String customer_name;
        public String contract_no;
        public String book_status;
        public String vessel_name;
        public String est_arrival;
        public String est_berthing;

        ///Fungsi ini untuk membuat Model_Bookings baru sesuai dengan atribut data class booking ini
        public Model_TariffAprove getTariff() {
            return new Model_TariffAprove(
                    bookingId,
                    nomer_boking,
                    contract_no,
                    customer_name,
                    customer_code,
                    flag_contract,
                    book_status,
                    customer_type_code,
                    flag_related_vessel,
                    vessel_name,
                    est_arrival,
                    est_berthing
            );
        }
    }

    //region Aksi untuk setiap perlakuan response
    public static boolean TreatResponse(Context context, String tag, @Nullable TariffData data) {
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
