package com.kbs.pocis.model;

public class Model_DetailsService {

    String include, price;

    public Model_DetailsService() {
    }

    public Model_DetailsService(String include, String price) {
        this.include = include;
        this.price = price;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
