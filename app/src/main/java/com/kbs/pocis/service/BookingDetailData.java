package com.kbs.pocis.service;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_DetailsCommodity;
import com.kbs.pocis.model.Model_DetailsFile;
import com.kbs.pocis.model.Model_DetailsService;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.PublicList.PublicList;

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

    @SerializedName("id")
    public int id;
    public String voyage_no;
    public String voyage_number;
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
    public String discharge_or_loading;
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
    public static class Compound {
        public int id;
        public String t_booking_id,description,tariff,currency,unit_code,crated,created_by,modified,modified_by;
        public String unit_code1,unit_code2,quantity,quantity1,quantity2,Unit;
        public Unit unit;
    }
    public static class Unit{
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
    //for myproject & Inovice/Proforma
    public Model_Project Information;
    public List<Model_Project> InformationAndDocument;
    public List<Model_Project> Service;
    public List<Model_Project> VesselReport;
    public List<List<Model_Project>> Piloting;
    public List<Model_Project> Documents;


    //for Menu Vessel Schedule

    public Datass AllTabsData;
    public static class Datass{
        @SerializedName("0")
        public Model_Project data;
    }

    //for Menu Unloading Vessel Schedule
    public List<Model_Project> Header;

    //for Menu Unloading Details
    public List<Model_Project> ActualVesselInProgress;
    public Ship ActualStowageMonitoring;
    public static class Ship{
        @SerializedName("HatchTotal")
        public List<Model_Project> HatchTotal;
        @SerializedName("HatchDetails")
        public List<Model_Project> HatchDetails;
    }
    public List<Model_Project> ItemSummary;
    public List<Model_Project> HeaderAndCCTV;
    public List<Model_Project> ContactAgent;
    public List<Model_Project> ContactPbm;
    public List<Model_Project> ActualTruckMonitoring;

    //Ini untuk User Profile
    public String name;
    public String m_city_id;
    public String address;
    public String phone;
    public String fax;
    public String email;
    public String npwp;
    public String contact_email;
    public String contact_hp;
    public String message;
    public String code;
    public String contact;
    public List<Type> types;
    public static class Type{
        public String name;
    }

    public String picture, title, content;
//    public String id;

    public String complain_title;
    public String complain_desc;
    public String t_customer_complaint_id;
    public String reason_name;
    public String user_name;
    public String description;
    public String created;
    public List<Model_Project> attachments;
    public List<Model_Project> details;
    public String reason_code;
    public String reason_desc;


    //for progress booking
    public List<Model_Project> List;


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
}
