package com.kbs.pocis.service.response;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {
    @SerializedName("error_code")
    public String error;
    @SerializedName("error_desc")
    public String desc;
//    @SerializedName("data")
//    public CallingDataLogin.DataToken data;
//    public class DataToken{
//        @SerializedName("token")
//        public String token;
//    }

    public String token;
    public LogoutResponse(String token){
        this.token = token;
    }
}
