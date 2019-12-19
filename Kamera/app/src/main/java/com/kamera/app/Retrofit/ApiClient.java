package com.kamera.app.Retrofit;

import com.kamera.app.Response.ApiResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiClient {
//    @FormUrlEncoded
//    @Headers("Accept: application/json")
//    @POST
//    Call<ApiResponse> store_image(
//            @Url String url,
//            @Field("image") String image
//    );

    @Headers("Accept: application/json")
    @Multipart
    @POST("ppb-predict/2/predict")
    Call<String> predict(
            @Part MultipartBody.Part image
    );
}