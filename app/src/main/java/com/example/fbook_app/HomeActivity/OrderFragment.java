package com.example.fbook_app.HomeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.zalopay.CreateOrder;
import com.example.fbook_app.Model.Request.BillRequest;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.RegisterResponse;
import com.example.fbook_app.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class OrderFragment extends Fragment {
    private TextView tvNameBook, tvPriceBook, tvDate, tvTime;
    private MaterialSpinner spinner;

    private ImageView btnBack;

    private CardView btnThanhToan;

    private View view;


    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(BookResponse.Result book) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putSerializable("object_book", book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);
        setData(view);


        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void thanhtoan(String status, int iDBook, int priceTotal, String create_at) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        BillRequest request = new BillRequest(status, iDBook, idUser, priceTotal, create_at);
        Call<BillResponse> call = apiService.addBill(token, request);
        if (token != null && idUser > 0) {
            call.enqueue(new Callback<BillResponse>() {
                @Override
                public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Thanh Toán Thành Công !", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                }

                @Override
                public void onFailure(Call<BillResponse> call, Throwable t) {

                }
            });
        }


    }

    private void setData(View view) {
        btnBack = view.findViewById(R.id.btn_back_thanhtoan);
        tvNameBook = view.findViewById(R.id.nameBook_thanhtoan);
        tvPriceBook = view.findViewById(R.id.priceBook_thanhtoan);
        tvDate = view.findViewById(R.id.date_thanhtoan);
        tvTime = view.findViewById(R.id.time_thanhtoan);
        spinner = view.findViewById(R.id.spiner);
        btnThanhToan = view.findViewById(R.id.card3_thanhtoan);

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


        BookResponse.Result mBook = (BookResponse.Result) getArguments().get("object_book");
        tvNameBook.setText(mBook.getBookName());
        String price = mBook.getPriceBook() + " vnđ";
        tvPriceBook.setText(price);
        tvDate.setText(formattedDate);
        tvTime.setText(formattedTime);

        spinner.setItems("Thanh Toán Ngay", "ZaloPay");

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "Đã Thanh Toán";
                int iDBook = mBook.getIDBook();
                int priceTotal = mBook.getPriceBook();
                String create_at = formattedDateTime;
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        thanhtoan(status, iDBook, priceTotal, create_at);
                        thanhtoanZalo(status, iDBook, priceTotal, create_at);
                    }
                }, 2000);

            }
        });

    }


    private void thanhtoanZalo(String status, int iDBook, int priceTotal, String createAt) {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(String.valueOf(priceTotal));
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(getActivity(), token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = myToken.getString("token", null);
                        SharedPreferences myIdUser = requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
                        int idUser = myIdUser.getInt("idUser", 0);

                        BillRequest request = new BillRequest(status, iDBook, idUser, priceTotal, createAt);
                        Call<BillResponse> call = apiService.addBill(token, request);
                        if (token != null && idUser > 0) {
                            call.enqueue(new Callback<BillResponse>() {
                                @Override
                                public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Thanh Toán Thành Công !", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                }

                                @Override
                                public void onFailure(Call<BillResponse> call, Throwable t) {

                                }
                            });
                        }
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

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        ZaloPaySDK.getInstance().onResult(intent);
//    }

}