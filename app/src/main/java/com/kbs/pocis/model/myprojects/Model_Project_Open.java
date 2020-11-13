package com.kbs.pocis.model.myprojects;

public class Model_Project_Open {

    String date, id, no_booking, code_schedule, tonage, start_date, name_consigne, name_vesel;

    public Model_Project_Open() {
    }

    public Model_Project_Open(String date, String id, String no_booking,
                              String code_schedule, String tonage, String start_date,
                              String name_consigne, String name_vesel) {
        this.date = date;
        this.id = id;
        this.no_booking = no_booking;
        this.code_schedule = code_schedule;
        this.tonage = tonage;
        this.start_date = start_date;
        this.name_consigne = name_consigne;
        this.name_vesel = name_vesel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo_booking() {
        return no_booking;
    }

    public void setNo_booking(String no_booking) {
        this.no_booking = no_booking;
    }

    public String getCode_schedule() {
        return code_schedule;
    }

    public void setCode_schedule(String code_schedule) {
        this.code_schedule = code_schedule;
    }

    public String getTonage() {
        return tonage;
    }

    public void setTonage(String tonage) {
        this.tonage = tonage;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getName_consigne() {
        return name_consigne;
    }

    public void setName_consigne(String name_consigne) {
        this.name_consigne = name_consigne;
    }

    public String getName_vesel() {
        return name_vesel;
    }

    public void setName_vesel(String name_vesel) {
        this.name_vesel = name_vesel;
    }
}
