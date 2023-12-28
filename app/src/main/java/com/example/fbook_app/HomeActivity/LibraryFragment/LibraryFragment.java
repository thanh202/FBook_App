package com.example.fbook_app.HomeActivity.LibraryFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbook_app.Adapter.LibraryAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.HomeActivity.LibraryFragment.DocSach.DocSachFragment;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibraryFragment extends Fragment implements FragmentReload {

    private SwipeRefreshLayout refreshLibrary;
    private TextView tvLibrary;
    private LibraryAdapter adapter;
    private RecyclerView rclListLibraryBook;
    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_library, container, false);
        refreshLibrary = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_library);
        tvLibrary = (TextView) mView.findViewById(R.id.tvLibrary);
        rclListLibraryBook = (RecyclerView) mView.findViewById(R.id.rcl_list_library_book);
        getDataLibrary();
        adapter = new LibraryAdapter(requireActivity());
        rclListLibraryBook.setAdapter(adapter);
        rclListLibraryBook.setLayoutManager(new GridLayoutManager(getContext(),2));
        refreshLibrary.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragmentData();
            }
        });
        adapter.setOnReadClickListener(new LibraryAdapter.OnReadClickListener() {
            @Override
            public void onItemClick(LibraryResponse.Result book) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(android.R.id.content, DocSachFragment.newInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
                    }
                },2000);
                 }
        });
        return mView;
    }
    private void getDataLibrary() {
        SharedPreferences myToken= requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser= requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        refreshLibrary.setRefreshing(true);
        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<LibraryResponse> call = apiService.getLibrary(token,idUser);
            call.enqueue(new Callback<LibraryResponse>() {
                @Override
                public void onResponse(Call<LibraryResponse> call, Response<LibraryResponse> response) {
                    refreshLibrary.setRefreshing(false);
                    if (response.isSuccessful()){
                        LibraryResponse libraryResponse = response.body();
                        if (libraryResponse != null){
                            showRecycle(libraryResponse);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LibraryResponse> call, Throwable t) {
                    refreshLibrary.setRefreshing(false);
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void showRecycle(LibraryResponse libraryResponse) {
        adapter.setListBook(libraryResponse.getResult());
        if (libraryResponse.getResult().size() <= 0) {
            tvLibrary.setVisibility(View.VISIBLE);
        } else {
            tvLibrary.setVisibility(View.GONE);
        }
    }
    @Override
    public void reloadFragmentData() {
        getDataLibrary();
    }
}