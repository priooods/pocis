package com.kbs.pocis.model.myprojects;

public class Model_Project_Service {

    String name, location, startDate, endDate, calculation, total, totalDp;

    public Model_Project_Service() {
    }

    public Model_Project_Service(String name, String location, String startDate, String endDate, String calculation, String total, String totalDp) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.calculation = calculation;
        this.total = total;
        this.totalDp = totalDp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalDp() {
        return totalDp;
    }

    public void setTotalDp(String totalDp) {
        this.totalDp = totalDp;
    }
}


