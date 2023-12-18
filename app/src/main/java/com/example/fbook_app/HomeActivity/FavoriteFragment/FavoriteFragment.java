package com.example.fbook_app.HomeActivity.FavoriteFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbook_app.Adapter.FavoriteBookAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietFavouriteBookFragment;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.Model.Response.DeleteResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteFragment extends Fragment implements FragmentReload {
    private RecyclerView rclListFavorite;
    private FavoriteBookAdapter adapter;
    private SwipeRefreshLayout refreshFavourite;
    private TextView tvListFavouriteNotFound;
    private View mView;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);
        rclListFavorite = mView.findViewById(R.id.rcl_list_favorite_book);
        adapter = new FavoriteBookAdapter(getContext());
        refreshFavourite = mView.findViewById(R.id.refresh_favourite);
        tvListFavouriteNotFound = mView.findViewById(R.id.tvListFavouriteNotFound);
        getDataFavouriteBook();
        adapter.setOnUnFavouriteClickListener(new FavoriteBookAdapter.OnUnFavouriteClickListener() {
            @Override
            public void onUnFavouriteClick(int idFavourite) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        unFavourite(idFavourite);
                    }
                },2000);

            }
        });
        refreshFavourite.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragmentData();
            }
        });
        rclListFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rclListFavorite.setAdapter(adapter);
        return mView;
    }

    private void unFavourite(int idFavourite) {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        refreshFavourite.setRefreshing(true);
        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<DeleteResponse> call = apiService.deleteFavourite(token, idFavourite, idUser);
            call.enqueue(new Callback<DeleteResponse>() {
                @Override
                public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                    refreshFavourite.setRefreshing(false);
                    if (response.isSuccessful()) {
                        Toast.makeText(requireActivity(), "un Favourite thành công", Toast.LENGTH_SHORT).show();
                        refreshData();
                    }
                }

                @Override
                public void onFailure(Call<DeleteResponse> call, Throwable t) {
                    refreshFavourite.setRefreshing(false);
                    Log.e("zzzzzz", "onFailure: " + t.getMessage());
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshData() {
        getDataFavouriteBook();
    }

    public void getDataFavouriteBook() {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        refreshFavourite.setRefreshing(true);
        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<ListFavouriteResponse> call = apiService.getListFavourite(token, idUser);
            call.enqueue(new Callback<ListFavouriteResponse>() {
                @Override
                public void onResponse(Call<ListFavouriteResponse> call, Response<ListFavouriteResponse> response) {
                    refreshFavourite.setRefreshing(false);
                    if (response.isSuccessful()) {
                        ListFavouriteResponse favouriteResponse = response.body();
                        if (favouriteResponse != null) {
                            showRecycle(favouriteResponse);
                            adapter.setOnItemClickListener(new FavoriteBookAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListFavouriteResponse.Result book) {
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietFavouriteBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListFavouriteResponse> call, Throwable t) {
                    refreshFavourite.setRefreshing(false);
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showRecycle(ListFavouriteResponse listFavouriteResponse) {
        adapter.setListBook(listFavouriteResponse.getResult());
        if (listFavouriteResponse.getResult().size() <= 0) {
            tvListFavouriteNotFound.setVisibility(View.VISIBLE);
        } else {
            tvListFavouriteNotFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void reloadFragmentData() {
        getDataFavouriteBook();
    }
}