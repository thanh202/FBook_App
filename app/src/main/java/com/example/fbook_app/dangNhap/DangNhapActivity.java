package com.example.fbook_app.dangNhap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.Doi_Mat_khau.ForgotPassword_Activity;
import com.example.fbook_app.HomeActivity.HomeActivity;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.R;

public class DangNhapActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView btnForgotPass, btnLoginHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btnBack = findViewById(R.id.rl_btn_back_login);
        btnForgotPass = findViewById(R.id.btn_forgotPassword_login);
        btnLoginHome = findViewById(R.id.btn_login_home);

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        btnForgotPass.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPassword_Activity.class));
        });
        btnLoginHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DangNhapActivity.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
