package com.kamera.app.Response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}