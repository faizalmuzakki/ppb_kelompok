package com.example.gps.Retrofit;

import com.example.gps.Response.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClient {
    @FormUrlEncoded
    @POST("store")
    Call<Response> store_location(
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude
    );
}