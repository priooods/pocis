package com.kbs.pocis.service;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_Project;

import java.lang.reflect.Field;
import java.util.List;

public class BookingDetailData {
    //region Singleton
    public static BookingDetailData i;
    public static boolean isExist(){
        return BookingData.i != null;
    }

    @SerializedName("id")
    public int id;
    public String voyage_no;
    @SerializedName("customer_type_code")
    public String customerType;
    @SerializedName("flag_contract")
    public String contract;
    public String no_booking;
    public String status;
    public String vessel_name;

//
//    @SerializedName("schedule")
//    public ScheduleData vessel;
//    public static class ScheduleData{
//        int id;
//        public String m_vessel_id, voy_no_in, voy_no_out,origin_port_id, est_arival, est_departure, remark, m_jetty_cigading_id,
//        vessel_name, origin_port, cigading_port;
//
//        public ScheduleData(int id, String m_vessel_id, String voy_no_in, String voy_no_out, String origin_port_id,String est_arival, String est_departure, String remark, String m_jetty_cigading_id, String vessel_name,String cigading_port) {
//            this.id = id;
//            this.m_vessel_id = m_vessel_id;
//            this.voy_no_in = voy_no_in;
//            this.voy_no_out = voy_no_out;
//            this.origin_port_id = origin_port_id;
//            this.est_arival = est_arival;
//            this.est_departure = est_departure;
//            this.remark = remark;
//            this.m_jetty_cigading_id = m_jetty_cigading_id;
//            this.vessel_name = vessel_name;
//            this.origin_port = origin_port;
//            this.cigading_port = cigading_port;
//        }
//    }
    //for myproject & Inovice/Proforma
    public List<Model_Project> Information;
    public List<Model_Project> Commodity;
    public List<Model_Project> InformationAndDocument;
    public List<Model_Project> Service;
//    public static ArrayList<Model_UploadDocument> temp_document;
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

    public String title, content;
    public String description;
    public String created;
    public List<Model_Project> attachments;
    public List<Model_Project> details;
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
