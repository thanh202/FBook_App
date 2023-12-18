package com.example.fbook_app.HomeActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.HomeActivity.zalopay.CreateOrder;
import com.example.fbook_app.Model.Request.BillRequest;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
    private TextView tvNameBook, tvPriceBook, tvDate, tvTime;
    private MaterialSpinner spinner;

    private String check = "";

    private ImageView btnBack;

    private CardView btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        setData();


        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void setData() {
        btnBack = findViewById(R.id.btn_back_thanhtoan);
        tvNameBook = findViewById(R.id.nameBook_thanhtoan);
        tvPriceBook = findViewById(R.id.priceBook_thanhtoan);
        tvDate = findViewById(R.id.date_thanhtoan);
        tvTime = findViewById(R.id.time_thanhtoan);
        spinner = findViewById(R.id.spiner);
        btnThanhToan = findViewById(R.id.card3_thanhtoan);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");

        String formattedDate = date.format(c.getTime());
        String formattedTime = time.format(c.getTime());
        String formattedDateTime = datetime.format(c.getTime());

        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        tvNameBook.setText(Common.currentBook.getBookName());
        tvPriceBook.setText(format.format(Common.currentBook.getPriceBook()));
        tvDate.setText(formattedDate);
        tvTime.setText(formattedTime);

        spinner.setItems("Thanh Toán Ngay", "ZaloPay");

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "";
                int iDBook = Integer.parseInt(String.valueOf(Common.currentBook.getIDBook()));
                int priceTotal = Integer.parseInt(String.valueOf(Common.currentBook.getPriceBook()));
                String create_at = formattedDateTime;
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(ThanhToanActivity.this);
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (spinner.getSelectedIndex() == 0) {
                            thanhtoan(status, iDBook, priceTotal, create_at);
                        } else {
                            thanhtoanZalo(status, iDBook, priceTotal, create_at);
                        }
                    }
                }, 2000);

            }
        });

    }

    private void thanhtoanZalo(String status, int iDBook, int priceTotal, String createAt) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences myToken = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        if (token != null && idUser > 0) {
            CreateOrder orderApi = new CreateOrder();
            try {
                JSONObject data = orderApi.createOrder(String.valueOf(priceTotal));
                String code = data.getString("return_code");
                if (code.equals("1")) {
                    String token1 = data.getString("zp_trans_token");
                    ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token1, "demozpdk://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(String s, String s1, String s2) {
                            BillRequest request = new BillRequest("Đã Thanh Toán", iDBook, idUser, priceTotal, createAt);
                            Call<BillResponse> call = apiService.addBill(token, request);
                            call.enqueue(new Callback<BillResponse>() {
                                @Override
                                public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {

                                }

                                @Override
                                public void onFailure(Call<BillResponse> call, Throwable t) {

                                }
                            });
                            Intent intent=new Intent(ThanhToanActivity.this,HomeActivity.class);
                            finish();
                            startActivity(intent);
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thành Công !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPaymentCanceled(String s, String s1) {

                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void thanhtoan(String status, int iDBook, int priceTotal, String create_at) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences myToken = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        BillRequest request = new BillRequest(status, iDBook, idUser, priceTotal, create_at);
        Call<BillResponse> call = apiService.addBill(token, request);
        if (token != null && idUser > 0) {
            call.enqueue(new Callback<BillResponse>() {
                @Override
                public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                    Intent intent=new Intent(ThanhToanActivity.this,HomeActivity.class);
                    finish();
                    startActivity(intent);
                    if (response.isSuccessful()) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thành Công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thất Bại !", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<BillResponse> call, Throwable t) {

                }
            });
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

}