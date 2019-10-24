package com.example.gps.Response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }
}