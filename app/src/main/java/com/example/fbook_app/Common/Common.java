package com.example.fbook_app.Common;

import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.Model.Response.SearchResponse;

public class Common {

    public static BookResponse.Result currentBook;
    public static SearchResponse.Result currentSearchBook;
    public static ListFavouriteResponse.Result currentFavouriteBook;

    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";

    public static String USER_NAME = "user_name";
    public static String BIRTH_DAY = "birth_day";
}
