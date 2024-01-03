package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationResponse {
    @SerializedName("result")
    @Expose
    private List<NotificationResponse.Result> result;

    public List<NotificationResponse.Result> getResult() {
        return result;
    }

    public void setResult(List<NotificationResponse.Result> result) {
        this.result = result;
    }

    public static class Result implements Serializable {
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
        @SerializedName("IDNoti")
        @Expose
        private int iDNofi;
        @SerializedName("Create_at")
        @Expose
        private String create_at;
        @SerializedName("BookName")
        @Expose
        private String bookName;

        public Result(String title, String content, int iDUser, int iDBook, int iDNofi, String create_at, String bookName) {
            Title = title;
            this.content = content;
            this.iDUser = iDUser;
            this.iDBook = iDBook;
            this.iDNofi = iDNofi;
            this.create_at = create_at;
            this.bookName = bookName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getiDUser() {
            return iDUser;
        }

        public void setiDUser(int iDUser) {
            this.iDUser = iDUser;
        }

        public int getiDBook() {
            return iDBook;
        }

        public void setiDBook(int iDBook) {
            this.iDBook = iDBook;
        }

        public int getiDNofi() {
            return iDNofi;
        }

        public void setiDNofi(int iDNofi) {
            this.iDNofi = iDNofi;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }
    }


}
