package com.example.fbook_app.HomeActivity.InfomationFragment.LichSuMuaSach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fbook_app.Adapter.BillAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuMuaSach extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView rclBill;
    private RecyclerView.LayoutManager layoutManager;

    private BillAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_sach);

        btnBack = findViewById(R.id.btn_back_liachsumuahang);
        rclBill = findViewById(R.id.rcl_lichsumuahang);
        loadData();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclBill.setLayoutManager(layoutManager);
        adapter = new BillAdapter(this);

        rclBill.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void loadData() {
        SharedPreferences myToken = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<BillResponse> call = apiService.getBill(token, idUser);
            call.enqueue(new Callback<BillResponse>() {
                @Override
                public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                    if (response.isSuccessful()) {
                        BillResponse billResponse = response.body();
                        if (billResponse != null) {
                            adapter.setBillList(billResponse.getResult());
                        }
                    }
                }

                @Override
                public void onFailure(Call<BillResponse> call, Throwable t) {
                    Toast.makeText(LichSuMuaSach.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}