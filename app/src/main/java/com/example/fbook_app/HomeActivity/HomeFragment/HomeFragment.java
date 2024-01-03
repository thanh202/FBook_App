package com.example.fbook_app.HomeActivity.HomeFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.fbook_app.Adapter.NewBookAdapter;
import com.example.fbook_app.Adapter.TheLoaiAdapter;
import com.example.fbook_app.Adapter.TopBookAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.HomeActivity.Notification.Notification;
import com.example.fbook_app.HomeActivity.SearchFragment.SearchFragment;
import com.example.fbook_app.HomeActivity.ThanhToanActivity;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.Model.Request.AddFavouriteRequest;
import com.example.fbook_app.Model.Response.AddFavouriteResponse;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.CategoryResponse;
import com.example.fbook_app.Model.Response.SearchResponse;
import com.example.fbook_app.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
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
    private MaterialSearchBar search;
    private SwipeRefreshLayout refresh;
    private CardView btnThongBao;
    private List<String> suggestList = new ArrayList<>();


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
        search = mView.findViewById(R.id.search);
        btnThongBao=mView.findViewById(R.id.btn_thongbao);
        adapterTopBook = new TopBookAdapter(getContext());
        adapter = new TheLoaiAdapter(getContext());
        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Notification.class);
                startActivity(intent);
            }
        });
        adapterNewBook = new NewBookAdapter(getContext());
        getData();
        adapter.setOnItemClickListener(new TheLoaiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String nameCategory) {
                search(nameCategory);
            }
        });
        Paper.init(getActivity());

        search.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                search(text.toString());
                hideKeyboard();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragmentData();
            }
        });

        rclTheLoai.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclTheLoai.setAdapter(adapter);


        rclNewBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rclNewBook.setAdapter(adapterNewBook);


        adapterNewBook.setOnLikeClickListener(new NewBookAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(int idBook) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        addFavouriteBook(idBook);
                    }
                },2000);

            }
        });

        rclTopBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclTopBook.setAdapter(adapterTopBook);
        return mView;
    }

    private void search(String text) {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        if (token != null) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            //Get Book
            Call<SearchResponse> call = apiService.getSearch(token, text);
            call.enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    if (response.isSuccessful()) {
                        SearchResponse searchResponse = response.body();
                        Handler handler = new Handler();
                        ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Vui Lòng Đợi ...");
                        dialog.show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(android.R.id.content, SearchFragment.newInstance(searchResponse, text)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();

                            }
                        },2000);
                      }
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("zzzz", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void hideKeyboard() {
        // Lấy đối tượng InputMethodManager từ hệ thống
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // Ẩn bàn phím
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
                                    Handler handler = new Handler();
                                    ProgressDialog dialog = new ProgressDialog(getContext());
                                    dialog.setMessage("Vui Lòng Đợi ...");
                                    dialog.show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                            fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();

                                        }
                                    },2000);
                                     }
                            });
                            adapterTopBook.setOnItemClickListener(new TopBookAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BookResponse.Result book) {
                                    Handler handler = new Handler();
                                    ProgressDialog dialog = new ProgressDialog(getContext());
                                    dialog.setMessage("Vui Lòng Đợi ...");
                                    dialog.show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                            fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();

                                        }
                                    },2000);
                                        }
                            });
                            adapterNewBook.setOnBuyClickListener(new NewBookAdapter.OnBuyClickListener() {
                                @Override
                                public void onBuyClick(BookResponse.Result book) {


                                    Common.currentBook = book;

                                    Handler handler = new Handler();
                                    ProgressDialog dialog = new ProgressDialog(getContext());
                                    dialog.setMessage("Vui Lòng Đợi ...");
                                    dialog.show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            Intent intent = new Intent(getContext(), ThanhToanActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 2000);
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
                        Toast.makeText(requireActivity(), "Thêm sách vào yêu thích thành công !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(requireActivity(), "Sách đã có sẵn trong yêu thích !", Toast.LENGTH_SHORT).show();
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