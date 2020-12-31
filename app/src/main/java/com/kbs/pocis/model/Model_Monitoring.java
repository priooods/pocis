package com.kbs.pocis.model;

public class Model_Monitoring {
    public static Model_Monitoring mn;
    public static int Code;
    public static boolean isExist(){
        return Model_Monitoring.mn != null;
    }

    public String m_customer_booking_id;
    public String m_customer_consignee_id;
    public String t_vessel_schedule_id;
    public String vessel_name;
    public String schedule_code;
    public String voyage_no;
    public String plan_status;
    public String berth_status;
    public String jetty_name;
    public String est_anchorage;
    public String est_arrival;
    public String est_end_work;
    public String est_berthing;
    public String est_start_work;
    public String est_departure;
    public String act_anchorage;
    public String act_arrival;
    public String act_berthing;
    public String act_start_work;

    public String link_cctv;
    public String is_in_kbs;
    public String shipping_line_type;
    public String act_departure;
    public String act_end_work;


    public String contact_name, contact_email,contact_nomer,contact_jabatan,contact_status, item_name,destination, ppj,
            cargo_type,hacth_no,hacth_side,hacth_position,actual_progress,status_equipment,equipment_name;
    public String actual_id, date,before_pos,pos,voyage_name, pcs, ritase, top_view,side_view, tonnage, actual_name,bl,actual_in_qty,actual_in_progres,actual_in_percent;

    public String status, voyage, jetty, booking_no, project_no,
            name,code, ship_line,est_arival, nomer;
    public int id;

    public Model_Monitoring(String status, String voyage, String est_bethring, String est_departure,
                            String act_bethring, String act_departure,
                            String jetty, String name, String code, String ship_line, String est_arival, String nomer, int id) {
        this.status = status;
        this.voyage = voyage;
        this.est_berthing = est_bethring;
        this.est_departure = est_departure;
        this.act_berthing = act_bethring;
        this.act_departure = act_departure;
        this.jetty = jetty;
        this.name = name;
        this.code = code;
        this.ship_line = ship_line;
        this.est_arival = est_arival;
        this.nomer = nomer;
        this.id = id;
    }

    //for Detail
    public Model_Monitoring(String contact_name, String contact_email, String contact_nomer, String contact_jabatan, String contact_status,
                            String code, String hacth_no, String date, String pos, String pcs, String ritase, String tonnage) {
        this.contact_name = contact_name;
        this.contact_email = contact_email;
        this.contact_nomer = contact_nomer;
        this.contact_jabatan = contact_jabatan;
        this.contact_status = contact_status;
        this.code = code;
        this.hacth_no = hacth_no;
        this.date = date;
        this.pos = pos;
        this.pcs = pcs;
        this.ritase = ritase;
        this.tonnage = tonnage;
    }
}
