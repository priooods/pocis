package com.kbs.pocis.model;

import com.kbs.pocis.service.createbooking.CallingList;

import java.util.HashMap;
import java.util.Map;

public class Model_Commodity {
    public CallingList consigne, commodity;
    public String weight, packages;

    public Model_Commodity(CallingList consigne, CallingList commodity, String packages, String weight) {
        this.packages = packages;
        this.commodity = commodity;
        this.weight = weight;
        this.consigne = consigne;
    }
    public void getMap(Map<String,String> map, int i) {
        map.put("CommodityBooking[" + i + "][commodity_type_id]", commodity.name);
        map.put("CommodityBooking[" + i + "][commodity_id]", String.valueOf(commodity.id));
        map.put("CommodityBooking[" + i + "][package]", packages);
        map.put("CommodityBooking[" + i + "][tonage]", weight);
        map.put("CommodityBooking[" + i + "][m_customer_id]", String.valueOf(consigne.id));
    }
}
