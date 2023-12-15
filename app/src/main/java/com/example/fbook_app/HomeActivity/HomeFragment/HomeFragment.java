package com.example.fbook_app.HomeActivity.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbook_app.Adapter.NewBookAdapter;
import com.example.fbook_app.Adapter.TheLoaiAdapter;
import com.example.fbook_app.Adapter.TopBookAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.HomeActivity.OrderFragment;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.Model.Request.AddFavouriteRequest;
import com.example.fbook_app.Model.Response.AddFavouriteResponse;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.CategoryResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements FragmentReload {
    private TheLoaiAdapter adapter;
    private TopBookAdapter adapterTopBook;
    private NewBookAdapter adapterNewBook;
    private RecyclerView rclTheLoai;
    private RecyclerView rclTopBook;
    private RecyclerView rclNewBook;
    private SwipeRefreshLayout refresh;

    private View mView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        refresh = mView.findViewById(R.id.refresh);
        rclTheLoai = mView.findViewById(R.id.rcl_book);
        rclTopBook = mView.findViewById(R.id.rcl_topBook);
        rclNewBook = mView.findViewById(R.id.rcl_newBook);
        adapterTopBook = new TopBookAdapter(getContext());
        adapter = new TheLoaiAdapter(getContext());
        adapterNewBook = new NewBookAdapter(getContext());
        getData();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragmentData();
            }
        });

        rclTheLoai.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclTheLoai.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BookResponse.Result book) {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
//            }
//        });

        rclNewBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rclNewBook.setAdapter(adapterNewBook);
        adapterNewBook.setOnItemClickListener(new NewBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookResponse.Result book) {

            }
        });

        adapterNewBook.setOnBuyClickListener(new NewBookAdapter.OnBuyClickListener() {
            @Override
            public void onBuyClick(BookResponse.Result book) {

            }
        });

        adapterNewBook.setOnLikeClickListener(new NewBookAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(int idBook) {
                addFavouriteBook(idBook);
            }
        });

        rclTopBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclTopBook.setAdapter(adapterTopBook);
        return mView;
    }


    private void getData() {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        refresh.setRefreshing(true);
        if (token != null) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            //Get Book
            Call<BookResponse> callBook = apiService.getListBook(token);
            callBook.enqueue(new Callback<BookResponse>() {
                @Override
                public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                    refresh.setRefreshing(false);
                    if (response.isSuccessful()) {
                        BookResponse bookResponse = response.body();
                        if (bookResponse != null) {
                            adapterNewBook.setListBook(bookResponse.getResult());
                            adapterTopBook.setListBook(bookResponse.getResult());
                            adapterNewBook.setOnItemClickListener(new NewBookAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BookResponse.Result book) {
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
                                }
                            });
                            adapterTopBook.setOnItemClickListener(new TopBookAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BookResponse.Result book) {
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
                                }
                            });
                            adapterNewBook.setOnBuyClickListener(new NewBookAdapter.OnBuyClickListener() {
                                @Override
                                public void onBuyClick(BookResponse.Result book) {
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(android.R.id.content, OrderFragment.newInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<BookResponse> call, Throwable t) {
                    refresh.setRefreshing(false);
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //Get Cat
            Call<CategoryResponse> callCat = apiService.getListCat(token);
            callCat.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    refresh.setRefreshing(false);
                    if (response.isSuccessful()) {
                        CategoryResponse catResponse = response.body();
                        if (catResponse != null) {
                            adapter.setCategoryList(catResponse.getResult());
                        }
                    }
                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                    refresh.setRefreshing(false);
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void addFavouriteBook(int idBook) {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            AddFavouriteRequest request = new AddFavouriteRequest(idBook, idUser);
            Call<AddFavouriteResponse> callAddFavourite = apiService.addFavourite(token, request);
            callAddFavourite.enqueue(new Callback<AddFavouriteResponse>() {
                @Override
                public void onResponse(Call<AddFavouriteResponse> call, Response<AddFavouriteResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireActivity(), "Thêm sách vào Favourite thành công", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddFavouriteResponse> call, Throwable t) {
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void reloadFragmentData() {
        getData();
    }
}