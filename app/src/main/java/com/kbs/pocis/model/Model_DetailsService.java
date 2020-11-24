package com.kbs.pocis.model;

public class Model_DetailsService {
/*
                "service_code": "A003",
                        "service_name": "TAMBAT LUAR NEGERI",
                        "tarif": "IDR 0 Per TONAGE "
    */
    String service_code, service_name, tarif;
    public Model_DetailsService(String service_code, String service_name, String tarif){
        this.service_code = service_code;
        this.service_name = service_name;
        this.tarif = tarif;
    }
    public String getName(){
        return service_code + " - "+service_name;
    }
    public String getPrice(){
        return tarif;
    }
}
