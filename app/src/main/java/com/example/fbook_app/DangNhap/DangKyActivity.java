package com.example.fbook_app.DangNhap;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.Model.Request.RegisterRequest;
import com.example.fbook_app.Model.Response.RegisterResponse;
import com.example.fbook_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {
    private ImageView btnBack;
    private EditText edtEmailSignUp;
    private EditText edtUserNameSignUp;
    private EditText edtPhoneSignUp;
    private EditText edtPasswordSignUp, edtRePasswordSignUp;
    private TextView btnSignUp, edtBirthDaySignUp;
    private Calendar calendar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        btnBack = findViewById(R.id.rl_btn_back_signUp);
        edtEmailSignUp = (EditText) findViewById(R.id.edt_email_signUp);
        edtUserNameSignUp = (EditText) findViewById(R.id.edt_userName_signUp);
        edtPhoneSignUp = (EditText) findViewById(R.id.edt_phone_signUp);
        edtBirthDaySignUp = findViewById(R.id.edt_birthDay_signUp);
        edtPasswordSignUp = (EditText) findViewById(R.id.edt_password_signUp);
        btnSignUp = (TextView) findViewById(R.id.btn_signUp);
        edtRePasswordSignUp = findViewById(R.id.edt_repassword_signUp);
        calendar = Calendar.getInstance();

        edtBirthDaySignUp.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // Ẩn bàn phím cho một View cụ thể
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            showDateDialog();
        });
        String phonePattern = "^0[0-9]{9}$"; // Một biểu thức chính quy cho số điện thoại có 10 kí tự và bắt đầu bằng số 0

        btnSignUp.setOnClickListener(v -> {
            String email = edtEmailSignUp.getText().toString();
            String userName = edtUserNameSignUp.getText().toString();
            String phone = edtPhoneSignUp.getText().toString();
            String birthDay = edtBirthDaySignUp.getText().toString();
            String passWord = edtPasswordSignUp.getText().toString();
            if (email.equals("")) {
                Toast.makeText(this, "Vui Lòng Nhập Email !", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email Không Hợp Lệ !", Toast.LENGTH_SHORT).show();
            } else if (userName.equals("")) {
                Toast.makeText(this, "Vui Lòng Nhập Họ Tên !", Toast.LENGTH_SHORT).show();
            } else if (phone.equals("")) {
                Toast.makeText(this, "Vui Lòng Nhập Số Điện Thoại !", Toast.LENGTH_SHORT).show();
            } else if (phone.matches(phonePattern)) {
                Toast.makeText(this, "Vui Lòng Nhập Đúng Số Điện Thoại !", Toast.LENGTH_SHORT).show();
                edtPhoneSignUp.setText("");
            } else if (birthDay.equals("")) {
                Toast.makeText(this, "Vui Lòng Nhập Ngày Sinh !", Toast.LENGTH_SHORT).show();
            } else if (passWord.equals("")) {
                Toast.makeText(this, "Vui Lòng Nhập PassWord !", Toast.LENGTH_SHORT).show();
            } else if (!passWord.equals(edtRePasswordSignUp.getText().toString())) {
                Toast.makeText(this, "Mật Khẩu Nhập Lại Không Khớp !", Toast.LENGTH_SHORT).show();
                edtRePasswordSignUp.setText("");
            } else if (passWord.length() < 6) {
                Toast.makeText(this, "Vui Lòng Nhập PassWord lớn hơn 6 kí tự !", Toast.LENGTH_SHORT).show();
                edtPasswordSignUp.setText("");
            }else {
                signUp(email, userName, phone, birthDay, passWord);
            }

        });
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the Calendar with the selected date
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Update the EditText with the selected date
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        edtBirthDaySignUp.setText(sdf.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void signUp(String email, String userName, String phone, String birthDay, String passWord) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        RegisterRequest request = new RegisterRequest(userName, passWord, email, birthDay, phone);
        Call<RegisterResponse> call = apiService.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    intent.putExtra("email_signup", registerResponse.getEmail());
                    intent.putExtra("pass_signup", registerResponse.getPassWord());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(DangKyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
