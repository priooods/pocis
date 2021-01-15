package com.kbs.pocis.service;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BookingData {
    //region Singleton
    public static BookingData i;
    public static boolean isExist(){
        return BookingData.i != null;
    }

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
    public String no_booking;
    public String status;
    public String voyage_no;
    public String vessel_name;
    public String description;
    public String path;
    public boolean status_change = false;

    //this for set up booking information when change type, related or contract
    public void checkChange(boolean change){
        this.status_change = change;
    }

    //this for set up hide voyage number in vessel information
    public boolean hideVoyage(){
        return !relatedVesel.equals("Y") || !customerType.equals("GENERAL");
    }
    //this for set up skip vessel information if user check related vessel No
    public boolean checkVesselInfoSkip(){
        return relatedVesel.equals("N");
    }

    public void setCustumer(String id, String customer, String related, String contract){
        this.customerId = id;
        customerType = customer;
        this.relatedVesel = related;
        this.contract = contract;
    }
    //data booking kedua ShowTemplate - SelectTemplate
    public ArrayList<BookTemplate> template;
    public static class BookTemplate{
        public int id;
        public String code;
        public String name;
        public String image_file;
        public ArrayList<BookTempList> listCheck;
        public Model_ShowTemplate getModel(){
            return new Model_ShowTemplate(id,code,name,image_file);
        }
        public BookTemplate(int id, String code,String name,String img,@Nullable ArrayList<Model_SelectTemplate> list){
            this.id = id;
            this.code = code;
            this.name = name;
            this.image_file = img;
            if (list != null) {
                int size = 0;
                for(Model_SelectTemplate t:list){
                    if (t.checked)
                        size++;
                }
                listCheck = new ArrayList<>(size);
                for (Model_SelectTemplate t : list) {
                    if (t.checked) {
                        listCheck.add(new BookTempList(t.id, t.code, t.desc, t.m_service_code_id));
                    }
                }
            }
        }
        public class BookTempList{
            public int id;
            public String m_service_code_id;
            public String code;
            public String name;
            public BookTempList(int Id, String Code, String Name, String service_code_id){
                id = Id;
                code = Code;
                name = Name;
                m_service_code_id = service_code_id;
            }

            public RequestBody input_form(String req) {
                return RequestBody.create(req, MediaType.parse("multipart/form-data"));
            }

            public void getMap(HashMap<String,RequestBody> map, int i){
                map.put("Services["+i+"][m_service_code_id]", input_form(String.valueOf(m_service_code_id)));
                Log.i("template_out", "list: "+ m_service_code_id);
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
            Log.i("add_show","new!");
            for (Model_ShowTemplate t : temp) {
                if (t.checked) {
                    template.add(new BookTemplate(t.id, t.code, t.display_desc_header,t.image_file, t.list));
                }
            }
        }
        else{
            Log.i("add_show","update!");
            int i = 0;
            ArrayList<BookTemplate> book = template;
            template = new ArrayList<>(Count);
            for (Model_ShowTemplate t : temp) {
                if (t.checked) {
                    boolean add = true;
                    for(;i<book.size();i++){
                        if (book.get(i).id == t.id){
//                            Log.i("add_show","update! shifting "+t.id+" "+t.code+" "+t.display_desc_header+ " "+book.get(i).listCheck.size());
                            template.add(book.get(i));
                            add = false;
                            break;
                        }
                    }
                    if (add) {
                        Log.i("add_show","update! new add "+t.id+" "+t.code+" "+t.display_desc_header);
                        template.add(new BookTemplate(t.id, t.code, t.display_desc_header,t.image_file, t.list));
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

            template.set(i, new BookTemplate(t.id, t.code, t.display_desc_header,t.image_file, t.list));
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
        public String vessel_name, port_discharge, port_origin, est_value, depar_value,
                estimate_arival, estimate_departure,voyage_number, discharge_loading;
        public VesselData(){
            vessel_name = "";
            port_discharge = "";
            port_origin = "";
            estimate_arival = "";
            estimate_departure = "";
            voyage_number = "";
        }
        public VesselData(String vessel_name, String dis_port, int dis_port_id, String origin, int origin_id,
                          String est_arival, String est_departure , int id_voy,int id_vess,String disload,
                          String voyage_number, String est_val, String dpt_val){
            this.vessel_name = vessel_name;
            this.port_discharge = dis_port;
            this.port_discharge_id = dis_port_id;
            this.port_origin = origin;
            this.port_origin_id = origin_id;
            this.estimate_arival = est_arival;
            this.estimate_departure = est_departure;
            this.id_voyage = id_voy;
            this.id_vessel = id_vess;
            this.discharge_loading = disload;
            this.voyage_number = voyage_number;
            this.est_value = est_val;
            this.depar_value = dpt_val;
        }
    }

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
