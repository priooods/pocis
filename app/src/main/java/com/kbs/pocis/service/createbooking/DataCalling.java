package com.kbs.pocis.service.createbooking;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.createboking.Model_AddForm;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;

public class DataCalling {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("desc")
    public String desc;
    @SerializedName("code")
    public String code;



//            API RESPONSE SHOW TEMPLATE
//            "id": 133,
//            "code": "S009",
//            "display_desc_header": "WATER SUPPLY VIA HYDRANT",
//            "flag_related_vessel": "Y",
//            "created": "2017-01-26 11:21:02",
//            "created_by": "6",
//            "modified": "2017-10-18 10:11:12",
//            "modified_by": "6",
//            "flag_vessel_new": "N",
//            "flag_contract": "N",
//            "t_contract_id": null,
//            "status": "E",
//            "image_file": "hydrant2.jpg"

//    public Model_AddForm getCostumerType() {
//        return new Model_AddForm(id,code,name, desc);
//    }
}
