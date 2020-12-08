package com.kbs.pocis.api;

import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.createbooking.CallingSaveBok;
import com.kbs.pocis.service.createbooking.CallingSelectTemp;
import com.kbs.pocis.service.createbooking.CallingShowTemp;
import com.kbs.pocis.service.createbooking.CreateBok;
import com.kbs.pocis.service.createbooking.CallingList;
import com.kbs.pocis.service.detailbooking.CallingDetail;
import com.kbs.pocis.service.onlinebooking.CallingData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Call<CallingDetail> getBookingDetail(
            @Field("token") String token,
            @Field("tbooking_id") String tbooking_id
    );

    @FormUrlEncoded
    @POST("tbooking/customer_type")
    Call<CreateBok> getCustumerType(
            @Field("token") String token
    );

    //Show_Template or Template_Header
    @FormUrlEncoded
    @POST("tbooking/template_header")
    Call<CallingShowTemp> getShowTemplate(
            @Field("token") String token,
            @Field("customer_type_id") int id,
            @Field("flag_related_vessel") String related,
            @Field("flag_contract") String contract
    );

    //Select_Template or Template_Detail
    @FormUrlEncoded
    @POST("tbooking/template_detail")
    Call<CallingSelectTemp> getSelectTemplate(
            @Field("token") String token,
            @Field("m_booking_template_header_id[]") ArrayList<String> m_booking_template_header_id
    );


    @GET("master/consignee/{name}")
    Call<List<CallingList>> getConsignee(
            @Path("name") String name
    );

    @GET("master/commodity/{name}")
    Call<List<CallingList>> getCommodityType(
            @Path("name") String name
    );


    @FormUrlEncoded
    @POST("master/voyage")
    Call<List<BookingDetailData>> getVoyageNumber(
            @Field("voyage_no") String voyage_no
    );

    @GET("master/vessel/{name}")
    Call<List<CallingList>> getVesselId(
            @Path("name") String name
    );

    @GET("master/port_cigadings/{name}")
    Call<List<CallingList>> getPortDischarge(
            @Path("name") String name
    );

    @GET("master/ports")
    Call<List<CallingList>> getPortOrigin();

    @Multipart
    @POST("tbooking/new")
    Call<CallingSaveBok> saveBooking(
            @PartMap Map<String, String> Booking,
            @PartMap Map<String, String> BookingVessel,
            @PartMap Map<String, String> VesselSchedule,
            @PartMap Map<String, String> CommodityBooking,
            @PartMap Map<String, String> Services,
            @Part List<MultipartBody.Part> FileUpload
    );
}
