package com.example.fbook_app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.DangNhap.DangKyActivity;
import com.example.fbook_app.DangNhap.DangNhapActivity;
import com.example.fbook_app.HomeActivity.HomeActivity;
import com.example.fbook_app.Model.Request.LoginRequest;
import com.example.fbook_app.Model.Response.LoginResponse;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView btnLoginWithGoogle;
    private TextView btnToSignUp;
    private TextView btnToLogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoginWithGoogle = (TextView) findViewById(R.id.btn_login_with_google);
        btnToSignUp = (TextView) findViewById(R.id.btn_to_sign_up_home);
        btnToLogin = (TextView) findViewById(R.id.btn_to_login_home);

        btnToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, DangNhapActivity.class));
        });
        btnToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, DangKyActivity.class));
        });



        Paper.init(this);
        String email = Paper.book().read(Common.USER_KEY);
        String passWord = Paper.book().read(Common.PWD_KEY);

//        if (email != null && passWord != null) {
//            if (!email.isEmpty() && !passWord.isEmpty()) {
//                login(email, passWord);
//            }
//        }
    }

    private void login(String email, String passWord) {
        Handler handler = new Handler();

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Vui Lòng Đợi ...");
        dialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                LoginRequest loginRequest = new LoginRequest(email, passWord);
                Call<LoginResponse> call = apiService.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            //pgLoadLogin.setVisibility(View.INVISIBLE);
                            LoginResponse loginResponse = response.body();
                            if (loginResponse.getStatus()) {
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                //btnLoginHome.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, loginResponse.getStatus().toString(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        //pgLoadLogin.setVisibility(View.INVISIBLE);
                        //btnLoginHome.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        },2000);
    }
}