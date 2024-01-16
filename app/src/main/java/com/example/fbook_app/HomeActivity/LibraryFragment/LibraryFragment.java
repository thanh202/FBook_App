package com.example.fbook_app.HomeActivity.LibraryFragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbook_app.Adapter.LibraryAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.HomeActivity.LibraryFragment.DocSach.DocSachFragment;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.Model.Request.DanhGiaRequest;
import com.example.fbook_app.Model.Request.NotificationRequest;
import com.example.fbook_app.Model.Response.DanhGiaResponse;
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.Model.Response.NotificationResponse;
import com.example.fbook_app.MyApplication;
import com.example.fbook_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibraryFragment extends Fragment implements FragmentReload {

    private SwipeRefreshLayout refreshLibrary;
    private TextView tvLibrary;
    private LibraryAdapter adapter;
    private RatingBar ratingUp;
    private EditText edtDanhGia;
    private CardView btnHuy, btnGui;
    private RecyclerView rclListLibraryBook;

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_library, container, false);
        refreshLibrary = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_library);
        tvLibrary = (TextView) mView.findViewById(R.id.tvLibrary);
        rclListLibraryBook = (RecyclerView) mView.findViewById(R.id.rcl_list_library_book);
        getDataLibrary();
        adapter = new LibraryAdapter(requireActivity());
        rclListLibraryBook.setAdapter(adapter);
        rclListLibraryBook.setLayoutManager(new GridLayoutManager(getContext(),2));
        refreshLibrary.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragmentData();
            }
        });
        adapter.setOnReadClickListener(new LibraryAdapter.OnReadClickListener() {
            @Override
            public void onItemClick(LibraryResponse.Result book) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(android.R.id.content, DocSachFragment.newInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
                    }
                },2000);
                 }

            @Override
            public void onDanhGiaClick(int idBook) {
                showDialogDanhGia(idBook);
            }
        });
        return mView;
    }
    private void getDataLibrary() {
        SharedPreferences myToken= requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser= requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        refreshLibrary.setRefreshing(true);
        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<LibraryResponse> call = apiService.getLibrary(token,idUser);
            call.enqueue(new Callback<LibraryResponse>() {
                @Override
                public void onResponse(Call<LibraryResponse> call, Response<LibraryResponse> response) {
                    refreshLibrary.setRefreshing(false);
                    if (response.isSuccessful()){
                        LibraryResponse libraryResponse = response.body();
                        if (libraryResponse != null){
                            showRecycle(libraryResponse);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LibraryResponse> call, Throwable t) {
                    refreshLibrary.setRefreshing(false);
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    @SuppressLint("MissingInflatedId")
    private void showDialogDanhGia(Integer idBook) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.danhgia_layout, null);

        edtDanhGia = view.findViewById(R.id.comment_up);
        ratingUp = view.findViewById(R.id.rating_bar_up);
        btnHuy = view.findViewById(R.id.btn_Huy);
        btnGui = view.findViewById(R.id.btn_Gui);
        alertDialog.setView(view);


        AlertDialog testDialog = alertDialog.create();

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog.dismiss();
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
                testDialog.dismiss();

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
    }
    public void showRecycle(LibraryResponse libraryResponse) {
        adapter.setListBook(libraryResponse.getResult());
        if (libraryResponse.getResult().size() <= 0) {
            tvLibrary.setVisibility(View.VISIBLE);
        } else {
            tvLibrary.setVisibility(View.GONE);
        }
    }
    @Override
    public void reloadFragmentData() {
        getDataLibrary();
    }
}