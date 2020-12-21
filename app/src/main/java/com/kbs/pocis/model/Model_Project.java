package com.kbs.pocis.model;

import com.kbs.pocis.service.Calling;

import java.util.ArrayList;
import java.util.List;

public class Model_Project {

    public static Model_Project mp;
    public static int Code;
    public static boolean isExist(){
        if (Model_Project.mp == null){
            return false;
        }
        return true;
    }

    public String booking_no, status, temp_proj_no, schedule_code, consig_name, start_date, end_date, ppj_nomer, date_issue,bi_date,location,
        related_vesel, payment_type, flag_compound, tonage, voyage, bill_payment, va_number,depart_group, exhange_rate,vesel_name,bpaj_no;
    public int id;
    public String invoice_no, project_no, cust_name, invoice_cancel, invoice_type, due_date, invoice_payment,booking_status;

    //Ini untuk Handle Ketika Server Perusahaan DOWN
    public Model_Project(String booking_no, String status, String temp_proj_no, String schedule_code, String consig_name, String start_date, String end_date, String ppj_nomer,
                         String date_issue, String related_vesel, String payment_type, String flag_compound, String tonage, String voyage, String bill_payment, String va_number, String depart_group,
                         String exhange_rate, String vesel_name, String bpaj_no, int id, String invoice_no, String project_no,
                         String cust_name, String invoice_cancel, String invoice_type, String due_date, String invoice_payment,
                         String booking_status, String bi_date, String location) {
        this.booking_no = booking_no;
        this.status = status;
        this.temp_proj_no = temp_proj_no;
        this.schedule_code = schedule_code;
        this.consig_name = consig_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.ppj_nomer = ppj_nomer;
        this.date_issue = date_issue;
        this.related_vesel = related_vesel;
        this.payment_type = payment_type;
        this.flag_compound = flag_compound;
        this.tonage = tonage;
        this.voyage = voyage;
        this.bill_payment = bill_payment;
        this.va_number = va_number;
        this.depart_group = depart_group;
        this.exhange_rate = exhange_rate;
        this.vesel_name = vesel_name;
        this.bpaj_no = bpaj_no;
        this.id = id;
        this.invoice_no = invoice_no;
        this.project_no = project_no;
        this.cust_name = cust_name;
        this.invoice_cancel = invoice_cancel;
        this.invoice_type = invoice_type;
        this.due_date = due_date;
        this.invoice_payment = invoice_payment;
        this.booking_status = booking_status;
        this.bi_date = bi_date;
        this.location = location;
    }
}

