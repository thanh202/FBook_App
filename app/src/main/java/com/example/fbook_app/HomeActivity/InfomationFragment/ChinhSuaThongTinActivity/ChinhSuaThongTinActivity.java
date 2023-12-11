package com.example.fbook_app.HomeActivity.InfomationFragment.ChinhSuaThongTinActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.fbook_app.R;

public class ChinhSuaThongTinActivity extends AppCompatActivity {
    private CardView btnBack;
    private EditText edt_nameUser,edt_email,edt_sdt,edt_birthday;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin);
        btnBack=findViewById(R.id.back_chinhsuathongtin);
        edt_nameUser=findViewById(R.id.nameUser_chinhsuathongtin);
        edt_email=findViewById(R.id.email_chinhsuathongtin);
        edt_sdt=findViewById(R.id.sdt_chinhsuathongtin);
        edt_birthday=findViewById(R.id.bthday_chinhsuathongtin);

        SharedPreferences myNameUser= getSharedPreferences("MyNameUser", Context.MODE_PRIVATE);
        String nameUser1 = myNameUser.getString("nameUser", null);
        edt_nameUser.setText(nameUser1);

        SharedPreferences myEmail= getSharedPreferences("MyEmail", Context.MODE_PRIVATE);
        String email = myEmail.getString("email", null);
        edt_email.setText(email);

        SharedPreferences myBirthDay= getSharedPreferences("MyBirthDay", Context.MODE_PRIVATE);
        String birthday = myBirthDay.getString("birthday", null);
        edt_birthday.setText(birthday);

        SharedPreferences mySDT= getSharedPreferences("MySDT", Context.MODE_PRIVATE);
        String sdt = mySDT.getString("sdt", null);
        edt_sdt.setText(sdt);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}