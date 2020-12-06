package com.kbs.pocis.model.createboking;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.service.createbooking.CreateTemp;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Model_ShowTemplate {

    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    @SerializedName("data")
    public List<ShowTemp> data;

    public static class ShowTemp {
//        @SerializedName("id")
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

    }

    public static boolean TreatResponse(Context context, String tag, @Nullable Model_ShowTemplate data) {
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


    String id;
    String img,name;
    public ArrayList<Model_SelectTemplate> list;
    boolean Check;

    public boolean OneChecked(){
        for(Model_SelectTemplate temp:list){
            if (temp.checked){
                return true;
            }
        }
        return false;
    }

    public Model_ShowTemplate(String id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
        list = null;
    }
    public Model_ShowTemplate(String id, String name, String img, ArrayList<Model_SelectTemplate> temp ) {
        this.id = id;
        this.name = name;
        this.img = img;
        Check = true;
        list = temp;
    }

    public ArrayList<Model_SelectTemplate> getList() {
        return list;
    }

    public void setList(ArrayList<Model_SelectTemplate> list) {
        this.list = list;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }
    public boolean getCheck(){
        return Check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
