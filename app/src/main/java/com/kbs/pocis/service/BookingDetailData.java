package com.kbs.pocis.service;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_DetailsCommodity;
import com.kbs.pocis.model.Model_DetailsFile;
import com.kbs.pocis.model.Model_DetailsService;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
    //endregion
    @SerializedName("customer_type_code")
    public String customerType;
    public String booking_date;
    @SerializedName("flag_related_flag")
    public String relatedVesel;
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
    public String voyage_no;
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
    public String compoundbooking;

    //data booking kedua ShowTemplate - SelectTemplate
    @SerializedName("customerbooking")
    public ArrayList<Model_DetailsService> template;
    //data booking ketiga UploadDocument
    @SerializedName("documentbooking")
    public ArrayList<Model_DetailsFile> file;
    //data booking keempat AddComodity
    @SerializedName("commoditybooking")
    public ArrayList<Model_DetailsCommodity> commodity;

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
}
