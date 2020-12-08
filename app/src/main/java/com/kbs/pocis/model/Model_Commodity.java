package com.kbs.pocis.model;

import com.kbs.pocis.service.createbooking.CallingList;

public class Model_Commodity {
    public CallingList consigne, commodity;
    public String weight, packages;

    public Model_Commodity(CallingList consigne, CallingList commodity, String packages, String weight) {
        this.packages = packages;
        this.commodity = commodity;
        this.weight = weight;
        this.consigne = consigne;
    }
}
