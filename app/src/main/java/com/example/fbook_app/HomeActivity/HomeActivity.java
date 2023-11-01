package com.example.fbook_app.HomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fbook_app.Adpater.BookAdapter;
import com.example.fbook_app.Adpater.NewBookAdapter;
import com.example.fbook_app.Adpater.TopBookAdapter;
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private BookAdapter adapter;
    private TopBookAdapter adapterTopBook;
    private NewBookAdapter adapterNewBook;
    private RecyclerView rclBook;
    private RecyclerView rclTopBook;
    private RecyclerView rclNewBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rclBook = findViewById(R.id.rcl_book);
        rclTopBook = findViewById(R.id.rcl_topBook);
        rclNewBook = findViewById(R.id.rcl_newBook);
        List<Book> list = new ArrayList<>();
        Book book1 = new Book(1,"Đấu phá thương khung P5","DuckZang","23-11-2003","50.000 vnđ","Già nam học viện nơi......",R.drawable.img_top_book);
        Book book2 = new Book(1,"Đấu phá thương khung P5","DuckZang","23-11-2003","50.000 vnđ","Già nam học viện nơi......",R.drawable.img_top_book);
        Book book3 = new Book(1,"Đấu phá thương khung P5","DuckZang","23-11-2003","50.000 vnđ","Già nam học viện nơi......",R.drawable.img_book);
        Book book4 = new Book(1,"Đấu phá thương khung P5","DuckZang","23-11-2003","50.000 vnđ","Già nam học viện nơi......",R.drawable.img_book);
        Book book5 = new Book(1,"Đấu phá thương khung P5","DuckZang","23-11-2003","50.000 vnđ","Già nam học viện nơi......",R.drawable.img_book);
        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);
        list.add(book5);
        adapter = new BookAdapter(this);
        adapter.setListBook(list);
        rclBook.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rclBook.setAdapter(adapter);

        adapterNewBook = new NewBookAdapter(this);
        adapterNewBook.setListBook(list);
        rclNewBook.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rclNewBook.setAdapter(adapterNewBook);

        adapterTopBook = new TopBookAdapter(this);
        adapterTopBook.setListBook(list);
        rclTopBook.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rclTopBook.setAdapter(adapterTopBook);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_fav) {
                    Toast.makeText(HomeActivity.this, "Favorite", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_cart) {
                    Toast.makeText(HomeActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_info) {
                    Toast.makeText(HomeActivity.this, "Info", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }
}