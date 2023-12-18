package com.example.fbook_app.HomeActivity.SearchFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbook_app.Adapter.SearchBookAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietSearchBookFragment;
import com.example.fbook_app.Model.Request.AddFavouriteRequest;
import com.example.fbook_app.Model.Response.AddFavouriteResponse;
import com.example.fbook_app.Model.Response.SearchResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    public static SearchFragment newInstance(SearchResponse book, String text) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable("search_book", book);
        args.putString("title_search", text );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView tvResultSearch;
    private RecyclerView rclSearchBook;
    private ImageView btnBack;
    private SearchBookAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_search, container, false);
        setData(mView);
        return mView;
    }

    private void setData(View mView) {
        tvResultSearch = (TextView) mView.findViewById(R.id.tv_result_search);
        rclSearchBook = (RecyclerView) mView.findViewById(R.id.rcl_searchBook);
        btnBack = mView.findViewById(R.id.btn_back_search);
        adapter = new SearchBookAdapter(requireContext());

        rclSearchBook.setAdapter(adapter);
        rclSearchBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        SearchResponse mBook = (SearchResponse) getArguments().get("search_book");
        String text = getArguments().getString("title_search");
        tvResultSearch.setText(text);
        adapter.setListBook(mBook.getResult());
        adapter.setOnLikeClickListener(new SearchBookAdapter.OnLikeClickListener() {
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
                        Toast.makeText(getContext(), "Đã Thêm Sách Vào Yêu Thích !", Toast.LENGTH_SHORT).show();
                    }
                },2000);

            }
        });
        adapter.setOnItemClickListener(new SearchBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchResponse.Result book) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietSearchBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();

                    }
                },2000);
                     }
        });
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
}
