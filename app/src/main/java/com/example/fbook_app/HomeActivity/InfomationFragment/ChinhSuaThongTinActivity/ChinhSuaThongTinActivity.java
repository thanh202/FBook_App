package com.example.fbook_app.HomeActivity.InfomationFragment.ChinhSuaThongTinActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.HomeActivity.InfomationFragment.InfomationFragment;
import com.example.fbook_app.Model.Request.UpdateInformationRequest;
import com.example.fbook_app.Model.Response.UpdateInformationResponse;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaThongTinActivity extends AppCompatActivity {
    private CardView btnBack, btnLuuThongTin;
    private EditText edt_nameUser, edt_birthday;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin);
        btnBack = findViewById(R.id.back_chinhsuathongtin);
        edt_nameUser = findViewById(R.id.nameUser_chinhsuathongtin);
        edt_birthday = findViewById(R.id.bthday_chinhsuathongtin);
        btnLuuThongTin = findViewById(R.id.btn_luuthongtin);
        edt_nameUser.setText(Common.USER_NAME);
        edt_birthday.setText(Common.BIRTH_DAY);
        btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(edt_nameUser.getText().toString(), edt_birthday.getText().toString());
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateUser(String userName, String birthDay) {
        SharedPreferences myToken = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            UpdateInformationRequest request = new UpdateInformationRequest(userName, birthDay);
            Call<UpdateInformationResponse> call = apiService.updateUser(token, idUser, request);
            call.enqueue(new Callback<UpdateInformationResponse>() {
                @Override
                public void onResponse(Call<UpdateInformationResponse> call, Response<UpdateInformationResponse> response) {
                    if (response.isSuccessful()) {
                        UpdateInformationResponse informationResponse = response.body();
                        Toast.makeText(ChinhSuaThongTinActivity.this, informationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdateInformationResponse> call, Throwable t) {
                    Toast.makeText(ChinhSuaThongTinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}