package com.kamera.app.Retrofit;

import com.kamera.app.Response.ApiResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface ApiClient {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST
    Call<ApiResponse> store_image(
            @Url String url,
            @Field("image") String image
    );

    @POST
    Call<ApiResponse> predict(
            @Part MultipartBody.Part photo,
            @PartMap Map<String,RequestBody> text
    );
}