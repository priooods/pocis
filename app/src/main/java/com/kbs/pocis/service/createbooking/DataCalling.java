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
//    public getData data;



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
    public class getData{
        @SerializedName("id")
        public String show_id;
        public String t_contract_id;
        public String flag_contract;
        public String flag_vessel_new;
        public String display_desc_header;
        @SerializedName("code")
        public String showTemp_code;
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
        public String created;
        public String created_by;
        public String modified;
        public String modified_by;
}
//        @SerializedName("id")
//        public String show_id;
//        public String t_contract_id;
//        public String flag_contract;
//        public String flag_vessel_new;
//        public String display_desc_header;
//        @SerializedName("code")
//        public String showTemp_code;
//        public String flag_related_vessel;
//        public String image_file;
//        @SerializedName("created")
//        public String showTemp_created;
//        @SerializedName("created_by")
//        public String showTemp_created_by;
//        @SerializedName("modified")
//        public String showTemp_modified;
//        @SerializedName("modified_by")
//        public String showTemp_modified_by;
//        public String created;
//        public String created_by;
//        public String modified;
//        public String modified_by;

    //region ShowTemp

//    //endregion

//    public Model_AddForm getCostumerType() {
//        return new Model_AddForm(id,code,name, desc);
//    }
}
