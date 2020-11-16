package com.kbs.pocis.model.createboking;

public class Model_Summary_ServiceInfoList {

    String number , desc;

    public Model_Summary_ServiceInfoList(String number, String desc) {
        this.number = number;
        this.desc = desc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
