package com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.Adapter.DanhGiaAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.HomeActivity.ThanhToanActivity;
import com.example.fbook_app.Model.Request.BillRequest;
import com.example.fbook_app.Model.Request.DanhGiaRequest;
import com.example.fbook_app.Model.Request.NotificationRequest;
import com.example.fbook_app.Model.Response.BillResponse;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.DanhGiaResponse;
import com.example.fbook_app.Model.Response.NotificationResponse;
import com.example.fbook_app.MyApplication;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietBookFragment extends Fragment {
    private View mView;

    public static ChiTietBookFragment getInstance(BookResponse.Result book) {
        ChiTietBookFragment chiTietBookFragment = new ChiTietBookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_book", book);
        chiTietBookFragment.setArguments(bundle);
        return chiTietBookFragment;
    }

    private ImageView btnBackChiTietBook;
    private ImageView imgViewBookChiTiet;
    private TextView tvNameBookBookChiTiet;
    private TextView tvDescriptionBookChiTiet;
    private TextView tvAuthorBookChiTiet;
    private TextView tvPublishYearBookChiTiet;
    private TextView tvTypeBookBookChiTiet;

    private TextView tvPriceBookBookChiTiet,tvKhongCoDanhGia;
    private TextView btnBuyBookChiTiet;
    private DanhGiaAdapter adapter;

    private RecyclerView rclListDanhGia;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chi_tiet_book, container, false);
        setData(mView);

        btnBackChiTietBook.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return mView;
    }

    private void setData(View mView) {
        btnBackChiTietBook = (ImageView) mView.findViewById(R.id.btn_back_chi_tiet_book);
        imgViewBookChiTiet = (ImageView) mView.findViewById(R.id.imgView_book_chi_tiet);
        tvNameBookBookChiTiet = (TextView) mView.findViewById(R.id.tv_nameBook_book_chi_tiet);
        tvDescriptionBookChiTiet = (TextView) mView.findViewById(R.id.tv_description_book_chi_tiet);
        tvAuthorBookChiTiet = (TextView) mView.findViewById(R.id.tv_author_book_chi_tiet);
        tvPublishYearBookChiTiet = (TextView) mView.findViewById(R.id.tv_publishYear_book_chi_tiet);
        tvTypeBookBookChiTiet = (TextView) mView.findViewById(R.id.tv_typeBook_book_chi_tiet);
        rclListDanhGia = (RecyclerView) mView.findViewById(R.id.rcl_danhgia);
        tvPriceBookBookChiTiet = (TextView) mView.findViewById(R.id.tv_priceBook_book_chi_tiet);
        tvKhongCoDanhGia = mView.findViewById(R.id.tv_khongDanhGia);
        btnBuyBookChiTiet = (TextView) mView.findViewById(R.id.btn_buy_book_chi_tiet);
        adapter = new DanhGiaAdapter(getContext());

        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        BookResponse.Result mBook = (BookResponse.Result) getArguments().get("object_book");
        String imgBook = RetrofitClient.BASE_URL + mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBook).into(imgViewBookChiTiet);
        tvNameBookBookChiTiet.setText(mBook.getBookName());
        tvAuthorBookChiTiet.setText(mBook.getAuthor());
        tvDescriptionBookChiTiet.setText(mBook.getDiscription());
        tvPublishYearBookChiTiet.setText(mBook.getPublishYear());


        if (mBook.getPriceBook()==0){
            tvPriceBookBookChiTiet.setText("Miễn Phí");
            btnBuyBookChiTiet.setText("Nhận ngay");
        }else {
            tvPriceBookBookChiTiet.setText(format.format(mBook.getPriceBook()));
        }
        tvTypeBookBookChiTiet.setText(mBook.getCatName());


        loadData(mBook.getIDBook());
        rclListDanhGia.setAdapter(adapter);
        rclListDanhGia.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        btnBuyBookChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String formattedDateTime = datetime.format(c.getTime());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Common.currentBook = mBook;
                        if (mBook.getPriceBook()==0){
                            thanhtoan("Đã Thanh Toán",mBook.getIDBook(), mBook.getPriceBook(),formattedDateTime);
                        }else{
                            Intent intent = new Intent(getContext(), ThanhToanActivity.class);
                            startActivity(intent);
                        }
                    }
                }, 2000);
            }
        });
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
                            Toast.makeText(requireActivity(), "Thêm vào thư viện thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Thanh Toán Thất Bại !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BillResponse> call, Throwable t) {

                }
            });
        }

    }

    private void loadData(Integer idBook) {
        SharedPreferences myToken = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);

        if (token != null && idBook > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<DanhGiaResponse> call = apiService.getDanhGia(token, idBook);
            call.enqueue(new Callback<DanhGiaResponse>() {
                @Override
                public void onResponse(Call<DanhGiaResponse> call, Response<DanhGiaResponse> response) {
                    if (response.isSuccessful()) {
                        DanhGiaResponse danhGiaResponse = response.body();
                        if (danhGiaResponse != null) {
                            adapter.setDanhgiaList(danhGiaResponse.getResult());
                            if (danhGiaResponse.getResult().size() == 0){
                                rclListDanhGia.setVisibility(View.INVISIBLE);
                                tvKhongCoDanhGia.setVisibility(View.VISIBLE);
                            }else{
                                rclListDanhGia.setVisibility(View.VISIBLE);
                                tvKhongCoDanhGia.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DanhGiaResponse> call, Throwable t) {
                }
            });
        }
    }

}
