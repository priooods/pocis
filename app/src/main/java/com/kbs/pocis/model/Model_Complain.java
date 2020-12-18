package com.kbs.pocis.model;

public class Model_Complain {

    public static Model_Commodity mc;
    public static int Code;
    public static boolean isExist(){
        if (Model_Complain.mc == null){
            return false;
        }
        return true;
    }

    public String status, number, dates,person,type,title, desc;
    public String service_name,parameter,tariff_idr,domestic_tarif,internasional_tarif,ship_title;


    //for tariff Calculates
    public Model_Complain(String ship_title,String service_name, String parameter, String tariff_idr, String domestic_tarif, String internasional_tarif) {
        this.service_name = service_name;
        this.parameter = parameter;
        this.tariff_idr = tariff_idr;
        this.domestic_tarif = domestic_tarif;
        this.internasional_tarif = internasional_tarif;
        this.ship_title = ship_title;
    }

    public Model_Complain(String status, String number, String dates, String person, String type, String title, String desc) {
        this.status = status;
        this.number = number;
        this.dates = dates;
        this.person = person;
        this.type = type;
        this.title = title;
        this.desc = desc;
    }
}
