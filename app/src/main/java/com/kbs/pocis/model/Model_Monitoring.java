package com.kbs.pocis.model;

public class Model_Monitoring {
    public static Model_Monitoring mn;
    public static int Code;
    public static boolean isExist(){
        if (Model_Monitoring.mn == null){
            return false;
        }
        return true;
    }

    public String status, voyage, est_bethring, est_departure,act_bethring, act_departure, jetty,
            name,code, ship_line,est_arival, nomer;
    public int id;

    public Model_Monitoring(String status, String voyage, String est_bethring, String est_departure,
                            String act_bethring, String act_departure,
                            String jetty, String name, String code, String ship_line, String est_arival, String nomer, int id) {
        this.status = status;
        this.voyage = voyage;
        this.est_bethring = est_bethring;
        this.est_departure = est_departure;
        this.act_bethring = act_bethring;
        this.act_departure = act_departure;
        this.jetty = jetty;
        this.name = name;
        this.code = code;
        this.ship_line = ship_line;
        this.est_arival = est_arival;
        this.nomer = nomer;
        this.id = id;
    }
}
