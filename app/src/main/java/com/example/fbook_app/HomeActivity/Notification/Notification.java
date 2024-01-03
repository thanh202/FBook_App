package com.example.fbook_app.HomeActivity.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fbook_app.Adapter.BillAdapter;
import com.example.fbook_app.Adapter.NotificationAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.InfomationFragment.LichSuMuaSach.LichSuMuaSach;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.NotificationResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView rclNotifi;
    private RecyclerView.LayoutManager layoutManager;

    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        loadData();
        btnBack = findViewById(R.id.btn_back_thongbao);
        rclNotifi = findViewById(R.id.rcl_thongbao);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclNotifi.setLayoutManager(layoutManager);

        adapter = new NotificationAdapter(this);
        rclNotifi.setAdapter(adapter);
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
            Call<NotificationResponse> call = apiService.getNotification(token, idUser);
            call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    if (response.isSuccessful()) {
                        NotificationResponse notificationResponse = response.body();
                        if (notificationResponse != null) {
                            adapter.setNotiList(notificationResponse.getResult());
                        }
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    Toast.makeText(Notification.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}