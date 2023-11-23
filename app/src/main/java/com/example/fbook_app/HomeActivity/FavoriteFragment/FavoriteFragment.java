package com.example.fbook_app.HomeActivity.FavoriteFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbook_app.Adapter.BookAdapter;
import com.example.fbook_app.Adapter.FavoriteBookAdapter;
import com.example.fbook_app.HomeActivity.HomeFragment.ChiTietBook.ChiTietBookFragment;
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    private RecyclerView rclListFavorite;
    private FavoriteBookAdapter adapter;
    private View mView;

    public FavoriteFragment() {
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
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);
        rclListFavorite = mView.findViewById(R.id.rcl_list_favorite_book);
        List<Book> list = new ArrayList<>();
        Book book1 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_top_book,10,"Tiểu Thuyết");
        Book book2 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_top_book,10,"Tiểu Thuyết");
        Book book3 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book,10,"Tiểu Thuyết");
        Book book4 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book,10,"Tiểu Thuyết");
        Book book5 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book,10,"Tiểu Thuyết");
        list.add(book1);
        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);
        list.add(book5);
        adapter = new FavoriteBookAdapter(getContext());
        adapter.setListBook(list);
        rclListFavorite.setLayoutManager(new GridLayoutManager(getContext(),2));
        rclListFavorite.setAdapter(adapter);
//        adapter.setOnItemClickListener(new FavoriteBookAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Book book) {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(android.R.id.content, ChiTietBookFragment.getInstance(book)).addToBackStack(fragmentManager.getClass().getSimpleName()).commit();
//            }
//        });

        return mView;
    }
}