package com.kbs.pocis.service;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;

import java.util.ArrayList;
import java.util.List;

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
    public String customerType, relatedVesel, contract,uploadDate;
    public void setCustumer(String customer, String related, String contract){
        customerType = customer;
        relatedVesel = related;
        this.contract = contract;
    }
    //data booking kedua ShowTemplate - SelectTemplate
    public List<BookTemplate> template;
    public class BookTemplate{
        public String code;
        public String name;
        public List<BookTempList> listCheck;
        public BookTemplate(String code,String name,@Nullable List<Model_SelectTemplate> list){
            this.code = code;
            this.name = name;
            if (list != null) {
                int size = 0;
                for(Model_SelectTemplate t:list){
                    if (t.isChecked())
                        size++;
                }
                listCheck = new ArrayList<>(size);
                for (Model_SelectTemplate t : list) {
                    if (t.isChecked()) {
                        listCheck.add(new BookTempList(t.getId(), t.getName()));
                    }
                }
            }
        }
        public class BookTempList{
            public String code;
            public String name;
            public BookTempList(String Code, String Name){
                code = Code;
                name = Name;
            }
        }
    }
    public void SetBookTemplate(List<Model_ShowTemplate> temp){
        //region Just Count Total Active Model_ShowTemplate
        int Count=0;
        for(Model_ShowTemplate t : temp){
            if (t.getCheck())
                Count++;
        }
        //endregion
        template = new ArrayList<>(Count);
        for(Model_ShowTemplate t : temp) {
            if (t.getCheck()) {
                template.add(new BookTemplate(t.getId(), t.getName(), t.list));
            }
        }
    }
    //data booking ketiga UploadDocument
    public ArrayList<Model_UploadDocument> file;

    //data booking keempat AddComodity
    public ArrayList<Model_Commodity> commodity;

    //data Vessel
    public VesselData vessel;
    public static class VesselData{
        public String vessel_name, loading_shipcall, discharge_ship, port_discharge, port_origin,
                estimate_arival, estimate_departure;
        public VesselData(String vessel_name, String shipcall, String dis_ship, String dis_port, String origin, String est_arival, String est_departure ){
            this.vessel_name = vessel_name;
            loading_shipcall = shipcall;
            discharge_ship = dis_ship;
            port_discharge = dis_port;
            port_origin = origin;
            estimate_arival = est_arival;
            estimate_departure = est_departure;
        }
        public void findData(){
            Log.i("tag","Nothin Happen");
        }
    }

    public void Upload(){
        i = null;
        BookingList.getI().AddBookingData(this);
    }
}
