package com.kbs.pocis.model;

import com.google.gson.annotations.SerializedName;

public class CallingData {
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
    public CallingData(String username, String password){
        this.username = username;
        this.password = password;
    }
}
