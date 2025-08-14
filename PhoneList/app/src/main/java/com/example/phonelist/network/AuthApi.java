package com.example.phonelist.network;

import com.example.phonelist.model.ContactApiModel;
import com.example.phonelist.model.LoginRequest;
import com.example.phonelist.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("api/Auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/Contatos")
    Call<Void> syncContact(@Body ContactApiModel contact);
}