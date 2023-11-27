package com.example.fbook_app.DangNhap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.Doi_Mat_khau.ForgotPassword_Activity;
import com.example.fbook_app.HomeActivity.HomeActivity;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.Model.Request.LoginRequest;
import com.example.fbook_app.Model.Response.LoginResponse;
import com.example.fbook_app.R;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView btnForgotPass, btnLoginHome;
    private EditText edtEmailLogin, edtPassWordLogin;
    private ProgressBar pgLoadLogin;
    private CheckBox cbRemember;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btnBack = findViewById(R.id.rl_btn_back_login);
        btnForgotPass = findViewById(R.id.btn_forgotPassword_login);
        btnLoginHome = findViewById(R.id.btn_login_home);
        edtEmailLogin = findViewById(R.id.edt_email_login);
        edtPassWordLogin = findViewById(R.id.edt_password_login);
        cbRemember = findViewById(R.id.cb_remember_pass);
        pgLoadLogin = findViewById(R.id.pg_load_login);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        Paper.init(this);

        Intent intentDangKy = getIntent();
        if (intentDangKy.hasExtra("email_signup") && intentDangKy.hasExtra("password_signup")){
            String emailSignup = intentDangKy.getStringExtra("email_signup");
            String passwordSignup = intentDangKy.getStringExtra("password");
            edtEmailLogin.setText(emailSignup);
            edtPassWordLogin.setText(passwordSignup);
        }

        // Load saved login information if available
        if (sharedPreferences.contains("email")) {
            String savedEmail = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "");

            edtEmailLogin.setText(savedEmail);
            edtPassWordLogin.setText(savedPassword);
            cbRemember.setChecked(true);
        }
        btnBack.setOnClickListener(v -> {
            finish();
        });
        btnForgotPass.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPassword_Activity.class));
        });
        btnLoginHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmailLogin.getText().toString();
                String passWord = edtPassWordLogin.getText().toString();
                if (cbRemember.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", passWord);
                    Paper.book().write(Common.USER_KEY, email);
                    Paper.book().write(Common.PWD_KEY, passWord);
                    editor.apply();
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("email");
                    editor.remove("password");
                    Paper.book().destroy();
                    editor.apply();
                }

                login(email, passWord);
            }
        });
    }

    private void login(String email, String passWord) {
        pgLoadLogin.setVisibility(View.VISIBLE);
        btnLoginHome.setVisibility(View.INVISIBLE);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, passWord);
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    pgLoadLogin.setVisibility(View.INVISIBLE);
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus()) {
                        SharedPreferences.Editor editor = getSharedPreferences("MyToken", MODE_PRIVATE).edit();
                        editor.putString("token", loginResponse.getResult());
                        editor.apply();
                        Intent intent = new Intent(DangNhapActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        btnLoginHome.setVisibility(View.VISIBLE);
                        Toast.makeText(DangNhapActivity.this, loginResponse.getStatus().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pgLoadLogin.setVisibility(View.INVISIBLE);
                btnLoginHome.setVisibility(View.VISIBLE);
                Toast.makeText(DangNhapActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
