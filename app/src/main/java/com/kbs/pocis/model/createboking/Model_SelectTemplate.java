package com.kbs.pocis.model.createboking;

import com.google.gson.annotations.SerializedName;

public class Model_SelectTemplate {

//        "id": 1676,
//        "m_booking_template_header_id": "231",
//        "code": "F003-005",
//        "display_desc_detail": "FEE SUPPLY BBM VIA DARAT",
//        "m_service_code_id": "698",
//        "m_service_code_element_id": null,
//        "created": "2018-05-02 13:23:55",
//        "created_by": null,
//        "modified": "2018-05-02 13:23:55",
//        "modified_by": null
    public int id;
    @SerializedName("m_booking_template_header_id")
    public String header_id;
    public String code;
    @SerializedName("display_desc_detail")
    public String desc;
    String m_service_code_id,m_service_code_element_id,created,created_by,modified,modified_by;
    public boolean checked;
}
