package com.kbs.pocis.model;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.service.Calling;

import java.util.List;

public class Model_Project extends Calling {

    public static Model_Project mp;
    public static int Code;
    public static int Check;
    public static int CheckMenuProject;

    //For Menu My Project & Invoice/Proforma
    public static List<Model_Project> Service;//
    public static List<Model_Project> Commodity;
    public static List<Model_Project> Information;
    public static List<Model_Project> VesselReport;
    public static List<List<Model_Project>> Piloting;
    public static List<Model_Project> Documents;
    public static List<Model_Project> InformationAndDocument;

    //for Vessel Schedule Details
    public static List<Model_Project> Header;

    //for Unloading Details
    public static List<Model_Project> ActualVesselInProgress;
    public static List<Model_Project> ItemSummary;
    public static List<Model_Project> HeaderAndCCTV;
    public static List<Model_Project> HatchDetails; //HatchTotal
    public static List<Model_Project> HatchTotal;
    public static List<Model_Project> ContactAgent;
    public static List<Model_Project> ContactPbm;
    public static List<Model_Project> ActualTruckMonitoring;

    //for complain type
    public static List<Model_Project> attachments;
    public static List<Model_Project> details;

    public static boolean isExist(){
        return Model_Project.mp != null;
    }

    public String last_port_name;
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
    public String t_vessel_schedule_id;
    public String no_booking;
    public String ppj_no;
    public String status_project;
    public String t_booking_id;

    public String act_arrival;
    public String act_anchorage;
    public String act_berthing;
    public String act_start_work;
    public String act_end_work;
    public String act_departure;


    public String pilotage_job_type;
    public String pilot_on_board;
    public String pilot_off_board;
    public String pilot_name;
    public String start_towing;
    public String tugboat_pilot;
    public String stop_towing;
    public String tugboat_towage;
    public String file_name;
    public String file_link;


    public String schedule_code;
    public String exchange_rate;
    public String bi_date;
    public String bill_paymemt_number;
    public String va_number;
    public String date_project_issued;

    public String t_project_report_header_id;
    public String status_bapj;
    public String department_group_name;
    public String date_issued;
    public int id;

    public String plan_status;
    public String dest_port_name;
    public String jetty_name;
    public String est_anchorage;
    public String est_arrival;
    public String est_end_work;
    public String est_berthing;
    public String est_start_work;
    public String est_departure;

    public String link_cctv;
    public String description;
    public String tonnage_actual;
    public String shipping_line_type;
    public String project_report_no;

    public String vessel_registered_no;
    public String vessel_year;
    public String vessel_mmsi_code;
    public String vessel_type;
    public String jetty_code;
    public String jetty_code_inaport;

    public String lowes_water_spring;
    public String vessel_dwt;
    public String vessel_gt;

    public String vessel_call_sign;
    public String jetty_length;
    public String jetty_dwt;

    public String vessel_length_overall;
    public String vessel_draft;
    public String agent_name;
    public String rute;
    public String discharge_loading;
    public String next_port_name;
    public String origin_port_name;

    public String commodity_name;
    public String cargo_type;
    public String hatch_no;
    public String hatch_side;
    public String hatch_position;
    public String ritase;
    public String actual_progress;
    public String contact;
    public String contact_hp;
    public String equipment_name;
    public String status_equipment;
    public String contact_email;

    public String consignee_name;
    public String bl;
    public String unit_qty;
    public String actual;
    public String prosentase;

    public String nopol;
    public String created;
    public String before_pos;
    public String pos;

    public String complain_title;
    public String complain_desc;

    public String reason_name;
    public String user_name;
    public String path;
    public String name;
    public String filename;
    public String status;

    public String customer_type_code;
    public String contract_no;
    public String booking_time;
    public String booking_date;
    public String estimate_arrival;
    public String estimate_departure;
    public String port_of_loading;
    public String flag_contract;
    public String flag_related_vessel;
    public String port_of_cigading;
    public String link;
    public String document_name;
    public String picture, title, content;
    public String commodity_type;
    public String tonage;
    @SerializedName("package")
    public String packages;


    //when get new Comment in Menu My Complaint. do not change
    public Model_Project(String created, String description, String name){
        this.name = name;
        this.created = created;
        this.description =description;
    }

    public Model_Project(String filename){
        this.dokumen_faktur_pajak = filename;
    }

}

