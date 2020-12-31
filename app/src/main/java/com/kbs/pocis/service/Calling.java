package com.kbs.pocis.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import es.dmoral.toasty.Toasty;

public class Calling {
    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    //region Aksi untuk setiap perlakuan response
    public static boolean TreatResponse(Context context, String tag, @Nullable Calling data) {
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
        } else if(error.equals("UNDER DEVELOPMENT")){
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
