package com.example.phonelist.network;

import com.example.phonelist.model.ContactApiModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactApi {
    @POST("api/Contatos")
    Call<Void> createContact(@Body ContactApiModel contact);
}