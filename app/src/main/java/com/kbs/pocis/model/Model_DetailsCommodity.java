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
/*
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }

        result.append( " @@@commoditycode :" + String.valueOf(commoditycode.id) + " " + commoditycode.code +" "+commoditycode.desc+" "+commoditycode.m_commodity_type_name+" "+commoditycode.type);
        result.append(newLine);
        result.append( " @@@commoditytype :" + String.valueOf(commoditytype.id) + " " + commoditytype.desc);
        result.append(newLine);
        result.append("}");
        return result.toString();
    }
 */
}
