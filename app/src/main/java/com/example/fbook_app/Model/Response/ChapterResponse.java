package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public static class Result{

        @SerializedName("IDchuong")
        @Expose
        private Integer iDchuong;
        @SerializedName("IDBook")
        @Expose
        private Integer iDBook;
        @SerializedName("chuongso")
        @Expose
        private Integer chuongso;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("Create_at")
        @Expose
        private String createAt;
        @SerializedName("BookName")
        @Expose
        private String bookName;
        @SerializedName("Author")
        @Expose
        private String author;
        @SerializedName("PublishYear")
        @Expose
        private String publishYear;

        public Integer getIDchuong() {
            return iDchuong;
        }

        public void setIDchuong(Integer iDchuong) {
            this.iDchuong = iDchuong;
        }

        public Integer getIDBook() {
            return iDBook;
        }

        public void setIDBook(Integer iDBook) {
            this.iDBook = iDBook;
        }

        public Integer getChuongso() {
            return chuongso;
        }

        public void setChuongso(Integer chuongso) {
            this.chuongso = chuongso;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublishYear() {
            return publishYear;
        }

        public void setPublishYear(String publishYear) {
            this.publishYear = publishYear;
        }
    }
}
