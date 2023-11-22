package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("PassWord")
    @Expose
    private String PassWord;

    public LoginRequest(String email, String passWord) {
        Email = email;
        PassWord = passWord;
    }
}
