package com.kbs.pocis.service;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_DetailsCommodity;
import com.kbs.pocis.model.Model_DetailsFile;
import com.kbs.pocis.model.Model_DetailsService;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BookingDetailData {
    //region Singleton
    public static BookingDetailData i;
    public static boolean isExist(){
        if (BookingData.i == null){
            return false;
        }
        return true;
    }
    // data booking pertama CustomerAddForm
    //region DB
    /*
            v "id": 36538,
            v "no_booking": "B0001-2020-00010",
            v "customer_type_code": "AGENT",
            v "booking_date": "2020-10-21 10:30:45",
            v "flag_related_vessel": "Yes",
            v "flag_contract": "No",
            "flag_compound": null,
            "status": "RM",
            "customerbooking": [],
            "formatted_booking_date": "21 October 2020",
            "formatted_booking_time": "10:30",
            "voyage_no": null,
            "customer_code": "B0001",
            "customer_name": "BUANA INDAH GEMACA PT.",
            "contract_no": "-",
            "book_status": "REJECTED BY MARKETING",
            "project_status": "-",
            "project_no": "-",
            "vessel_name": "MV. NORD PEAK",
            "no_dp": "-",
            "no_reff": "-",
            "no_invoice": "-",
            "est_arrival": "2020-10-22 00:00:00",
            "est_berthing": null,
            "compoundbooking": null,
    */
    //for response Show Template
    //Voyage response
//    "id": "890",
//            "voyage_no": "1",
//            "est_arrival": "2017-09-29 00:00:00",
//            "est_departure": "2017-10-02 00:00:00",
//            "act_departure": "0000-00-00 00:00:00"


    @SerializedName("id")
    public int id;
    public String voyage_no;
    public String act_departure;

    @SerializedName("customer_type_code")
    public String customerType;
    @SerializedName("flag_related_flag")
    public String relatedVesel;

    public String booking_date;
    @SerializedName("flag_related_contract")
    public String contract;
    @SerializedName("formatted_booking_date")
    public String bookingDate;
    @SerializedName("formatted_booking_time")
    public String bookingTime;
    public String id_booking;
    public String no_booking;
    public String flag_compound;
    public String status;
    public String customer_code;
    public String customer_name;
    public String contract_no;
    public String book_status;
    public String project_status;
    public String project_no;
    public String vessel_name;
    public String no_dp;
    public String no_reff;
    public String no_invoice;
    public String est_arrival;
    public String est_berthing;
    public Compound compoundbooking;
    public class Compound {
        public int id;
        public String t_booking_id,description,tariff,currency,unit_code,crated,created_by,modified,modified_by;
        public String unit_code1,unit_code2,quantity,quantity1,quantity2,Unit;
        public Unit unit;
    }
    public class Unit{
        public int id;
        public String code,desc,created,created_by,modified,modified_by;
    }

    //data booking kedua ShowTemplate - SelectTemplate
    @SerializedName("customerbooking")
    public ArrayList<Model_DetailsService> template;
    //data booking ketiga UploadDocument
    @SerializedName("documentbooking")
    public ArrayList<Model_DetailsFile> file;
    //data booking keempat AddComodity
    @SerializedName("commoditybooking")
    public ArrayList<Model_DetailsCommodity> commodity;

    public ArrayList<Model_DetailsCommodity> addinfo;

//
//    //Response dari Template_Header
//    public List<Model_SelectTemplate> temp_detail;
//    public static class Temp_detail{
//        public String id;
//        public String m_booking_template_header_id;
//        public String code;
//        public String m_service_code_id;
//        public String m_service_code_element_id;
//        public String display_desc_detail;
//    }

    //data Schedule
    /*
            "id": 17490,
            "m_vessel_id": "5044",
            "voy_no_in": "16225D",
            "voy_no_out": null,
            "origin_port_id": "141",
            "est_arrival": "2020-10-22 00:00:00",
            "est_departure": "2020-10-22 00:00:00",
            "remark": "",
            "m_jetty_cigading_id": "44",
            "vessel_name": "MV. NORD PEAK",
            "origin_port": "CWNID - Port Of Ciwandan",
            "cigading_port": "Cwd - CIWANDAN PORT"
    */
    @SerializedName("schedule")
    public ScheduleData vessel;
    public static class ScheduleData{
        int id;
        public String m_vessel_id, voy_no_in, voy_no_out,origin_port_id, est_arival, est_departure, remark, m_jetty_cigading_id,
        vessel_name, origin_port, cigading_port;

        public ScheduleData(int id, String m_vessel_id, String voy_no_in, String voy_no_out, String origin_port_id,String est_arival, String est_departure, String remark, String m_jetty_cigading_id, String vessel_name,String cigading_port) {
            this.id = id;
            this.m_vessel_id = m_vessel_id;
            this.voy_no_in = voy_no_in;
            this.voy_no_out = voy_no_out;
            this.origin_port_id = origin_port_id;
            this.est_arival = est_arival;
            this.est_departure = est_departure;
            this.remark = remark;
            this.m_jetty_cigading_id = m_jetty_cigading_id;
            this.vessel_name = vessel_name;
            this.origin_port = origin_port;
            this.cigading_port = cigading_port;
        }
    }

    public String readString() {
        if (customerType!=null) {
            StringBuilder result = new StringBuilder();
            String newLine = System.getProperty("line.separator");

            result.append(this.getClass().getName());
            result.append(" Object {");
            result.append(newLine);

            //determine fields declared in this class only (no fields of superclass)
            Field[] fields = this.getClass().getDeclaredFields();

            //print field names paired with their values
            for (Field field : fields) {
                result.append("  ");
                try {
                    result.append(field.getName());
                    result.append(": ");
                    //requires access to private field:
                    result.append(field.get(this));
                } catch (IllegalAccessException ex) {
                    System.out.println(ex);
                }
                result.append(newLine);
            }
            result.append("}");
            return result.toString();
        }
        return "ERROR!";
    }
    /*
        "id": 5124,
        "t_booking_id": "19676",
        "description": "SUPPLY AIR VIA HYDRANT (KAPAL LN) TB. KIM HOCK TUG 3",
        "tariff": "7.000",
        "currency": "USD",
        "unit_code": "M3",
        "created": "2019-05-05 22:44:24",
        "created_by": "1169",
        "modified": "2019-05-05 22:44:24",
        "modified_by": null,
        "unit_code1": null,
        "unit_code2": null,
        "quantity": null,
        "quantity1": "30.000",
        "quantity2": null,
        "unit": {
        "id": 23,
                "code": "M3",
                "desc": "CUBIC METER",
                "created": "2016-09-29 10:11:50",
                "created_by": "27",
                "modified": "2016-09-29 10:29:33",
                "modified_by": "27"
    }
    */
}
