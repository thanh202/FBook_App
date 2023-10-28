package com.example.fbook_app.dangNhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.Doi_Mat_khau.ForgotPassword_Activity;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.R;

public class DangNhapActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView btnForgotPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btnBack = findViewById(R.id.rl_btn_back_login);
        btnForgotPass = findViewById(R.id.btn_forgotPassword_login);

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        btnForgotPass.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPassword_Activity.class));
        });
    }
}
