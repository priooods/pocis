package com.kbs.pocis.api;

import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.service.onlinebooking.TariffData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    //http://cigading.ptkbs.co.id:9280/v1/auth/login
    @FormUrlEncoded
    @POST("auth/login")
    Call<CallingData> getUserLogin(
            @Field("username") String id,
            @Field("password") String secret
    );
    @FormUrlEncoded
    @POST("auth/logout")
    Call<CallingData> getLogoutUser(
            @Field("token") String token
    );
    @FormUrlEncoded
    @POST("tbooking/all")
    Call<CallingData> getAllBooking(
            @Field("token") String token,
            @Field("page") String page
    );
    @FormUrlEncoded
    @POST("tbooking/tocancel")
    Call<CallingData> getAllCancel(
            @Field("token") String token,
            @Field("page") String page
    );
    @FormUrlEncoded
    @POST("tarif-approve/all")
    Call<TariffData> getTariffAprove(
            @Field("token") String token,
            @Field("page") String page
    );
    @FormUrlEncoded
    @POST("tbooking/view")
    Call<CallingData> getBookingDetail(
            @Field("token") String token,
            @Field("tbooking_id") String tbooking_id
    );
}
