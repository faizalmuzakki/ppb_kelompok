package com.kamera.app.Retrofit;

import com.kamera.app.Response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiClient {
    @FormUrlEncoded
    @POST
    Call<ApiResponse> store_image(
            @Url String url,
            @Field("image") String image
    );
}