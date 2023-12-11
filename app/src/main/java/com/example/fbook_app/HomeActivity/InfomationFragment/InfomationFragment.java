package com.example.fbook_app.HomeActivity.InfomationFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbook_app.HomeActivity.InfomationFragment.ChinhSuaThongTinActivity.ChinhSuaThongTinActivity;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.R;

import io.paperdb.Paper;


public class InfomationFragment extends Fragment implements FragmentReload {
private TextView nameUser,idUser;

    private LinearLayout btnChinhSuaThongTin, btnDangXuat;
    private View view;

    public InfomationFragment() {
        // Required empty public constructor
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
        idUser=view.findViewById(R.id.idUser);
        nameUser=view.findViewById(R.id.nameUser);

        SharedPreferences myIdUser= requireActivity().getSharedPreferences("MyIdUser", Context.MODE_PRIVATE);
        int idUser1 = myIdUser.getInt("idUser", 0);
        String idUser2=String.valueOf(idUser1);
        idUser.setText(idUser2);

        SharedPreferences myNameUser= requireActivity().getSharedPreferences("MyNameUser", Context.MODE_PRIVATE);
        String nameUser1 = myNameUser.getString("nameUser", null);
        nameUser.setText(nameUser1);
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChinhSuaThongTinActivity.class);
                startActivity(intent);
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

    private void showDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Thông Báo");
        builder.setMessage("Vui Lòng Xác Nhận Đăng Xuất !");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProgressDialog dialog=new ProgressDialog(getContext());
                dialog.setMessage("Vui Lòng Đợi ...");
                dialog.show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        Paper.book().destroy();
                        startActivity(intent);
                    }
                },2000);
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

    }
}