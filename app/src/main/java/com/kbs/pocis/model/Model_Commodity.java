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
    public Map<String,String> getMap() {
        HashMap<String, String> map = new HashMap();
        map.put("commodity_type_id", commodity.commodity_type_id);
        map.put("package", packages);
        map.put("tonage", weight);
        map.put("m_customer_id", String.valueOf(consigne.id));
        return map;
    }
}
