package com.kbs.pocis.model.myprojects;

public class Model_Project_Service {

    String name, tarif, param1, param2, amountDP, amount;

    public Model_Project_Service(String name, String tarif, String param1, String param2, String amountDP, String amount) {
        this.name = name;
        this.tarif = tarif;
        this.param1 = param1;
        this.param2 = param2;
        this.amountDP = amountDP;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getAmountDP() {
        return amountDP;
    }

    public void setAmountDP(String amountDP) {
        this.amountDP = amountDP;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}


