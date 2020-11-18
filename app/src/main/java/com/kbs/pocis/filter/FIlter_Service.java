package com.kbs.pocis.filter;

import com.kbs.pocis.service.BookingData;

public class FIlter_Service {

    public static FIlter_Service filter;
    public static boolean isExist(){
        if (FIlter_Service.filter == null){
            return false;
        }
        return true;
    }

    //Search di Online Booking
    public String vesel_name, booking_nomer;
    public void SetFilterOnline (String vesel, String nomer){
        this.booking_nomer = nomer;
        vesel_name = vesel;
    }
}
