package com.example.fbook_app.HomeActivity.LibraryFragment.DocSach;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.Adapter.ChapterAdapter;
import com.example.fbook_app.ApiNetwork.ApiService;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.LibraryFragment.LibraryFragment;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.ChapterResponse;
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.sidesheet.SideSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ImageView btnSetting, btnMenu;
    private NestedScrollView scrollView;
    private TextView tvContent, tvTitle;
    private LibraryResponse.Result mBook;
    private ImageView imgBook;
    private TextView tvNameBook;
    private TextView tvAuthor;
    private RecyclerView rclChapter;
    private ChapterAdapter adapter;
    private int currentChapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_docsach, container, false);
        setData(mView);
        return mView;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveReadingState(currentChapter);
    }
    private void saveReadingState(int currentPage) {
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("CURRENT_PAGE", currentPage);
        editor.putInt("SCROLL_POSITION", scrollView.getScrollY());
        editor.apply();
    }
    private void setData(View mView) {
        mBook = (LibraryResponse.Result) getArguments().get("content_book");
        appBarLayout = (AppBarLayout) mView.findViewById(R.id.appBarLayout);
        bookImageView = (ImageView) mView.findViewById(R.id.bookImageView);
        btnBack = (ImageView) mView.findViewById(R.id.btn_back);
        btnMenu = mView.findViewById(R.id.btn_menu_chapter);
        btnSetting = (ImageView) mView.findViewById(R.id.btn_setting);
        scrollView = (NestedScrollView) mView.findViewById(R.id.scrollView);
        tvContent = (TextView) mView.findViewById(R.id.tv_content);
        tvTitle = mView.findViewById(R.id.tv_title_docsach);
        String imgBook = RetrofitClient.BASE_URL + mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBook).into(bookImageView);
        tvTitle.setText(mBook.getBookName());
        getChapter(mBook.getIDBook());
        adapter = new ChapterAdapter(requireActivity());
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        btnSetting.setOnClickListener(v -> {
            showDialogSetting();
        });
        btnMenu.setOnClickListener(v -> {
            showSideSheetDialog();
        });
    }
    public void setContent(List<ChapterResponse.Result> list){
        ChapterResponse.Result chapter = list.get(0);
        tvContent.setText(chapter.getContent());
    }
    public void restoreScrollPosition(List<ChapterResponse.Result> list, int position, int scrollPosition){
        ChapterResponse.Result chapter = list.get(position);
        tvContent.setText(chapter.getContent());
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,scrollPosition);
            }
        });
    }
    private void showSideSheetDialog() {
        SideSheetDialog sideSheetDialog = new SideSheetDialog(requireActivity());
        sideSheetDialog.setContentView(R.layout.fragment_chapter);
        imgBook = (ImageView) sideSheetDialog.findViewById(R.id.img_book);
        tvNameBook = (TextView) sideSheetDialog.findViewById(R.id.tv_name_book);
        tvAuthor = (TextView) sideSheetDialog.findViewById(R.id.tv_author);
        rclChapter = sideSheetDialog.findViewById(R.id.rcl_chapter);
        String imgBookChapter = RetrofitClient.BASE_URL + mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBookChapter).into(imgBook);
        tvNameBook.setText(mBook.getBookName());
        tvAuthor.setText(mBook.getAuthor());
        rclChapter.setAdapter(adapter);
        rclChapter.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        adapter.setOnItemClickListener(new ChapterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                tvContent.setText(content);
                sideSheetDialog.hide();
                scrollView.scrollTo(0,0);
            }

            @Override
            public void onGetPositionChapter(int position) {
                currentChapter = position;
            }
        });
        sideSheetDialog.show();
    }

    private void getChapter(Integer idBook) {
        SharedPreferences myToken = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = myToken.getString("token", null);
        if (token != null && idBook > 0) {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<ChapterResponse> call = apiService.getChapter(token, idBook);
            call.enqueue(new Callback<ChapterResponse>() {
                @Override
                public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
                        int savedPosition = sharedPreferences.getInt("SCROLL_POSITION", 0);
                        int position = sharedPreferences.getInt("CURRENT_PAGE", 0);
                        ChapterResponse chapterResponse = response.body();
                        if (isFirstTimeReading()){
                            setContent(chapterResponse.getResult());
                            markFirstTimeReading();
                        }else{
                            restoreScrollPosition(chapterResponse.getResult(),position,savedPosition);
                        }
                        adapter.setListChapter(chapterResponse.getResult());
                    }
                }

                @Override
                public void onFailure(Call<ChapterResponse> call, Throwable t) {

                }
            });
        }
    }
    private boolean isFirstTimeReading() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        return prefs.getBoolean("firstTimeReading", true);
    }
    private void markFirstTimeReading() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstTimeReading", false);
        editor.apply();
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
