package com.kbs.pocis.service;

import com.kbs.pocis.api.LoginResponse;
import com.kbs.pocis.model.Model_User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    //http://cigading.ptkbs.co.id:9280/v1/auth/login
    @POST("auth/login")
    Call<LoginResponse> UserLogin(@Body Model_User modelUser);


}
