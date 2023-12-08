package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;}

    public static class Result{
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("message")
        @Expose
        private String message;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    public static class User{
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
        private String birthday;
        @SerializedName("Phone")
        @Expose
        private String phone;

        public Integer getIDUser() {
            return iDUser;
        }

        public void setIDUser(Integer iDUser) {
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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
