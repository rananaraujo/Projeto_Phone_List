package com.example.phonelist.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static final String BASE_URL = "http://192.168.0.7:5271/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static AuthApi getAuthApi() {
        return getRetrofitInstance().create(AuthApi.class);
    }

    public static ContactApi getContactApi() {
        return getRetrofitInstance().create(ContactApi.class);
    }
}