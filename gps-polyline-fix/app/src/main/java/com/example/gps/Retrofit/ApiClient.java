package com.example.gps.Retrofit;

import com.example.gps.Response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClient {
    @FormUrlEncoded
    @POST("store")
    Call<ApiResponse> store_location(
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude
    );
}