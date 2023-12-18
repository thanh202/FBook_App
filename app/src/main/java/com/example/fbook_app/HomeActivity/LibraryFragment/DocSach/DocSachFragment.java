package com.example.fbook_app.HomeActivity.LibraryFragment.DocSach;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.LibraryFragment.LibraryFragment;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.R;
import com.google.android.material.appbar.AppBarLayout;

public class DocSachFragment extends Fragment {
    public static DocSachFragment newInstance(LibraryResponse.Result book) {
        DocSachFragment fragment = new DocSachFragment();
        Bundle args = new Bundle();
        args.putSerializable("content_book", book);
        fragment.setArguments(args);
        return fragment;
    }

    private AppBarLayout appBarLayout;
    private ImageView bookImageView;
    private ImageView btnBack;
    private ImageView btnSetting;
    private NestedScrollView scrollView;
    private TextView tvContent,tvTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_docsach, container, false);
        setData(mView);
        return mView;
    }

    private void setData(View mView) {
        LibraryResponse.Result mBook = (LibraryResponse.Result) getArguments().get("content_book");
        appBarLayout = (AppBarLayout) mView.findViewById(R.id.appBarLayout);
        bookImageView = (ImageView) mView.findViewById(R.id.bookImageView);
        btnBack = (ImageView) mView.findViewById(R.id.btn_back);
        btnSetting = (ImageView) mView.findViewById(R.id.btn_setting);
        scrollView = (NestedScrollView) mView.findViewById(R.id.scrollView);
        tvContent = (TextView) mView.findViewById(R.id.tv_content);
        tvTitle = mView.findViewById(R.id.tv_title_docsach);
        scrollView.smoothScrollTo(0, 0);
        String imgBook = RetrofitClient.BASE_URL + mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBook).into(bookImageView);
        tvContent.setText(mBook.getContent());
        tvTitle.setText(mBook.getBookName());

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        btnSetting.setOnClickListener(v -> {
            showDialogSetting();
        });
    }
    private int brightnessValue = 50;
    private int textSizeValue = 16;
    private boolean nightMode = false;
    private void showDialogSetting() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.setting_dialog, null);

        // Ánh xạ các thành phần trong layout của dialog
        SeekBar brightnessSeekBar = dialogView.findViewById(R.id.brightnessSeekBar);
        SeekBar textSizeSeekBar = dialogView.findViewById(R.id.textSizeSeekBar);
        Button closeButton = dialogView.findViewById(R.id.closeButton);
        Switch nightModeSwitch = dialogView.findViewById(R.id.nightModeSwitch);

        brightnessSeekBar.setProgress(brightnessValue);
        textSizeSeekBar.setProgress(textSizeValue);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Thiết lập layout cho dialog
        builder.setView(dialogView);

        // Tạo và hiển thị dialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Xử lý sự kiện khi SeekBar thay đổi
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnessValue = progress;
                updateBrightness(tvContent, progress);
                // Xử lý khi ánh sáng thay đổi (có thể áp dụng vào giao diện)

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSizeValue = progress;
                updateTextSize(tvContent, progress);
                // Xử lý khi kích thước chữ thay đổi (có thể áp dụng vào giao diện)
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nightMode = isChecked;
                nightModeSwitch.setChecked(isChecked);
                // Cập nhật chế độ đọc ban đêm trực tiếp lên giao diện đọc sách
                updateNightMode(tvContent, isChecked);

            }
        });
        // Xử lý sự kiện khi nút đóng được nhấn
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void updateBrightness(TextView bookContentTextView, int brightness) {
        // Áp dụng ánh sáng vào giao diện đọc sách (ví dụ)
        float alpha = brightness / 100.0f;
        bookContentTextView.setAlpha(alpha);
    }

    private void updateTextSize(TextView bookContentTextView, int textSize) {
        // Áp dụng kích thước chữ vào giao diện đọc sách (ví dụ)
        bookContentTextView.setTextSize(textSize);
    }
    private void updateNightMode(TextView bookContentTextView, boolean nightMode) {
        // Áp dụng chế độ đọc ban đêm vào giao diện đọc sách (ví dụ)
        if (nightMode) {
            // Thiết lập màu chữ và màu nền cho chế độ đọc ban đêm
            bookContentTextView.setTextColor(getResources().getColor(android.R.color.white));
            bookContentTextView.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            // Thiết lập màu chữ và màu nền cho chế độ bình thường
            bookContentTextView.setTextColor(getResources().getColor(android.R.color.black));
            bookContentTextView.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }
}
