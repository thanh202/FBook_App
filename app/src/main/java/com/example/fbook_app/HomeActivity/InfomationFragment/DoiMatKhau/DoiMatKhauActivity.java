package com.example.fbook_app.HomeActivity.InfomationFragment.DoiMatKhau;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Request.UpdatePasswordRequest;
import com.example.fbook_app.Model.Response.UpdatePasswordResponse;
import com.example.fbook_app.Model.Response.UserResponse;
import com.example.fbook_app.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            String currentPassword = edCurrentPassword.getText().toString();
            String newPassword = edCurrentPassword.getText().toString();
            String acceptPassword1 = acceptNewPassword.getText().toString();
            if (!newPassword.equalsIgnoreCase(acceptPassword1)){
                changePassword(currentPassword,newPassword);
            }else {
                tvWarning.setText("Vui lòng nhập đúng mật khẩu mới");
            }

        });
    }

    private void changePassword(String currentPassword, String newPassword) {
        SharedPreferences myToken= getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser= getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        if (token != null && idUser > 0){
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            UpdatePasswordRequest request = new UpdatePasswordRequest(currentPassword,newPassword);
            Call<UpdatePasswordResponse> call = apiService.updatePassword(token,idUser,request);
            call.enqueue(new Callback<UpdatePasswordResponse>() {
                @Override
                public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(DoiMatKhauActivity.this, "Doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                    tvWarning.setText(t.getMessage());
                }
            });
        }
    }
}
