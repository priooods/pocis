package com.kbs.pocis.model;

import java.util.List;

public class Model_Complain {

    public static Model_Commodity mc;
    public static int Code;
    public static boolean isExist(){
        if (Model_Complain.mc == null){
            return false;
        }
        return true;
    }

    public static List<Model_Complain> CalculatorResults;
    public String service_name,parameter,tariff,
            tarif_domestik,tarif_internasional;
}
