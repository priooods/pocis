package com.kbs.pocis.model;

public class Model_Monitoring {
    public static Model_Monitoring mn;
    public static int Code;
    public static boolean isExist(){
        if (Model_Monitoring.mn == null){
            return false;
        }
        return true;
    }

    public String contact_name, contact_email,contact_nomer,contact_jabatan,contact_status, item_name,destination, ppj,
            cargo_type,hacth_no,hacth_side,hacth_position,actual_progress,status_equipment,equipment_name;
    public String actual_id, date,before_pos,pos,voyage_name, pcs, ritase, top_view,side_view, tonnage, actual_name,bl,actual_in_qty,actual_in_progres,actual_in_percent;

    public String status, voyage, est_bethring, est_departure,act_bethring, act_departure, jetty, booking_no, project_no,
            name,code, ship_line,est_arival, nomer;
    public int id;

    public Model_Monitoring(String status, String voyage, String est_bethring, String est_departure,
                            String act_bethring, String act_departure,
                            String jetty, String name, String code, String ship_line, String est_arival, String nomer, int id) {
        this.status = status;
        this.voyage = voyage;
        this.est_bethring = est_bethring;
        this.est_departure = est_departure;
        this.act_bethring = act_bethring;
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
