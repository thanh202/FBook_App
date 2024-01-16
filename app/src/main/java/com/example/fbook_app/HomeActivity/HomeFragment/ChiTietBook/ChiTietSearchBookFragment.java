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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.Adapter.ChapterBookAdapter;
import com.example.fbook_app.Adapter.ChapterSearchBookAdapter;
import com.example.fbook_app.Adapter.DanhGiaAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.HomeActivity.ThanhToanActivity;
import com.example.fbook_app.Model.Request.DanhGiaRequest;
import com.example.fbook_app.Model.Request.NotificationRequest;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.DanhGiaResponse;
import com.example.fbook_app.Model.Response.NotificationResponse;
import com.example.fbook_app.Model.Response.SearchResponse;
import com.example.fbook_app.MyApplication;
import com.example.fbook_app.R;
import com.example.fbook_app.ThanhToanSearchActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSearchBookFragment extends Fragment {
    private View mView;

    public static ChiTietSearchBookFragment getInstance(SearchResponse.Result book) {
        ChiTietSearchBookFragment chiTietBookFragment = new ChiTietSearchBookFragment();
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
    private TextView tvChapterBookChiTiet;

    private TextView tvPriceBookBookChiTiet,btnDanhGia;
    private TextView btnBuyBookChiTiet;

    private DanhGiaAdapter adapter;
    private EditText edtDanhGia;
    private RecyclerView rclListDanhGia;
    private RatingBar ratingUp;
    private CardView btnHuy, btnGui;


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
        btnBuyBookChiTiet = (TextView) mView.findViewById(R.id.btn_buy_book_chi_tiet);
        adapter = new DanhGiaAdapter(getContext());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        SearchResponse.Result mBook = (SearchResponse.Result) getArguments().get("object_book");
        String imgBook = RetrofitClient.BASE_URL + mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBook).into(imgViewBookChiTiet);
        tvNameBookBookChiTiet.setText(mBook.getBookName());
        tvAuthorBookChiTiet.setText(mBook.getAuthor());
        tvDescriptionBookChiTiet.setText(mBook.getDiscription());
        tvPublishYearBookChiTiet.setText(mBook.getPublishYear());
        tvPriceBookBookChiTiet.setText(format.format(mBook.getPriceBook()));
        tvTypeBookBookChiTiet.setText(mBook.getCatName());
        String chapterBook = String.valueOf(mBook.getChapter());


        adapter = new DanhGiaAdapter(getContext());
        rclListDanhGia.setAdapter(adapter);
        rclListDanhGia.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        btnBuyBookChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler =new Handler();
                ProgressDialog dialog=new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Common.currentSearchBook = mBook;
                        Intent intent = new Intent(getContext(), ThanhToanSearchActivity.class);
                        startActivity(intent);
                    }
                },2000);
                }
        });
        btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDanhGia(mBook.getIDBook());
            }
        });
        loadData(mBook.getIDBook());
    }
    @SuppressLint("MissingInflatedId")
    private void showDialogDanhGia(Integer idBook) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.danhgia_layout, null);

        edtDanhGia = view.findViewById(R.id.comment_up);
        ratingUp = view.findViewById(R.id.rating_bar_up);
        btnHuy = view.findViewById(R.id.btn_Huy);
        btnGui=view.findViewById(R.id.btn_Gui);
        alertDialog.setView(view);


        AlertDialog testDialog = alertDialog.create ();

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog.dismiss ();
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                SharedPreferences myToken = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = myToken.getString("token", null);
                SharedPreferences myIdUser = getActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
                int idUser = myIdUser.getInt("idUser", 0);

                DanhGiaRequest request = new DanhGiaRequest(idBook, idUser, ratingUp.getRating(), edtDanhGia.getText().toString());
                Call<DanhGiaResponse> call = apiService.addDanhGia(token, request);

                if (token != null && idUser > 0) {
                    call.enqueue(new Callback<DanhGiaResponse>() {
                        @Override
                        public void onResponse(Call<DanhGiaResponse> call, Response<DanhGiaResponse> response) {
                            if (response.isSuccessful()) {
                            }
                        }

                        @Override
                        public void onFailure(Call<DanhGiaResponse> call, Throwable t) {

                        }
                    });
                }
                sendNotificationSuccess(idBook);
                testDialog.dismiss ();

            }
        });
        testDialog.show();
    }
    private void sendNotificationSuccess(Integer idBook) {

        Intent intent = new Intent(getContext(), com.example.fbook_app.HomeActivity.Notification.Notification.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        String title = "Gửi Đánh Giá Thành Công";
        String body = "Cảm ơn bạn đã gửi đánh giá cho chúng tôi !. Ý kiến của bạn là động lực để chúng tôi cải thiện và phát triển.";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Notification notification = new NotificationCompat.Builder(getContext(), MyApplication.ID)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setSmallIcon(R.drawable.logo_fbook)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1, notification);
        }
        addNofi(title, body, idBook);
    }


    private void addNofi(String title, String body, Integer idBook) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences myToken = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = getActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String formattedDateTime = datetime.format(c.getTime());


        NotificationRequest request = new NotificationRequest(title, body, idUser, idBook, formattedDateTime);
        Call<NotificationResponse> call = apiService.addNotification(token, request);
        if (token != null && idUser > 0) {
            call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {

                }
            });
        }
        setData(mView);
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
