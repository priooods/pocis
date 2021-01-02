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




//    public String berth_status;
//    public String est_anchorage;
//    public String est_arrival;
//    public String est_end_work;
//    public String est_berthing;
//    public String est_start_work;
//    public String est_departure;
//    public String act_anchorage;
//    public String act_arrival;
//    public String act_berthing;
//    public String act_start_work;
//
//    public String link_cctv;
//    public String is_in_kbs;
//    public String shipping_line_type;
//    public String act_departure;
//    public String shipping_line_type;

//    "m_customer_booking_id": ",55,41,203,",
//            "m_customer_consignee_id": ",41,",
//            "t_vessel_schedule_id": "7778",
//            "vessel_name": "MV. SDTR BELLONA",
//            "schedule_code": "V7RV401001",
//            "voyage_no": "6934D",
//            "plan_status": "Not Planned",
//            "berth_status": "Departure",
//            "jetty_name": "DERMAGA 1.3",
//            "est_anchorage": "2019-05-25 00:00:00",
//            "est_arrival": "2019-05-25 00:00:00",
//            "est_berthing": "2019-05-25 00:00:00",
//            "est_start_work": "2019-05-25 00:00:00",
//            "est_end_work": "2019-05-29 00:00:00",
//            "est_departure": "2019-05-31 00:20:06",
//            "act_anchorage": "0000-00-00 00:00:00",
//            "act_arrival": "2019-05-26 04:00:00",
//            "act_berthing": "2019-05-26 05:46:29",
//            "act_start_work": "2019-05-26 08:31:30",
//            "act_end_work": "2019-05-30 03:15:06",
//            "act_departure": "2019-05-30 04:27:03",
//            "link_cctv": "http://ptkbs:cigading@cigading.ptkbs.co.id:9113",
//            "is_in_kbs": "1",
//            "shipping_line_type": "Ocean Going"


    public String contact_name, contact_email,contact_nomer,contact_jabatan,contact_status, item_name,destination, ppj,
            cargo_type,hacth_no,hacth_side,hacth_position,actual_progress,status_equipment,equipment_name;
    public String actual_id, date,before_pos,pos,voyage_name, pcs, ritase, top_view,side_view, tonnage, actual_name,bl,actual_in_qty,actual_in_progres,actual_in_percent;

    public String status, voyage, jetty, booking_no, project_no,
            name,code, ship_line, nomer;
    public int id;


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
