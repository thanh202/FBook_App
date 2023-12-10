package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFavouriteResponse {
    @SerializedName("IDFavorite")
    @Expose
    private Integer iDFavorite;
    @SerializedName("IDBook")
    @Expose
    private String iDBook;
    @SerializedName("IDUser")
    @Expose
    private String iDUser;

    public Integer getIDFavorite() {
        return iDFavorite;
    }

    public void setIDFavorite(Integer iDFavorite) {
        this.iDFavorite = iDFavorite;
    }

    public String getIDBook() {
        return iDBook;
    }

    public void setIDBook(String iDBook) {
        this.iDBook = iDBook;
    }

    public String getIDUser() {
        return iDUser;
    }

    public void setIDUser(String iDUser) {
        this.iDUser = iDUser;
    }
}
