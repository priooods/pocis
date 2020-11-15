package com.kbs.pocis.service;

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
    public String customerType, relatedVesel, contract;
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
                listCheck = new ArrayList<>(list.size());
                for (Model_SelectTemplate t : list) {
                    listCheck.add(new BookTempList(t.getId(), t.getName()));
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
    public List<Model_UploadDocument> file;

    //data booking keempat AddComodity
    public List<Model_Commodity> comodity;

    //data Vessel
    public String vesel_name, loading_shipcall, discharge_ship, port_discharge, port_origin,
            estimate_arival, estimate_departure;
}
