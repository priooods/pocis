package com.kbs.pocis.model.onlineboking;

public class Model_TariffAprove {

    public String bookingId, nomer_boking, contractNo, customerName, cutomerCode, flag_contract,
            bookingStatus, customerType_code, flagRelated_vessel, vessel_Name, est_arival, est_berthing ;

    public Model_TariffAprove(String bookingId, String nomer_boking, String contractNo, String customerName, String cutomerCode, String flag_contract, String bookingStatus,
                              String customerType_code, String flagRelated_vessel, String vessel_Name, String est_arival, String est_berthing) {
        this.bookingId = bookingId;
        this.nomer_boking = nomer_boking;
        this.contractNo = contractNo;
        this.customerName = customerName;
        this.cutomerCode = cutomerCode;
        this.flag_contract = flag_contract;
        this.bookingStatus = bookingStatus;
        this.customerType_code = customerType_code;
        this.flagRelated_vessel = flagRelated_vessel;
        this.vessel_Name = vessel_Name;
        this.est_arival = est_arival;
        this.est_berthing = est_berthing;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getNomer_boking() {
        return nomer_boking;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCutomerCode() {
        return cutomerCode;
    }

    public String getFlag_contract() {
        return flag_contract;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getCustomerType_code() {
        return customerType_code;
    }

    public String getFlagRelated_vessel() {
        return flagRelated_vessel;
    }

    public String getVessel_Name() {
        return vessel_Name;
    }

    public String getEst_arival() {
        return est_arival;
    }

    public String getEst_berthing() {
        return est_berthing;
    }
}
