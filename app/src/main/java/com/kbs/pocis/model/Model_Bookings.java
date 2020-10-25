package com.kbs.pocis.model;

public class Model_Bookings {

    String contractNo, nomerBook, statusBook,
            vesselName, customerType, customerName, flagVessel, flagContract;

    int bookingDate, bookingTime;

    public Model_Bookings() {
    }

    public Model_Bookings(String contractNo, String nomerBook, String statusBook, String vesselName, String customerType,
                          String customerName, String flagVessel, String flagContract, int bookingDate, int bookingTime) {
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getNomerBook() {
        return nomerBook;
    }

    public void setNomerBook(String nomerBook) {
        this.nomerBook = nomerBook;
    }

    public String getStatusBook() {
        return statusBook;
    }

    public void setStatusBook(String statusBook) {
        this.statusBook = statusBook;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFlagVessel() {
        return flagVessel;
    }

    public void setFlagVessel(String flagVessel) {
        this.flagVessel = flagVessel;
    }

    public String getFlagContract() {
        return flagContract;
    }

    public void setFlagContract(String flagContract) {
        this.flagContract = flagContract;
    }

    public int getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(int bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(int bookingTime) {
        this.bookingTime = bookingTime;
    }
}
