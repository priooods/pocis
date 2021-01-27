package com.kbs.pocis.api;

import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.CallProjectList;
import com.kbs.pocis.service.PublicList.PublicList;
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
import retrofit2.http.Query;

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
    @POST("auth/info")
    Call<CallingDetail> getUserInfo(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("profile")
    Call<BookingDetailData> changeProfile(
            @Field("m_city_id") int m_city_id,
            @Field("name") String name,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("npwp") String npwp,
            @Field("fax") String fax,
            @Field("contact") String contact,
            @Field("contact_email") String contact_email,
            @Field("contact_hp") String contact_hp,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("auth/changepass")
    Call<Calling> updatePassword(
            @Field("password") String password,
            @Field("c_password") String c_password,
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

    //tbooking/cancel
    @FormUrlEncoded
    @POST("tbooking/cancel")
    Call<CallingDetail> cancelBooking(
            @Field("token") String token,
            @Field("tbooking_id") String tbooking_id,
            @Field("remark") String remark
    );
    //tarif-approve/reject
    @FormUrlEncoded
    @POST("tarif-approve/reject")
    Call<CallingDetail> rejectTariff(
            @Field("token") String token,
            @Field("tbooking_id") String tbooking_id,
            @Field("remark") String remark
    );
    //tarif-approve/approve
    @FormUrlEncoded
    @POST("tarif-approve/approve")
    Call<CallingDetail> approveTariff(
            @Field("token") String token,
            @Field("tbooking_id") String tbooking_id,
            @Field("remark") String remark
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
            @Field("discharge_or_loading") String discharge_or_loading,
            @Field("m_vessel_id") int m_vessel_id
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
            @Query("token") String token
            ,@PartMap Map<String, RequestBody> Booking
            ,@PartMap Map<String, RequestBody> BookingVessel//BookingVessel
            ,@Part MultipartBody.Part... document
    );

    @FormUrlEncoded
    @POST("invoice/listInvoice")
    Call<PublicList> getListInvoice(
            @Field("token") String token,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("proforma/listProforma")
    Call<PublicList> getListProforma(
            @Field("token") String token,
            @Field("page") String page
    );
//my-projects/listProjectList

    @FormUrlEncoded
    @POST("my-projects/listProjectList")
    Call<CallProjectList> getListProject(
            @Field("token") String token,
            @Field("page") String page
    );


    @FormUrlEncoded
    @POST("my-projects/listProjectsApproval")
    Call<PublicList> getListApproved(
            @Field("token") String token,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("my-projects/listBapjList")
    Call<PublicList> getListBAPJ(
            @Field("token") String token,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("invoice/detailInvoice")
    Call<CallingDetail> getDetailInvoice(
            @Field("token") String token,
            @Field("t_billing_invoice_id") String t_billing_invoice_id,
            @Field("flag_compound") String flag_compound
    );
    @FormUrlEncoded
    @POST("proforma/detailProforma")
    Call<CallingDetail> getDetailProforma(
            @Field("token") String token,
            @Field("t_project_header_id") String t_project_header_id,
            @Field("flag_compound") String flag_compound
    );

    @FormUrlEncoded
    @POST("my-projects/detailProjectList")
    Call<CallingDetail> getDetailList(
            @Field("token") String token,
            @Field("t_booking_id") String t_booking_id,
            @Field("t_project_header_id") String flag_compound
    );

    @FormUrlEncoded
    @POST("my-projects/detailBapjList")
    Call<CallingDetail> getDetailBAPJ(
            @Field("token") String token,
            @Field("t_project_report_header_id") String t_project_report_header_id,
            @Field("t_vessel_schedule_id") String t_vessel_schedule_id
    );

    @FormUrlEncoded
    @POST("my-projects/detailProjectsApproval")
    Call<CallingDetail> getDetailApproval(
            @Field("token") String token,
            @Field("t_booking_id") String t_booking_id,
            @Field("t_project_header_id") String t_project_header_id
    );

    @GET("master/complain-reason/")
    Call<List<BookingDetailData>> getMyComplaintType();

    //operational-monitoring/loadUnloadProgress
    @FormUrlEncoded
    @POST("operational-monitoring/loadUnloadProgress")
    Call<PublicList> getUnloading(
            @Field("token") String token,
            @Field("progress_tab") String progress_tab,
            @Field("page") String page
    );

    //operational-monitoring/vesselSchedule
    @FormUrlEncoded
    @POST("operational-monitoring/vesselSchedule")
    Call<PublicList> getVesselSchedule(
            @Field("token") String token,
            @Field("page") String page
    );

    //operational-monitoring/vesselSchedule
    @FormUrlEncoded
    @POST("operational-monitoring/vesselScheduleDetail")
    Call<CallingDetail> detailsVessel(
            @Field("token") String token,
            @Field("t_vessel_schedule_id") String t_vessel_schedule_id,
            @Field("voyage_no") String voyage_no
    );

    //operational-monitoring/vesselSchedule
    @FormUrlEncoded
    @POST("operational-monitoring/loadUnloadProgressDetail")
    Call<CallingDetail> detailUnloading(
            @Field("token") String token,
            @Field("t_vessel_schedule_id") String t_vessel_schedule_id,
            @Field("voyage_no") String voyage_no
    );

    @FormUrlEncoded
    @POST("complain/list")
    Call<PublicList> getComplainList(
            @Field("token") String token,
            @Field("page") String page
    );

    @Multipart
    @POST("complain/add")
    Call<CallingSaveBok> saveComplaint(
            @Query("token") String token
            ,@PartMap Map<String, Integer> ComplainReason
            ,@PartMap Map<String, RequestBody> Complain
            ,@Part MultipartBody.Part... document
    );

    @FormUrlEncoded
    @POST("complain/view")
    Call<CallingDetail> detailComplaint(
            @Field("token") String token,
            @Field("complain_id") int id
    );

    @FormUrlEncoded
    @POST("complain/comment")
    Call<CallingDetail> newCommentComplain(
            @Field("token") String token,
            @Field("m_complain_id") int id,
            @Field("description") String description
    );

    //cust-news-rewards/news
    @FormUrlEncoded
    @POST("cust-news-rewards/news")
    Call<PublicList> customerNews(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("operational-monitoring/progressBooking")
    Call<CallingDetail> progressBooking(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("cust-news-rewards/rewards")
    Call<PublicList> customerRewards(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("operational-monitoring/progressBookingDetail")
    Call<CallingDetail> progressDetail(
            @Field("token") String token
            ,@Field("booking_no ") String booking_no
    );

    @FormUrlEncoded
    @POST("operational-monitoring/vesselLineUp")
    Call<CallingDetail> vesselLineup(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("operational-monitoring/tariffCalculator")
    Call<CallingDetail> vesselService(
            @Field("token") String token,
            @Field("calculator_type") String calculator_type,
            @Field("gt_kapal") String gt_kapal,
            @Field("est_berthing") String est_berthing,
            @Field("est_departure") String est_departure
    );

    @FormUrlEncoded
    @POST("operational-monitoring/tariffCalculator")
    Call<CallingDetail> goodsService(
            @Field("token") String token,
            @Field("total_tonnage") String total_tonnage
    );
}
