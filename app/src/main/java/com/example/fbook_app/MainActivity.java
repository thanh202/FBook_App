package com.example.fbook_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.DangNhap.DangKyActivity;
import com.example.fbook_app.DangNhap.DangNhapActivity;

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
    }
}