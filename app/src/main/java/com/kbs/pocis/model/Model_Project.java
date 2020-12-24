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

    public String t_project_report_header_id;
    public String status_bapj_code;
    public String status_bapj;
    public String department_group_name;
    public String total_net_amount;
    public String date_issued;
    public int id;


}

