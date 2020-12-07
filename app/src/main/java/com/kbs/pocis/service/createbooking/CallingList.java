package com.kbs.pocis.service.createbooking;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.createboking.Model_AddForm;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;

public class CallingList {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("desc")
    public String desc;
    @SerializedName("code")
    public String code;
}
