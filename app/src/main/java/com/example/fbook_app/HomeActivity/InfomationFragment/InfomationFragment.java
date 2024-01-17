package com.example.fbook_app.HomeActivity.InfomationFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Common.Common;
import com.example.fbook_app.HomeActivity.InfomationFragment.ChinhSuaThongTinActivity.ChinhSuaThongTinActivity;
import com.example.fbook_app.HomeActivity.InfomationFragment.DoiMatKhau.DoiMatKhauActivity;
import com.example.fbook_app.HomeActivity.InfomationFragment.LichSuMuaSach.LichSuMuaSach;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.Model.Response.UserResponse;
import com.example.fbook_app.R;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfomationFragment extends Fragment implements FragmentReload {
    private TextView tvNameUser, tvIdUser;

    private LinearLayout btnChinhSuaThongTin, btnDoiPassWord, btnDangXuat;

    private CardView btnLichSuMuaSach;

    private ImageView avtUser;

    String userName, birthDay;
    private View view;

    public InfomationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadFragmentData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infomation, container, false);
        btnChinhSuaThongTin = view.findViewById(R.id.btn_chinhsuathongtin);
        btnDangXuat = view.findViewById(R.id.btn_DangXuat);
        btnDoiPassWord = view.findViewById(R.id.btn_doiMatKhau);
        tvIdUser = view.findViewById(R.id.idUser);
        avtUser=view.findViewById(R.id.avt_user);
        tvNameUser = view.findViewById(R.id.nameUser);
        btnLichSuMuaSach = view.findViewById(R.id.btn_lichsumuasach);
        getInformation();

        btnLichSuMuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), LichSuMuaSach.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }, 2000);
            }
        });
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), ChinhSuaThongTinActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }, 2000);
            }
        });
        btnDoiPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), DoiMatKhauActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }, 2000);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        Paper.init(getContext());
        return view;
    }

    private void getInformation() {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        SharedPreferences myIdUser = requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser = myIdUser.getInt("idUser", 0);
        if (token != null && idUser > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<UserResponse> call = apiService.getUser(token, idUser);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        UserResponse userResponse = response.body();
                        tvIdUser.setText(userResponse.getIDUser() + "");
                        tvNameUser.setText(userResponse.getUserName());
                        Common.USER_NAME = userResponse.getUserName();
                        Common.BIRTH_DAY = userResponse.getBirthday();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông Báo");
        builder.setMessage("Vui Lòng Xác Nhận Đăng Xuất !");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        Paper.book().destroy();
                        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("CURRENT_PAGE");
                        editor.remove("SCROLL_POSITION");
                        editor.apply();
                        startActivity(intent);
                    }
                }, 2000);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void reloadFragmentData() {
        getInformation();
    }


}