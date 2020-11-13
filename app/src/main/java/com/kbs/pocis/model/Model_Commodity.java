package com.kbs.pocis.model;

public class Model_Commodity {

    public String packages, commodity, weight, consigne;

    public Model_Commodity() {
    }

    public Model_Commodity(String packages, String commodity, String weight, String consigne) {
        this.packages = packages;
        this.commodity = commodity;
        this.weight = weight;
        this.consigne = consigne;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getConsigne() {
        return consigne;
    }

    public void setConsigne(String consigne) {
        this.consigne = consigne;
    }
}
