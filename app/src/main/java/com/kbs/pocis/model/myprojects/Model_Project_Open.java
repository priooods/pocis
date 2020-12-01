package com.kbs.pocis.model.myprojects;

public class Model_Project_Open {

    public String booking_no, status, temp_proj, schedule_code, consig_name, start_date, end_date, ppj_nomer, date_issue,
        related_vesel, payment_type, flag_compound, tonage, voyage, bill_payment, number_bot, depart_group, vesel_name;
    public int id;

    //For Project Approved
    public Model_Project_Open(String booking_no, String status, String customer_name, String ppj_nomer, String vesel_name, String tempNo, String schedule_code){
        this.booking_no = booking_no;
        this.status = status;
        this.ppj_nomer = ppj_nomer;
        this.vesel_name = vesel_name;
        this.consig_name = customer_name;
        this.temp_proj = tempNo;
        this.schedule_code = schedule_code;
    }

    //For Project List
    public Model_Project_Open(String booking_no, String status, String temp_proj, String schedule_code, String consig_name, String start_date, String ppj_nomer, String date_issue, String tonage, String bill_payment, String number_bot, String vesel_name) {
        this.booking_no = booking_no;
        this.status = status;
        this.temp_proj = temp_proj;
        this.schedule_code = schedule_code;
        this.consig_name = consig_name;
        this.start_date = start_date;
        this.ppj_nomer = ppj_nomer;
        this.date_issue = date_issue;
        this.tonage = tonage;
        this.bill_payment = bill_payment;
        this.number_bot = number_bot;
        this.vesel_name = vesel_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDate_issue() {
        return date_issue;
    }

    public String getPpj_nomer() {
        return ppj_nomer;
    }

    public String getBooking_no() {
        return booking_no;
    }

    public String getStatus() {
        return status;
    }

    public String getTemp_proj() {
        return temp_proj;
    }

    public String getSchedule_code() {
        return schedule_code;
    }

    public String getConsig_name() {
        return consig_name;
    }

    public String getRelated_vesel() {
        return related_vesel;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getFlag_compound() {
        return flag_compound;
    }

    public String getTonage() {
        return tonage;
    }

    public String getVoyage() {
        return voyage;
    }

    public String getBill_payment() {
        return bill_payment;
    }

    public String getNumber_bot() {
        return number_bot;
    }

    public String getDepart_group() {
        return depart_group;
    }

    public String getVesel_name() {
        return vesel_name;
    }

    public int getId() {
        return id;
    }
}

