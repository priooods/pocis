package com.kbs.pocis.service;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookingList {
    //region Singleton
    public static BookingList i;
    public static BookingList getI(){
        if (i == null) {
            i = new BookingList();
            Log.i("data","New BookingList! befause of null");
        }
        return i;
    }
    // data booking pertama CustomerAddForm
    public ArrayList<BookingData> data_list;
    public void AddBookingData(@NotNull BookingData add){
        add.uploadDate = "Waktu Upload<?>";
        data_list.add(add);
    }
    public int pCurrent,pLast;
    public int Total;
    public BookingList(){
        data_list = new ArrayList<>(5);
    }
    public CallingData getAllBookingLikeAPI(int current_page) {
        CallingData output = new CallingData();
        output.error = "0";
        output.desc = "output was generate with offline method BookingList class";
        output.data = new CallingData.Input();

        output.data.current_page = current_page;
        pCurrent = current_page;

        output.data.from_page = 1 + current_page * 5;
        output.data.to_page = Math.min(1 + current_page * 5 + 5, data_list.size());
        Log.i("OFFLINE","finish load page : "+output.data.from_page+" to "+output.data.to_page);
        if (output.data.from_page > output.data.to_page) {
            output.data.from_page = -1;
            output.data.to_page = 0;
            output.data.book = null;
            output.data.total = data_list.size();
            output.data.per_page = 0;
            output.data.book = new CallingData.Booking[0];
            return output;
        }
        output.data.per_page = output.data.to_page - output.data.from_page + 1;
        output.data.book = new CallingData.Booking[output.data.per_page];
        for(int i = 0;i<output.data.per_page;i+=1) {
            output.data.book[i] = new CallingData.Booking();
            {
                CallingData.Booking book = output.data.book[i];
                book.booking_id = String.valueOf(output.data.from_page + i);
                Log.i("TAG","ADD OFFLINE DATA-"+i);
                updateDataFromBookingData(book, data_list.get(output.data.from_page + i - 1));
            }
        }
        return output;
    }

    void updateDataFromBookingData(CallingData.Booking book, BookingData data){
        book.no_contract = data.contract;
        book.vessel_name = data.vessel.vessel_name;
        book.flag_related_vessel = data.relatedVesel;
        book.flag_contract = data.contract;
        book.customer_name = data.customerType;
        book.customer_type_name = data.customerType;
        book.booking_date = data.uploadDate;
        book.formatted_booking_date = "?"+data.vessel.estimate_departure;
        book.formatted_booking_time = "?";
        book.status_booking = "BOOKING";
    }
}
