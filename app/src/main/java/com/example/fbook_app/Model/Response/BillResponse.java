package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BillResponse {
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
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("IDBook")
        @Expose
        private int iDBook;
        @SerializedName("IDUser")
        @Expose
        private int iDUser;
        @SerializedName("IDBill")
        @Expose
        private int iDBill;
        @SerializedName("PriceTotal")
        @Expose
        private Integer priceTotal;
        @SerializedName("Create_at")
        @Expose
        private String create_at;

        public int getiDBill() {
            return iDBill;
        }

        public void setiDBill(int iDBill) {
            this.iDBill = iDBill;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getiDBook() {
            return iDBook;
        }

        public void setiDBook(int iDBook) {
            this.iDBook = iDBook;
        }

        public int getiDUser() {
            return iDUser;
        }

        public void setiDUser(int iDUser) {
            this.iDUser = iDUser;
        }

        public Integer getPriceTotal() {
            return priceTotal;
        }

        public void setPriceTotal(Integer priceTotal) {
            this.priceTotal = priceTotal;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }
    }


}
