package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFavouriteRequest {
    @SerializedName("IDBook")
    @Expose
    private int idBook;
    @SerializedName("IDUser")
    @Expose
    private int idUser;

    public AddFavouriteRequest(int idBook, int idUser) {
        this.idBook = idBook;
        this.idUser = idUser;
    }
}
