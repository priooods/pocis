package com.kbs.pocis.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.kbs.pocis.api.APIClient;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class UserData implements Parcelable {
    static final int detik = 10;
    public String username;
    public String password;
    private String token;
    private long time;
    private UserService service;
    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
        RefreshTime();
    }
    public UserData(String username, String password, long time) {
        this.username = username;
        this.password = password;
        this.time = time;
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

    protected UserData(Parcel in) {
        username = in.readString();
        password = in.readString();
        token = in.readString();
        RefreshTime();
    }
    //region Parceable Things
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(token);
    }
    //endregion
}
