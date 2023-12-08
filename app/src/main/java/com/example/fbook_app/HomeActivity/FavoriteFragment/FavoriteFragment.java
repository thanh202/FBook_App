package com.example.fbook_app.HomeActivity.FavoriteFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbook_app.Adapter.FavoriteBookAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteFragment extends Fragment {
    private RecyclerView rclListFavorite;
    private FavoriteBookAdapter adapter;
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
        getDataFavouriteBook();
        rclListFavorite.setLayoutManager(new GridLayoutManager(getContext(),2));
        rclListFavorite.setAdapter(adapter);
//        adapter.setOnItemClickListener(new FavoriteBookAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Book book) {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
//            }
//        });

        return mView;
    }
    private void getDataFavouriteBook(){
        SharedPreferences myToken= requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser= requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        if (token != null && idUser > 0){
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<ListFavouriteResponse> call = apiService.getListFavourite(token,idUser);
            call.enqueue(new Callback<ListFavouriteResponse>() {
                @Override
                public void onResponse(Call<ListFavouriteResponse> call, Response<ListFavouriteResponse> response) {
                    if (response.isSuccessful()){
                        ListFavouriteResponse favouriteResponse = response.body();
                        if (favouriteResponse != null){
                            adapter.setListBook(favouriteResponse.getResult());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListFavouriteResponse> call, Throwable t) {
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}