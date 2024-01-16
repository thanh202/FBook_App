package com.example.fbook_app.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatingTbResponse {
        @SerializedName("success")
        @Expose
        private String Success;
        @SerializedName("averageRating")
        @Expose
        private float AverageRating;

        public String getSuccess() {
            return Success;
        }

        public void setSuccess(String success) {
            Success = success;
        }

        public float getAverageRating() {
            return AverageRating;
        }

        public void setAverageRating(float averageRating) {
            AverageRating = averageRating;
        }
    }

