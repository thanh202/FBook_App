package com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.Adapter.ChapterBookAdapter;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.R;

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
    private TextView tvChapterBookChiTiet;
    private RecyclerView rclListChapter;
    private TextView tvPriceBookBookChiTiet;
    private TextView btnBuyBookChiTiet;
    private ChapterBookAdapter chapterBookAdapter;

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

    private void setData( View mView) {
        btnBackChiTietBook = (ImageView) mView.findViewById(R.id.btn_back_chi_tiet_book);
        imgViewBookChiTiet = (ImageView) mView.findViewById(R.id.imgView_book_chi_tiet);
        tvNameBookBookChiTiet = (TextView) mView.findViewById(R.id.tv_nameBook_book_chi_tiet);
        tvDescriptionBookChiTiet = (TextView) mView.findViewById(R.id.tv_description_book_chi_tiet);
        tvAuthorBookChiTiet = (TextView) mView.findViewById(R.id.tv_author_book_chi_tiet);
        tvPublishYearBookChiTiet = (TextView) mView.findViewById(R.id.tv_publishYear_book_chi_tiet);
        tvTypeBookBookChiTiet = (TextView) mView.findViewById(R.id.tv_typeBook_book_chi_tiet);
        tvChapterBookChiTiet = (TextView) mView.findViewById(R.id.tv_chapter_book_chi_tiet);
        rclListChapter = (RecyclerView) mView.findViewById(R.id.rcl_list_chapter);
        tvPriceBookBookChiTiet = (TextView) mView.findViewById(R.id.tv_priceBook_book_chi_tiet);
        btnBuyBookChiTiet = (TextView) mView.findViewById(R.id.btn_buy_book_chi_tiet);

        BookResponse.Result mBook = (BookResponse.Result) getArguments().get("object_book");
        String imgBook = RetrofitClient.BASE_URL+mBook.getImageBook();
        Glide.with(requireActivity()).load(imgBook).into(imgViewBookChiTiet);
        tvNameBookBookChiTiet.setText(mBook.getBookName());
        tvAuthorBookChiTiet.setText(mBook.getAuthor());
        tvDescriptionBookChiTiet.setText(mBook.getDiscription());
        tvPublishYearBookChiTiet.setText(mBook.getPublishYear());
        String price = mBook.getPriceBook()+" vnđ";
        tvPriceBookBookChiTiet.setText(price);
        tvTypeBookBookChiTiet.setText(mBook.getCatName());
        String chapterBook = String.valueOf(mBook.getChapter());
        tvChapterBookChiTiet.setText(chapterBook);

        chapterBookAdapter = new ChapterBookAdapter(requireActivity());
        chapterBookAdapter.setListChapterBook(mBook);
        rclListChapter.setAdapter(chapterBookAdapter);
        rclListChapter.setLayoutManager(new GridLayoutManager(getContext(),8));

        chapterBookAdapter.setOnItemClickListener(new ChapterBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
            }
        });
    }

}
