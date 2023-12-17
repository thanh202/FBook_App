package com.example.fbook_app.HomeActivity.LibraryFragment.DocSach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.HomeActivity.LibraryFragment.LibraryFragment;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.R;

public class DocSachFragment extends Fragment {
    public static DocSachFragment newInstance(LibraryResponse.Result book ) {
        DocSachFragment fragment = new DocSachFragment();
        Bundle args = new Bundle();
        args.putSerializable("content_book", book);
        fragment.setArguments(args);
        return fragment;
    }
    private ImageView bookImageView;
    private TextView bookTitle;
    private TextView Author;
    private TextView tvContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_doc_sach, container, false);
        setData(mView);
        return mView;
    }

    private void setData(View mView) {
        LibraryResponse.Result mBook = (LibraryResponse.Result) getArguments().get("content_book");
        bookImageView = (ImageView) mView.findViewById(R.id.bookImageView);
        bookTitle = (TextView) mView.findViewById(R.id.bookTitle);
        Author = (TextView) mView.findViewById(R.id.Author);
        tvContent = (TextView) mView.findViewById(R.id.tv_content);
        final ScrollView scrollView = mView.findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
        String imgBook = RetrofitClient.BASE_URL + mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBook).into(bookImageView);
        bookTitle.setText(mBook.getBookName());
        Author.setText(mBook.getAuthor());
        tvContent.setText(mBook.getContent());
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                // Nếu người dùng vuốt xuống, ẩn ảnh và tên sách
//                if (scrollY > oldScrollY) {
//                    bookImageView.setVisibility(View.GONE);
//                    bookTitle.setVisibility(View.GONE);
//                    Author.setVisibility(View.GONE);
//                } else {
//                    // Nếu người dùng vuốt lên, hiển thị lại ảnh và tên sách
//                    bookImageView.setVisibility(View.VISIBLE);
//                    bookTitle.setVisibility(View.VISIBLE);
//                    Author.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }
}
