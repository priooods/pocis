package com.kbs.pocis.model.createboking;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Model_ShowTemplate {
    public int id;
    public String code;
    public String display_desc_header;
    public String flag_related_vessel;
    public String t_contract_id;
    public String flag_contract;
    public String flag_vessel_new;
    public String created;
    public String created_by;
    public String modified;
    public String modified_by;
    public String status;
    public String image_file;
    public boolean checked;
    public ArrayList<Model_SelectTemplate> list;

    public Model_ShowTemplate(int id, String code, String desc, String img) {
        this.id = id;
        this.code = code;
        display_desc_header = desc;
        image_file = img;
        list = new ArrayList<>();
        checked = true;
    }
}
