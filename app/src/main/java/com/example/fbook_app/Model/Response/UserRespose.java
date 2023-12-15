package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserRespose {
    @SerializedName("result")
    @Expose
    private List<UserRespose.Result> result;

    public List<UserRespose.Result> getResult() {
        return result;
    }

    public void setResult(List<UserRespose.Result> result) {
        this.result = result;
    }
    public static class Result implements Serializable {
        @SerializedName("IDUser")
        @Expose
        private Integer iDUser;

        @SerializedName("UserName")
        @Expose
        private String userName;

        @SerializedName("Email")
        @Expose
        private String email;

        @SerializedName("Birthday")
        @Expose
        private String birthDay;

        @SerializedName("Phone")
        @Expose
        private String phone;

        public Integer getiDUser() {
            return iDUser;
        }

        public void setiDUser(Integer iDUser) {
            this.iDUser = iDUser;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
