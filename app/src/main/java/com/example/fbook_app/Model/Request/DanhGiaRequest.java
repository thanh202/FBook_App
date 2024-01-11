package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DanhGiaRequest {
    @SerializedName("IDBook")
    @Expose
    private int iDBook;
    @SerializedName("IDUser")
    @Expose
    private int iDUser;
    @SerializedName("Rate")
    @Expose
    private float rate;
    @SerializedName("Comment")
    @Expose
    private String comment;

    public DanhGiaRequest(int iDBook, int iDUser, float rate, String comment) {
        this.iDBook = iDBook;
        this.iDUser = iDUser;
        this.rate = rate;
        this.comment = comment;
    }
}
