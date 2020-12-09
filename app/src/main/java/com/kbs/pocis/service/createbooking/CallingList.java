package com.kbs.pocis.service.createbooking;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.createboking.Model_AddForm;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;

public class CallingList {
    public CallingList(int id, String name, String desc, String code) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.code = code;
    }

    @SerializedName("id")
    public int id;
    @SerializedName(value = "name",alternate = "m_commodity_type_id")
    public String name;
    @SerializedName(value = "desc")
    public String desc;
    @SerializedName("code")
    public String code;
//    @SerializedName("m_commodity_type_id")
//    public String commodity_type_id;
    //@SerializedName("m_commodity_type_name")
    //public String commodity_type_name;

}
