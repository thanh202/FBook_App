package com.example.fbook_app.ApiNetwork;

import com.example.fbook_app.Model.Request.LoginRequest;
import com.example.fbook_app.Model.Request.RegisterRequest;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.CategoryResponse;
import com.example.fbook_app.Model.Response.LoginResponse;
import com.example.fbook_app.Model.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @GET("book/get_list")
    Call<BookResponse> getListBook(@Header("Authorization") String token);
    @GET("Category/get_list")
    Call<CategoryResponse> getListCat(@Header("Authorization") String token);
    @POST("acount/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("acount/add")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
}
