package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public static class Result implements Serializable {
        @SerializedName("IDCat")
        @Expose
        private Integer iDCat;
        @SerializedName("CatName")
        @Expose
        private String catName;
        @SerializedName("img")
        @Expose
        private String imgCat;

        public Integer getiDCat() {
            return iDCat;
        }

        public void setiDCat(Integer iDCat) {
            this.iDCat = iDCat;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getImgCat() {
            return imgCat;
        }

        public void setImgCat(String imgCat) {
            this.imgCat = imgCat;
        }
    }

}
