package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateInformationRequest {
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Birthday")
    @Expose
    private String birthDay;

    public UpdateInformationRequest(String userName, String birthDay) {
        this.userName = userName;
        this.birthDay = birthDay;
    }
}
