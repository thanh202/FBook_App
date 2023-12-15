package com.example.fbook_app.HomeActivity.InfomationFragment.ChinhSuaThongTinActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.DangNhap.DangNhapActivity;
import com.example.fbook_app.Model.Response.DeleteResponse;
import com.example.fbook_app.Model.Response.UserRespose;
import com.example.fbook_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaThongTinActivity extends AppCompatActivity {
    private CardView btnBack, btnLuuThongTin;
    private EditText edt_nameUser, edt_email, edt_sdt, edt_birthday;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin);
        btnBack = findViewById(R.id.back_chinhsuathongtin);
        edt_nameUser = findViewById(R.id.nameUser_chinhsuathongtin);
        edt_email = findViewById(R.id.email_chinhsuathongtin);
        edt_sdt = findViewById(R.id.sdt_chinhsuathongtin);
        edt_birthday = findViewById(R.id.bthday_chinhsuathongtin);
        btnLuuThongTin = findViewById(R.id.btn_luuthongtin);

        SharedPreferences myNameUser = getSharedPreferences("MyNameUser", Context.MODE_PRIVATE);
        String nameUser1 = myNameUser.getString("nameUser", null);
        edt_nameUser.setText(nameUser1);

        SharedPreferences myEmail = getSharedPreferences("MyEmail", Context.MODE_PRIVATE);
        String email = myEmail.getString("email", null);
        edt_email.setText(email);

        SharedPreferences myBirthDay = getSharedPreferences("MyBirthDay", Context.MODE_PRIVATE);
        String birthday = myBirthDay.getString("birthday", null);
        edt_birthday.setText(birthday);

        SharedPreferences mySDT = getSharedPreferences("MySDT", Context.MODE_PRIVATE);
        String sdt = mySDT.getString("sdt", null);
        edt_sdt.setText(sdt);


        btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(ChinhSuaThongTinActivity.this);
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateUser();
                    }
                }, 2000);
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateUser() {
        SharedPreferences myToken = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        if (token != null && idUser > 0){
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<UserRespose> call = apiService.updateUser(token,idUser);
            call.enqueue(new Callback<UserRespose>() {
                @Override
                public void onResponse(Call<UserRespose> call, Response<UserRespose> response) {
                    if (response.isSuccessful()){

                        Toast.makeText(ChinhSuaThongTinActivity.this, "Update User thành công", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ChinhSuaThongTinActivity.this, DangNhapActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }

                @Override
                public void onFailure(Call<UserRespose> call, Throwable t) {
                    Log.e("zzzzzz", "onFailure: " + t.getMessage() );
                    Toast.makeText(ChinhSuaThongTinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}