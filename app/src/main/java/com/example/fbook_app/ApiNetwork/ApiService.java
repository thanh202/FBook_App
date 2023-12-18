package com.example.fbook_app.ApiNetwork;

import com.example.fbook_app.Model.Request.AddFavouriteRequest;
import com.example.fbook_app.Model.Request.BillRequest;
import com.example.fbook_app.Model.Request.LoginRequest;
import com.example.fbook_app.Model.Request.RegisterRequest;
import com.example.fbook_app.Model.Request.UpdateInformationRequest;
import com.example.fbook_app.Model.Request.UpdatePasswordRequest;
import com.example.fbook_app.Model.Response.AddFavouriteResponse;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.CategoryResponse;
import com.example.fbook_app.Model.Response.DeleteResponse;
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.Model.Response.LoginResponse;
import com.example.fbook_app.Model.Response.RegisterResponse;
import com.example.fbook_app.Model.Response.SearchResponse;
import com.example.fbook_app.Model.Response.UpdateInformationResponse;
import com.example.fbook_app.Model.Response.UpdatePasswordResponse;
import com.example.fbook_app.Model.Response.UserResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface ApiService {
    @GET("book/get_list")
    Call<BookResponse> getListBook(@Header("Authorization") String token);
    @GET("Category/get_list")
    Call<CategoryResponse> getListCat(@Header("Authorization") String token);
    @POST("acount/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("acount/add")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
    @POST("Favorite/add")
    Call<AddFavouriteResponse> addFavourite(@Header("Authorization") String token, @Body AddFavouriteRequest request);
    @POST("bill/add")
    Call<BillResponse> addBill(@Header("Authorization") String token, @Body BillRequest request);
    @GET("bill/get_bill_iduser/{IDUser}")
    Call<BillResponse> getBill(@Header("Authorization") String token, @Path("IDUser") int idUser);
    @PUT("user/updateprofile/{IDUser}")
    Call<UpdateInformationResponse> updateUser(@Header("Authorization") String token, @Path("IDUser") int idUser, @Body UpdateInformationRequest request);
    @PUT("user/change_password/{IDUser}")
    Call<UpdatePasswordResponse> updatePassword(@Header("Authorization") String token, @Path("IDUser") int idUser, @Body UpdatePasswordRequest request);
    @GET("Favorite/byiduser/{IDUser}")
    Call<ListFavouriteResponse> getListFavourite(@Header("Authorization") String token, @Path("IDUser") int idUser);
    @GET("user/get_list/{IDUser}")
    Call<UserResponse> getUser(@Header("Authorization") String token, @Path("IDUser") int idUser);
    @GET("/book/searchap/{query}" )
    Call<SearchResponse> getSearch(@Header("Authorization") String token,@Path("query") String query);
    @GET("bill/getbookpaid/{IDUser}")
    Call<LibraryResponse> getLibrary(@Header("Authorization") String token, @Path("IDUser") int idUser);
    @DELETE("Favorite/delete/{IDFavorite}/{IDUser}")
    Call<DeleteResponse> deleteFavourite(@Header("Authorization") String token, @Path("IDFavorite") int idFavourite, @Path("IDUser") int idUser);
}
