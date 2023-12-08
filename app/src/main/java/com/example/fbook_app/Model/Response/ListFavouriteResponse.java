package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListFavouriteResponse {
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
        @SerializedName("IDFavorite")
        @Expose
        private Integer iDFavorite;
        @SerializedName("IDUser")
        @Expose
        private Integer iDUser;
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
        @SerializedName("Create_at")
        @Expose
        private String createAt;
        @SerializedName("Chapter")
        @Expose
        private Integer chapter;
        @SerializedName("IDCat")
        @Expose
        private Integer iDCat;

        public Integer getIDFavorite() {
            return iDFavorite;
        }

        public void setIDFavorite(Integer iDFavorite) {
            this.iDFavorite = iDFavorite;
        }

        public Integer getIDUser() {
            return iDUser;
        }

        public void setIDUser(Integer iDUser) {
            this.iDUser = iDUser;
        }

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

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public Integer getChapter() {
            return chapter;
        }

        public void setChapter(Integer chapter) {
            this.chapter = chapter;
        }

        public Integer getIDCat() {
            return iDCat;
        }

        public void setIDCat(Integer iDCat) {
            this.iDCat = iDCat;
        }
    }
}
