package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("IDUser")
    @Expose
    private String iDUser;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("PassWord")
    @Expose
    private String passWord;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Birthday")
    @Expose
    private String birthday;
    @SerializedName("Phone")
    @Expose
    private String phone;

    public RegisterRequest(String iDUser, String userName, String passWord, String email, String birthday, String phone) {
        this.iDUser = iDUser;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
    }
}
