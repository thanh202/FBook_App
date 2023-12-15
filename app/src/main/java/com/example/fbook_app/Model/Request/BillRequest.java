package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillRequest {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("IDBook")
    @Expose
    private int iDBook;
    @SerializedName("IDUser")
    @Expose
    private int iDUser;
    @SerializedName("PriceTotal")
    @Expose
    private Integer priceTotal;
    @SerializedName("Create_at")
    @Expose
    private String create_at;

    public BillRequest(String status, int iDBook, int iDUser, Integer priceTotal, String create_at) {
        this.status = status;
        this.iDBook = iDBook;
        this.iDUser = iDUser;
        this.priceTotal = priceTotal;
        this.create_at = create_at;
    }
}
