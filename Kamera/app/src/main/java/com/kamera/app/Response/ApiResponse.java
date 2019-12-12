package com.kamera.app.Response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    @SerializedName("label")
    private String label;

    public String getLabel() {
        return label;
    }
}