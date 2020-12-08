package com.kbs.pocis.service.createbooking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.service.BookingDetailData;

import es.dmoral.toasty.Toasty;

public class CallingSaveBok {
    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    public BookingDetailData data;

    public static boolean TreatResponse(Context context, String tag, @Nullable CallingShowTemp data) {
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
}
