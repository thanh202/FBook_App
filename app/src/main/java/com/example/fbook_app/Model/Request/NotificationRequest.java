package com.example.fbook_app.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationRequest {
    @SerializedName("title")
    @Expose
    private String Title;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("IDUser")
    @Expose
    private int iDUser;
    @SerializedName("IDBook")
    @Expose
    private int iDBook;
    @SerializedName("Create_at")
    @Expose
    private String create_at;

    public NotificationRequest(String title, String content, int iDUser, int iDBook, String create_at) {
        Title = title;
        this.content = content;
        this.iDUser = iDUser;
        this.iDBook = iDBook;
        this.create_at = create_at;
    }
}
