package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePasswordRequest {
    @SerializedName("PassWord")
    @Expose
    private String passWord;
    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    public UpdatePasswordRequest(String passWord, String newPassword) {
        this.passWord = passWord;
        this.newPassword = newPassword;
    }
}
