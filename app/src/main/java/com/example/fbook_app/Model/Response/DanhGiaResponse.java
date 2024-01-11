package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DanhGiaResponse {
    @SerializedName("result")
    @Expose
    private List<DanhGiaResponse.Result> result;

    public List<DanhGiaResponse.Result> getResult() {
        return result;
    }

    public void setResult(List<DanhGiaResponse.Result> result) {
        this.result = result;
    }
    public static class Result{

        @SerializedName("IDFeedBack")
        @Expose
        private Integer iDFeedBack;
        @SerializedName("IDUser")
        @Expose
        private Integer iDUser;
        @SerializedName("IDBook")
        @Expose
        private Integer iDBook;
        @SerializedName("Rate")
        @Expose
        private Integer rate;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("Create_at")
        @Expose
        private String createAt;
        @SerializedName("BookName")
        @Expose
        private String bookName;


        public Integer getiDFeedBack() {
            return iDFeedBack;
        }

        public void setiDFeedBack(Integer iDFeedBack) {
            this.iDFeedBack = iDFeedBack;
        }

        public Integer getiDUser() {
            return iDUser;
        }

        public void setiDUser(Integer iDUser) {
            this.iDUser = iDUser;
        }

        public Integer getiDBook() {
            return iDBook;
        }

        public void setiDBook(Integer iDBook) {
            this.iDBook = iDBook;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }
    }
}
