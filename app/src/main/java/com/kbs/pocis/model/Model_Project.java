package com.kbs.pocis.model;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.service.Calling;

import java.util.ArrayList;
import java.util.List;

public class Model_Project {

    public static Model_Project mp;
    public static int Code;
    public static boolean isExist(){
        return Model_Project.mp != null;
    }

//    "Service": [
//    {
//        "t_billing_invoice_id": "22058",
//            "service_name": "F023 - JASA PANDU NON CGD",
//            "tariff": "USD 1595.560",
//            "parameter_1": "1.000 LS",
//            "parameter_2": "- ",
//            "amount_in_idr": "24994607"
//    },
//    {
//        "t_billing_invoice_id": "22058",
//            "service_name": "F024 - JASA TUNDA NON CGD",
//            "tariff": "USD 8813.740",
//            "parameter_1": "1.000 LS",
//            "parameter_2": "- ",
//            "amount_in_idr": "138068118"
//    }


    public String m_customer_id;
    public String t_billing_invoice_id;
    public String invoice_no;
    public String booking_no;
    public String project_no;
    public String customer_name;
    public String vessel_name;
    public String status_payment;
    public String status_cancel;
    public String dokumen_tanda_tangan_invoice;
    public String dokumen_tanda_terima;
    public String dokumen_faktur_pajak;
    public String dokumen_kwitans;
    public String voyage_no;
    public String payment_type;
    public String invoice_type;
    public String bill_payment_reff_no;
    public String va_reff_no;
    public String free_ppn;
    public String ppn_in_idr;
    public String pph_in_idr;
    public String amount_paid_by_dp;
    public String amount_paid_by_invoice;
    public String total_net_amount_in_idr;
    public String flag_compound;
    public String due_date;
    public String service_name;
    public String tariff;
    public String parameter_1;
    public String parameter_2;
    public String amount_in_idr;

    public String t_project_header_id;
    public String temp_project_no;
    public String tipe_pembayaran;
    public String status_booking;
    public String tonnage;
    public String related_vessel;
    public String start_date;
    public String end_date;
    public String department_group;
    public String total;
    public String total_dp;
    public String document_ppj;
    public String document_proforma_invoice;

    public String no_booking;
    public String ppj_no;
    public String status_project;
    public String t_booking_id;


    public String status_booking_code;
    public String schedule_code;
    public String status_project_code;
    public String exchange_rate;
    public String bi_date;
    public String bill_paymemt_number;
    public String va_number;
    public String date_project_issued;


    ///ini untuk defaul. nnti diupdate
    public String  status, temp_proj_no, consig_name,  ppj_nomer, date_issue, location,
        related_vesel, tonage, voyage, bill_payment, depart_group, exhange_rate ,bpaj_no;
    public int id;
    public String invoice_cancel, invoice_payment,booking_status;

//    Ini untuk Handle Ketika Server Perusahaan DOWN
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
        this.vessel_name = vesel_name;
        this.bpaj_no = bpaj_no;
        this.id = id;
        this.invoice_no = invoice_no;
        this.project_no = project_no;
        this.customer_name = cust_name;
        this.invoice_cancel = invoice_cancel;
        this.invoice_type = invoice_type;
        this.due_date = due_date;
        this.invoice_payment = invoice_payment;
        this.booking_status = booking_status;
        this.bi_date = bi_date;
        this.location = location;
    }


//    "m_customer_id": "55",
//            "t_billing_invoice_id": "22058",
//            "invoice_no": "BUA01-000295",
//            "booking_no": "B0001-2020-00002",
//            "project_no": "PPJ-2020/01497",
//            "customer_name": "BUANA INDAH GEMACA PT.",
//            "vessel_name": "MV. BELLINA COLOSSUS",
//            "status_payment": "Unpaid",
//            "status_cancel": "YES",
//            "dokumen_tanda_tangan_invoice": null,
//            "dokumen_tanda_terima": null,
//            "dokumen_faktur_pajak": null,
//            "dokumen_kwitans": null,
//            "voyage_no": "1076L",
//            "payment_type": "Bill Payment",
//            "invoice_type": "Total Payment",
//            "bill_payment_reff_no": "0215833907235",
//            "va_reff_no": "8923715833907235",
//            "free_ppn": "0",
//            "ppn_in_idr": "14823884",
//            "pph_in_idr": "0",
//            "amount_paid_by_dp": "0.00",
//            "amount_paid_by_invoice": "0",
//            "total_net_amount_in_idr": "163062725",
//            "flag_compound": "No",
//            "due_date": "2020-03-19 00:00:00"


}

