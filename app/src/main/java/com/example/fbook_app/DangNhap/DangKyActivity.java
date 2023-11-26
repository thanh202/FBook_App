package com.example.fbook_app.DangNhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.MainActivity;
import com.example.fbook_app.R;

public class DangKyActivity extends AppCompatActivity {
    private ImageView btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        btnBack = findViewById(R.id.rl_btn_back_signUp);
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
