package com.kbs.pocis.model.onlineboking;

import java.util.Date;

public class Model_Bookings {

    String  BookingId, nomerBook, statusBook, contractNo, customerName,
            vesselName, customerType, flagVessel, bookingDate, flagContract, bookingTime;

    public Model_Bookings(String BookingId, String contractNo, String nomerBook, String statusBook, String vesselName, String customerType,
                          String customerName, String flagVessel, String flagContract, String bookingDate, String bookingTime) {
        this.BookingId = BookingId;
        this.contractNo = contractNo;
        this.nomerBook = nomerBook;
        this.statusBook = statusBook;
        this.vesselName = vesselName;
        this.customerType = customerType;
        this.customerName = customerName;
        this.flagVessel = flagVessel;
        this.flagContract = flagContract;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    public String getBookingId() {return BookingId;}
    public String getContractNo() {
        return contractNo;
    }
    public String getNomerBook() {
        return nomerBook;
    }
    public String getStatusBook() {
        return statusBook;
    }
    public String getVesselName() {
        return vesselName;
    }
    public String getCustomerType() {
        return customerType;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getFlagVessel() {
        return flagVessel;
    }
    public String getFlagContract() {
        return flagContract;
    }
    public String getBookingDate() {
        return bookingDate;
    }
    public String getBookingTime() {
        return bookingTime;
    }



}
