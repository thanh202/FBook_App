package com.example.fbook_app.HomeActivity.HomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbook_app.Adapter.BookAdapter;
import com.example.fbook_app.Adapter.NewBookAdapter;
import com.example.fbook_app.Adapter.TopBookAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private BookAdapter adapter;
    private TopBookAdapter adapterTopBook;
    private NewBookAdapter adapterNewBook;
    private RecyclerView rclBook;
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
        rclBook = mView.findViewById(R.id.rcl_book);
        rclTopBook = mView.findViewById(R.id.rcl_topBook);
        rclNewBook = mView.findViewById(R.id.rcl_newBook);
        adapterTopBook = new TopBookAdapter(getContext());
        adapter = new BookAdapter(getContext());
        adapterNewBook = new NewBookAdapter(getContext());
        getListBook();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        rclBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclBook.setAdapter(adapter);
        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookResponse.Result book) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
            }
        });

        rclNewBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rclNewBook.setAdapter(adapterNewBook);
        adapterNewBook.setOnItemClickListener(new NewBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookResponse.Result book) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
            }
        });
        rclTopBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclTopBook.setAdapter(adapterTopBook);
        adapterTopBook.setOnItemClickListener(new TopBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookResponse.Result book) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
            }
        });
        return mView;
    }

    private void refreshData() {
        getListBook();
    }

    private void getListBook() {
        refresh.setRefreshing(true);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<BookResponse> call = apiService.getListBook();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                refresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    BookResponse bookResponse = response.body();
                    if (bookResponse != null) {
                        adapter.setListBook(bookResponse.getResult());
                        adapterNewBook.setListBook(bookResponse.getResult());
                        adapterTopBook.setListBook(bookResponse.getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                refresh.setRefreshing(false);
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}