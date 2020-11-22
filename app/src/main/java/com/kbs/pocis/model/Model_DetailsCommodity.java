package com.kbs.pocis.model;

public class Model_DetailsCommodity {

    String comodityName, comodityTyoe;
    String packageNo,tonage;

    public Model_DetailsCommodity() {
    }

    public Model_DetailsCommodity(String comodityName, String comodityTyoe, String packageNo, String tonage) {
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

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getTonage() {
        return tonage;
    }

    public void setTonage(String tonage) {
        this.tonage = tonage;
    }
}
