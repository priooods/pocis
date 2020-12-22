package com.kbs.pocis.service;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.createboking.ShowTemplate;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.createbooking.CallingShowTemp;
import com.kbs.pocis.service.createbooking.ModelSave.Model_Save_Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingData {
    //region Singleton
    public static BookingData i;
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
    public String customerId;
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
    public String voyage_number;

    public void setCustumer(String id, String customer, String related, String contract){
        this.customerId = id;
        customerType = customer;
        relatedVesel = related;
        this.contract = contract;
    }
    //data booking kedua ShowTemplate - SelectTemplate
    public ArrayList<BookTemplate> template;
    public static class BookTemplate{
        public int id;
        public String code;
        public String name;
        public ArrayList<BookTempList> listCheck;
        public Model_ShowTemplate getModel(){
            return new Model_ShowTemplate(id,code,name,null);
        }
        public BookTemplate(int id, String code,String name,@Nullable ArrayList<Model_SelectTemplate> list){
            this.id = id;
            this.code = code;
            this.name = name;
            if (list != null) {
                int size = 0;
                for(Model_SelectTemplate t:list){
                    if (t.checked)
                        size++;
                }
                listCheck = new ArrayList<>(size);
                for (Model_SelectTemplate t : list) {
                    if (t.checked) {
                        listCheck.add(new BookTempList(t.id, t.code, t.desc));
                    }
                }
            }
        }
        public class BookTempList{
            public int id;
            public String code;
            public String name;
            public BookTempList(int Id, String Code, String Name){
                id = Id;
                code = Code;
                name = Name;
            }
            public void getMap(Map<String,String> map, int i){
                map.put("Services["+i+"][m_service_code_id]",String.valueOf(id));
            }
        }
    }

    public void ShowBookUpdate(ArrayList<Model_ShowTemplate> temp) {
        //region Just Count Total Active Model_ShowTemplate
        int Count = 0;
        for (Model_ShowTemplate t : temp) {
            if (t.checked)
                Count++;
        }
        //endregion
        if (template == null) {
            template = new ArrayList<>(Count);
            Log.e("add_show","new!");
            for (Model_ShowTemplate t : temp) {
                if (t.checked) {
                    template.add(new BookTemplate(t.id, t.code, t.display_desc_header, t.list));
                }
            }
        }
        else{
            Log.e("add_show","update!");
            int i = 0;
            ArrayList<BookTemplate> book = template;
            template = new ArrayList<>(Count);
            for (Model_ShowTemplate t : temp) {
                if (t.checked) {
                    boolean add = true;
                    for(;i<book.size();i++){
                        if (book.get(i).id == t.id){
                            Log.e("add_show","update! shifting "+t.id+" "+t.code+" "+t.display_desc_header+ " "+book.get(i).listCheck.size());
                            template.add(book.get(i));
                            add = false;
                            break;
                        }
                    }
                    if (add) {
                        Log.e("add_show","update! new add "+t.id+" "+t.code+" "+t.display_desc_header);
                        template.add(new BookTemplate(t.id, t.code, t.display_desc_header, t.list));
                    }
                }
            }
        }
    }

    public boolean SelectBookUpdate(ArrayList<Model_ShowTemplate> temp){
        if (template.size() != temp.size())
            return false;
        for(int i = 0; i < temp.size();i++) {
            Model_ShowTemplate t = temp.get(i);
            if (t.id != template.get(i).id)
                return false;

            template.set(i, new BookTemplate(t.id, t.code, t.display_desc_header, t.list));
        }
        Log.i("finish","success update select!");
        return true;
    }
    //data booking ketiga UploadDocument
    public ArrayList<Model_UploadDocument> file;


    //data booking keempat AddComodity
    @SerializedName("commodity_booking")
    public ArrayList<Model_Commodity> commodity;

    //data Vessel
    public VesselData vessel;
    public static class VesselData{
        public int id_voyage, id_vessel,port_origin_id,port_discharge_id;
        public String vessel_name, port_discharge, port_origin,
                estimate_arival, estimate_departure,voyage_number, discharge_loading;
        public VesselData(){
            vessel_name = "";
            port_discharge = "";
            port_origin = "";
            estimate_arival = "";
            estimate_departure = "";
            voyage_number = "";
        }
        public VesselData(String vessel_name, String dis_port, int dis_port_id, String origin, int origin_id, String est_arival, String est_departure , int id_voy,int id_vess, String voyage_number, String disload){
            this.vessel_name = vessel_name;
            port_discharge = dis_port;
            port_discharge_id = dis_port_id;
            port_origin = origin;
            port_origin_id = origin_id;
            estimate_arival = est_arival;
            estimate_departure = est_departure;
            id_voyage = id_voy;
            id_vessel = id_vess;
            discharge_loading = disload;
            this.voyage_number = voyage_number;
        }
    }

    public void Upload(){
        i = null;
//        bookingDate = "23 November 2020";
//        bookingTime = "17:00 WIB";
//        BookingList.getI().AddBookingData(this);
    }

    public List<Model_Save_Services> code_service;
    public List<Model_Save_Services> getCode_service(){
        return this.code_service;
    }
    public void SelectServicesId (Model_Save_Services id_service){
        code_service.add(id_service);
    }

//    public List<CommodityData> dataComodity;
//    public static class CommodityData{
//        public String packages, tonage,commodity_type_id,m_customer_id, id_comodity;
//        public CommodityData(String type_id, String id, String pack,String tonag,String customer_id ){
//            this.commodity_type_id = type_id;
//            id_comodity = id;
//            packages = pack;
//            tonage = tonag;
//            this.m_customer_id = customer_id;
//
//        }
//    }

    public Approved approved;
    public static class Approved{
        public int id;
        public String booking_no, status, ppj_nomer, vessel_name, cosigne_name, temp_proj_no, schedule_code;
        public Approved(int id, String bokno,String stat,String ppj,String vesnam,String consigne,String temp,String codes){
            this.id = id;
            this.booking_no = bokno;
            this.status = stat;
            this.ppj_nomer = ppj;
            this.vessel_name = vesnam;
            this.cosigne_name = consigne;
            this.temp_proj_no = temp;
            this.schedule_code = codes;
        }
    }
}
