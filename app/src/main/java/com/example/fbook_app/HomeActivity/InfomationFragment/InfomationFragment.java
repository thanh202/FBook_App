package com.example.fbook_app.HomeActivity.InfomationFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fbook_app.HomeActivity.InfomationFragment.ChinhSuaThongTinActivity.ChinhSuaThongTinActivity;
import com.example.fbook_app.MainActivity;
import com.example.fbook_app.R;


public class InfomationFragment extends Fragment {


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
                Toast.makeText(getContext(), "Coming Soon ...", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


}