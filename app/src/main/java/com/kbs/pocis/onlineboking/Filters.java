package com.kbs.pocis.onlineboking;

import com.kbs.pocis.service.onlinebooking.CallingData;

public class Filters {
    public String vessel,nomor;
    public boolean checkFilter(CallingData.Booking data){
        return true;
    }
    public Filters(String nomor, String vessel){
        this.nomor = nomor;
        this.vessel = vessel;
    }
}
