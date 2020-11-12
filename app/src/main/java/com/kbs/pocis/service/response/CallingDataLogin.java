package com.kbs.pocis.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CallingDataLogin implements Parcelable {
    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
    @SerializedName("data")
    public DataToken data;

    public class DataToken{
        @SerializedName("token")
        public String token;
    }

    public String username;
    public String password;
    public CallingDataLogin(String username, String password){
        this.username = username;
        this.password = password;
    }

    protected CallingDataLogin(Parcel in) {
        error = in.readString();
        desc = in.readString();
        username = in.readString();
        password = in.readString();
    }

    public static final Creator<CallingDataLogin> CREATOR = new Creator<CallingDataLogin>() {
        @Override
        public CallingDataLogin createFromParcel(Parcel in) {
            return new CallingDataLogin(in);
        }

        @Override
        public CallingDataLogin[] newArray(int size) {
            return new CallingDataLogin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data.token);
    }

}
