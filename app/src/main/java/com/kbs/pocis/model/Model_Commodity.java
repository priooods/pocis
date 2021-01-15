package com.kbs.pocis.model;

import com.kbs.pocis.service.createbooking.CallingList;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Model_Commodity {
    public CallingList consigne, commodity;
    public String weight, packages;

    public Model_Commodity(CallingList consigne, CallingList commodity, String packages, String weight) {
        this.packages = packages;
        this.commodity = commodity;
        this.weight = weight;
        this.consigne = consigne;
    }

    public RequestBody input_form(String req) {
        return RequestBody.create(req, MediaType.parse("multipart/form-data"));
    }

    public void getMap(HashMap<String, RequestBody> map, int i) {
        map.put("CommodityBooking[" + i + "][commodity_type_id]", input_form(commodity.name));
        map.put("CommodityBooking[" + i + "][commodity_id]", input_form(String.valueOf(commodity.id)));
        map.put("CommodityBooking[" + i + "][package]", input_form(packages));
        map.put("CommodityBooking[" + i + "][tonage]", input_form(weight));
        map.put("CommodityBooking[" + i + "][m_customer_id]", input_form(String.valueOf(consigne.id)));
    }
}
