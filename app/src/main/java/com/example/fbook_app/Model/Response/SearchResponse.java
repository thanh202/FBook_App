package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchResponse implements Serializable{
    @SerializedName("result")
    @Expose
    private List<SearchResponse.Result> result;

    public List<SearchResponse.Result> getResult() {
        return result;
    }

    public void setResult(List<SearchResponse.Result> result) {
        this.result = result;
    }

    public static class Result implements Serializable {
        @SerializedName("IDBook")
        @Expose
        private Integer iDBook;
        @SerializedName("BookName")
        @Expose
        private String bookName;
        @SerializedName("Author")
        @Expose
        private String author;
        @SerializedName("PublishYear")
        @Expose
        private String publishYear;
        @SerializedName("PriceBook")
        @Expose
        private Integer priceBook;
        @SerializedName("Discription")
        @Expose
        private String discription;
        @SerializedName("ImageBook")
        @Expose
        private String imageBook;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("Chapter")
        @Expose
        private String chapter;
        @SerializedName("IDCat")
        @Expose
        private Integer iDCat;
        @SerializedName("CatName")
        @Expose
        private String catName;

        public Integer getIDBook() {
            return iDBook;
        }

        public void setIDBook(Integer iDBook) {
            this.iDBook = iDBook;
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

        public Integer getPriceBook() {
            return priceBook;
        }

        public void setPriceBook(Integer priceBook) {
            this.priceBook = priceBook;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getImageBook() {
            return imageBook;
        }

        public void setImageBook(String imageBook) {
            this.imageBook = imageBook;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getChapter() {
            return chapter;
        }

        public void setChapter(String chapter) {
            this.chapter = chapter;
        }

        public Integer getIDCat() {
            return iDCat;
        }

        public void setIDCat(Integer iDCat) {
            this.iDCat = iDCat;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }
    }
}
