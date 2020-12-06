package com.kbs.pocis.service.createbooking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;

import java.lang.reflect.Field;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class CreateTemp {

    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    //@SerializedName("data")
    public ShowTemp[] data;

    public static class ShowTemp{
        public int id;
        public String code;
        public String t_contract_id;
        public String flag_contract;
        public String flag_vessel_new;
        public String display_desc_header;
        public String flag_related_vessel;
        public String image_file;
        @SerializedName("created")
        public String showTemp_created;
        @SerializedName("created_by")
        public String showTemp_created_by;
        @SerializedName("modified")
        public String showTemp_modified;
        @SerializedName("modified_by")
        public String showTemp_modified_by;
        public String status;


//        public Model_ShowTemplate getShowTemplate(){
//            return new Model_ShowTemplate(
//                    code,
//                    display_desc_header,
//                    image_file
//            );
//        }

    }


    //region Aksi untuk setiap perlakuan response
    public static boolean TreatResponse(Context context, String tag, @Nullable CreateTemp data) {
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
