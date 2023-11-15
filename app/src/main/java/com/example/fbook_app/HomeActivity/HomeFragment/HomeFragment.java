package com.example.fbook_app.HomeActivity.HomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbook_app.Adapter.BookAdapter;
import com.example.fbook_app.Adapter.NewBookAdapter;
import com.example.fbook_app.Adapter.TopBookAdapter;
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private BookAdapter adapter;
    private TopBookAdapter adapterTopBook;
    private NewBookAdapter adapterNewBook;
    private RecyclerView rclBook;
    private RecyclerView rclTopBook;
    private RecyclerView rclNewBook;

    private View mView;

    public HomeFragment() {
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
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        rclBook = mView.findViewById(R.id.rcl_book);
        rclTopBook = mView.findViewById(R.id.rcl_topBook);
        rclNewBook = mView.findViewById(R.id.rcl_newBook);

        List<Book> list = new ArrayList<>();
        Book book1 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_top_book);
        Book book2 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_top_book);
        Book book3 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book);
        Book book4 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book);
        Book book5 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book);
        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);
        list.add(book5);
        adapter = new BookAdapter(getContext());
        adapter.setListBook(list);
        rclBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclBook.setAdapter(adapter);

        adapterNewBook = new NewBookAdapter(getContext());
        adapterNewBook.setListBook(list);
        rclNewBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rclNewBook.setAdapter(adapterNewBook);

        adapterTopBook = new TopBookAdapter(getContext());
        adapterTopBook.setListBook(list);
        rclTopBook.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rclTopBook.setAdapter(adapterTopBook);

        return mView;
    }
}