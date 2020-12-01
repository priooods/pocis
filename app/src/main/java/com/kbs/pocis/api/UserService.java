package com.kbs.pocis.api;

import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.createbooking.CreateBok;
import com.kbs.pocis.service.createbooking.CreateTemp;
import com.kbs.pocis.service.createbooking.DataCalling;
import com.kbs.pocis.service.detailbooking.DetailData;
import com.kbs.pocis.service.onlinebooking.CallingData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<CallingData> getTariffAprove(
            @Field("token") String token,
            @Field("page") String page
    );
    @FormUrlEncoded
    @POST("tbooking/view")
    Call<DetailData> getBookingDetail(
            @Field("token") String token,
            @Field("tbooking_id") String tbooking_id
    );

    @FormUrlEncoded
    @POST("tbooking/customer_type")
    Call<CreateBok> getCustumerType(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("tbooking/template_header")
    Call<CreateTemp> getShowTemplate(
            @Field("token") String token,
            @Field("customer_type_id") int id,
            @Field("flag_related_vessel") String related,
            @Field("flag_contract") String contract
    );


    @GET("master/consignee/{name}")
    Call<List<DataCalling>> getConsignee(
            @Path("name") String name
    );

    @GET("master/commodity_type/{desc}")
    Call<List<DataCalling>> getCommodityType(
            @Path("desc") String desc
    );

    @FormUrlEncoded
    @POST("master/voyage")
    Call<List<BookingDetailData>> getVoyageNumber(
            @Field("voyage_no") String voyage_no
    );

    @GET("master/port_cigadings/{name}")
    Call<List<DataCalling>> getPortDischarge(
            @Path("name") String name
    );

    @GET("master/ports")
    Call<List<DataCalling>> getPortOrigin();


}
