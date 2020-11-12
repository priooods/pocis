package com.kbs.pocis.service;

import com.kbs.pocis.service.response.CallingDataLogin;
import com.kbs.pocis.service.response.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    //http://cigading.ptkbs.co.id:9280/v1/auth/login
    @FormUrlEncoded
    @POST("auth/login")
    Call<CallingDataLogin> getUserLogin(
            @Field("username") String id,
            @Field("password") String secret
    );

    @FormUrlEncoded
    @POST("auth/logout")
    Call<LogoutResponse> getLogoutUser(
            @Field("token") String token
    );


}
