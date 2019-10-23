package com.example.gps.Response;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("message")
    private String pesan;

    public String getPesan() {
        return pesan;
    }
}