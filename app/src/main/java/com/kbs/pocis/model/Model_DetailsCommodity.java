package com.kbs.pocis.model;

import android.util.Log;

import java.lang.reflect.Field;

public class Model_DetailsCommodity {
/*
                "id": 31013,
                        "t_booking_id": "36538",
                        "m_commodity_name_id": "3",
                        "m_commodity_type_id": "4",
                        "tonage": "950.000",
                        "packaging": "1",
                        "commoditycode": {
                "id": 3,
                        "code": "SOBM",
                        "desc": "SOYBEAN MEAL",
                        "m_commodity_type_name": "-",
                        "type": null
                },
                    "commoditytype": {
                "id": 4,
                        "desc": "GRAIN BULK"
                 }
*/
public int id;
public String t_booking_id,m_commodity_name_id,m_commodity_type_id,tonage,packaging;
public Code commoditycode;
public Type commoditytype;
class Code {
    public int id;
    public String code,desc,m_commodity_type_name,type;
}
class Type {
    public int id;
    public String desc;
}

    public Model_DetailsCommodity(String comodityName, String comodityTyoe, String packageNo, String tonage) {
        this.commoditycode.m_commodity_type_name = comodityName;
        this.commoditycode.type = comodityTyoe;
        this.packaging = packageNo;
        this.tonage = tonage;
    }
    public String getCommodityName(){
        return commoditycode.m_commodity_type_name;
    }
    public String getCommodityType(){
        return commoditytype.desc;
    }

    public String getComodityTyoe() {
        return comodityTyoe;
    }

    public void setComodityTyoe(String comodityTyoe) {
        this.comodityTyoe = comodityTyoe;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getTonage() {
        return tonage;
    }

    public void setTonage(String tonage) {
        this.tonage = tonage;
    }
}
