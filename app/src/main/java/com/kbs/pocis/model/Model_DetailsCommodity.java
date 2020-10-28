package com.kbs.pocis.model;

public class Model_DetailsCommodity {

    String comodityName, comodityTyoe;
    int packageNo;
    double tonage;

    public Model_DetailsCommodity() {
    }

    public Model_DetailsCommodity(String comodityName, String comodityTyoe, int packageNo, double tonage) {
        this.comodityName = comodityName;
        this.comodityTyoe = comodityTyoe;
        this.packageNo = packageNo;
        this.tonage = tonage;
    }

    public String getComodityName() {
        return comodityName;
    }

    public void setComodityName(String comodityName) {
        this.comodityName = comodityName;
    }

    public String getComodityTyoe() {
        return comodityTyoe;
    }

    public void setComodityTyoe(String comodityTyoe) {
        this.comodityTyoe = comodityTyoe;
    }

    public int getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(int packageNo) {
        this.packageNo = packageNo;
    }

    public double getTonage() {
        return tonage;
    }

    public void setTonage(double tonage) {
        this.tonage = tonage;
    }
}
