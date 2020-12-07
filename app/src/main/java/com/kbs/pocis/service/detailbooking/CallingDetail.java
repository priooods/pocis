package com.kbs.pocis.service.detailbooking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;

import java.lang.reflect.Field;

import es.dmoral.toasty.Toasty;

public class CallingDetail extends Calling {
    public BookingDetailData data;

    public String readString() {
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
        result.append("}");
        Log.i("detail_booking",data.readString());
        return result.toString();
    }

}
