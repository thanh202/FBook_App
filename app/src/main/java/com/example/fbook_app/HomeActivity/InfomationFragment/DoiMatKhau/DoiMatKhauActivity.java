package com.example.fbook_app.HomeActivity.InfomationFragment.DoiMatKhau;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.R;
import com.google.android.material.textfield.TextInputEditText;

public class DoiMatKhauActivity extends AppCompatActivity {
    private ImageView imgBack;

    private TextInputEditText edCurrentPassword;
    private TextInputEditText newPassword;
    private TextInputEditText acceptNewPassword;
    private TextView tvWarning;
    private Button btnUpdatePassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        imgBack = (ImageView) findViewById(R.id.img_back);
        edCurrentPassword = (TextInputEditText) findViewById(R.id.ed_current_password);
        newPassword = (TextInputEditText) findViewById(R.id.new_password);
        acceptNewPassword = (TextInputEditText) findViewById(R.id.accept_new_password);
        tvWarning = (TextView) findViewById(R.id.tv_warning);
        btnUpdatePassword = (Button) findViewById(R.id.btn_update_password);

        imgBack.setOnClickListener(v -> {
            finish();
        });

        btnUpdatePassword.setOnClickListener(v -> {
            changePassword();
        });
    }

    private void changePassword() {

    }
}
