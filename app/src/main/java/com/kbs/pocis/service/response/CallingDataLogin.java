package com.kbs.pocis.service.response;

import com.google.gson.annotations.SerializedName;

public class CallingDataLogin {
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
}
