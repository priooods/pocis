package com.kbs.pocis.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.kbs.pocis.api.APIClient;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.onlineboking.Filters;
import com.kbs.pocis.service.onlinebooking.CallingData;

import java.util.Calendar;

public class UserData {
    //region Singleton
    public static UserData i;
    public static boolean isExists(){
        return (i!=null);
    }
    //endregion
    static final int detik = 10;
    public String username;
    public String password;
    private String token;
    private long time;
    private UserService service;
    public Filters filter;

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
        RefreshTime();
        i = this;
    }
    public void updateFilter(String no, String vessel) {
        if (!no.isEmpty() && !vessel.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(CallingData.Booking data) {
                    return data.no_booking!=null && data.vessel_name != null && data.no_booking.contains(no) && data.vessel_name.contains(vessel);
                }
            };
        } else if (!no.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(CallingData.Booking data) {
                    return data.no_booking!=null && data.no_booking.contains(no);
                }
            };
        } else if (!vessel.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(CallingData.Booking data) {
                    return data.vessel_name != null && data.vessel_name.contains(vessel);
                }
            };
        } else {
            filter = null;
        }
    }

    public UserData(String username, String password, long time) {
        this.username = username;
        this.password = password;
        this.time = time;
        filter = null;
    }

    public UserService getService(){
        if (service == null){
            service = APIClient.getClient().create(UserService.class);
        }
        return service;
    }

    public void RefreshTime(){
        time = Calendar.getInstance().getTime().getTime();
    }

    public String getToken() {
        RefreshTime();
        return token;
    }

    public long getTime(){
        return time;
    }

    public boolean CheckTime(){
        Log.i("time","check Null time user " + (time)+ " "+Calendar.getInstance().getTime().getTime());
        return (Calendar.getInstance().getTime().getTime()-time)/(1000) <= detik;
    }

    public int CheckCount(){
        return (int)(Calendar.getInstance().getTime().getTime()-time)/1000;
    }

    public void setToken(String token) {
        Log.i("token","Token Updated to : "+token);
        this.token = token;
    }
}
